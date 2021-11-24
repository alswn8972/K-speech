<template>
  <div>
    <div v-if="!isRecoding" class="practice_record" @click="onRecording"> </div>
    <div v-else class="practice_recording" @click="stopRecording"> </div>
    <audio-device-select-dialog
      v-model="showSelectableDevices"
      :devices="availableDevices"
      @record="startRecording"
    ></audio-device-select-dialog>
  </div>
</template>

<script>
import AudioDeviceSelectDialog from '../audio/AudioDeviceSelectDialog.vue';
import Recorder from '../../assets/js/recorder';

export default {
  name: "sentencePractice",
  components: {
    AudioDeviceSelectDialog,
  },
  props: {
    value: {
      type: Array,
      default: []
    },
    index : Number,
    data : Array
  },
  data: () => {
    return {
      /** @type {Recorder} */
      isRecoding: false,
      mypron : null,
      score : null,
      accessKey : 'c23cef1f-ed3f-4449-9cdf-20397f93b6d7',
      languageCode : 'korean',
      audioURL :null,
      recorder : null,
      recorderState : 'stopped',
      enableRecording : true,
      deviceSelectionError : null,
      audioData : null,
      showSelectableDevices: false,
      audio : null,
      recognition : new(window.SpeechRocognition || window.webkitSpeechRecognition)(),
      availableDevices: [],
      strings: {
        noMicrophone: 'No suitable recording device could be found.'
      },
    }
  },
  created(){
    this.recorder = new Recorder();
    this.recorder.addEventListener('start', () => {
      this.recorderState = 'recording';
    });
    this.recorder.addEventListener('stop', () => {
      this.recorderState = 'stopped';
    });
  },
  methods: {
    onRecording(){
      this.isRecoding=true;
      this.record()
      this.recognition.lang='ko-KR'
      this.recognition.start()
    },
    async record() {
      if (this.recorderState === 'paused') {
        this.recorder.resume();
        return;
      }
      
      this.enableRecording = false;
      this.deviceSelectionError = null;
      let previousDeviceId = this.$store.state.selectedAudioInputId;
      this.availableDevices = await this.getAvailableDevices();
      
      if (this.availableDevices.length < 1) {
        this.deviceSelectionError = true;
        return;
      }
      
      if (this.availableDevices.length === 1)
        return this.startRecording(this.availableDevices[0]);
      
      let previousDevice = this.availableDevices.find(
        d => d.deviceId === previousDeviceId
      );

      if (previousDevice)  return this.startRecording(previousDevice.deviceId);
      
      this.presentSelectableDevices();
    },
    startRecording(deviceId) {
      this.$store.commit('setAudioInputId', deviceId);
      this.recorder.start(deviceId);
    },
    async stopRecording() {
      this.isRecoding=false;
      this.recognition.stop()

      
      await this.recorder.stop();
      
      let recording = {};
      Object.assign(recording, this.recorder.lastRecording);
      this.createURL()
      
    },
    createURL(){
      this.audioURL = null;
      let reader = new FileReader();

      reader.onload = e => {
        this.audioURL = e.target.result;
      };
      reader.readAsDataURL(this.recorder.lastRecording.blob)

      let pcmReader = new FileReader();
      pcmReader.onload = e => {
        this.audioData = e.target.result;
        this.pronunciation();
      };
      pcmReader.readAsDataURL(this.recorder.lastRecording.pcm)
    },
    async getAvailableDevices() {
      try {
        await navigator.mediaDevices.getUserMedia({
          audio: true,
          video: false
        });
        let devices = await navigator.mediaDevices.enumerateDevices();
        return devices.filter(d => d.kind === 'audioinput');
      } catch (e) {
        console.error('Error enumerating media devices: ', e);
        return [];
      }
    },
    presentSelectableDevices() {
      this.showSelectableDevices = true;
    },
    async pronunciation(){
      this.audio = new Audio(this.audioURL)
      this.audio.play(this.audioURL)
      var openApiURL = 'http://aiopen.etri.re.kr:8000/WiseASR/PronunciationKor'; 
      var requestJson = {
          'access_key': this.accessKey,
          'argument': {
              'language_code': this.languageCode,
              'script': this.data[this.index].pron,
              'audio': this.audioData.substr(22)
          }
      };

      var request = require('request');
      var options = {
          url: openApiURL,
          body: JSON.stringify(requestJson),
          headers: {'Content-Type':'application/json; charset=UTF-8'}
      };
      request.post(options, (error, response, body)=> {
          // console.log('responseBody = ' + body);
          let my = body.split(":");
          this.score = my[my.length-1].replace("}}","")
          
          this.recognition.onresult = event=> {
            this.mypron = event.results[0][0].transcript;
          }

          let evalResult = {
            score : this.score.substring(0,5),
            mypron : this.mypron
          }
          
          this.value.splice(this.index, 1, evalResult)
          this.$emit('update', this.value);
          this.mypron=""
      });
    },
  }
};
</script>

<style scoped src="../../css/Practice.css">
</style>
