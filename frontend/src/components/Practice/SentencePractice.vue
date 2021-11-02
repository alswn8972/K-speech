<template>
  <div class="practice">
  <div class="pre_button" @click="pre"></div>
    <div class="practice_content" >
      <div class="practice_title">
        <a>{{this.data[index].content}}</a>
        <div v-if="!isRecoding" class="practice_record" @click="onRecording"> </div>
        <div v-else class="practice_recording" @click="evaluate"> </div>
        
      </div>
      <div class="practice_body">
        <a>올바른 발음 : {{this.data[index].pron}}</a>
      </div>
      <p>{{index+1}}/ 15</p>
    </div>
    <div class="next_button" @click="next"></div>
    <audio-device-select-dialog
      v-model="showSelectableDevices"
      :devices="availableDevices"
      @record="startRecording"
    ></audio-device-select-dialog>
    <audio controls ref="audioEl" v-show="audioURL != null">
        <source :src="audioURL || ''" type="audio/mpeg">Your browser does not support audio playback.
      </audio>
    <!--<recording-list :recordings="value" @delete="deleteRecording"></recording-list>-->
  </div>
  
</template>

<script>
import AudioDeviceSelectDialog from '../audio/AudioDeviceSelectDialog.vue';
import http from "@/util/http-common";
import Recorder from '../../assets/js/recorder';
// import RecordingList from '../audio/RecordingList.vue';

export default {
  name: "sentencePractice",
  components: {
    AudioDeviceSelectDialog,
    // RecordingList
  },
  // props: {
  //   value: {
  //     type: Array,
  //     default: []
  //   }
  // },
  data: () => {
    return {
      isRecoding: false,
      index : 0,
      data : [
          {
            content:0,
            pron:0
          }
        ],
      score: null,
      accessKey : '9ce13ac9-a6bf-43ea-acd4-edec418e0a3a',
      languageCode : 'korean',
      audioURL:null,
      recorder: null,
      recorderState: 'stopped',
      enableRecording: true,
      deviceSelectionError: null,
      showSelectableDevices: false,
      availableDevices: [],
      value: {
        type: Array,
        default: []
      },
      strings: {
        noMicrophone: 'No suitable recording device could be found.'
      },
    }
  },
  created(){
    // 단어데이터
    this.gameType = this.$route.params.gameType;
    console.log(this.gameType);
    this.getData();

    // 녹음 기능
    this.recorder = new Recorder();
    this.recorder.addEventListener('start', () => {
      this.recorderState = 'recording';
    });
    this.recorder.addEventListener('stop', () => {
      this.recorderState = 'stopped';
    });
    this.recorder.addEventListener('pause', () => {
      this.recorderState = 'paused';
    });
    this.recorder.addEventListener(
      'resume',
      () => (this.recorderState = 'recording')
    );
  },
  methods: {
    onRecording(){
      this.isRecoding=true;
      this.record()
    },
    async record() {
      console.log(1)
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
    pauseRecording() {
      this.recorder.pause();
    },
    deleteRecording(recording) {
      this.$emit('input', this.value.filter(x => !Object.is(x, recording)));
    },
    async stopRecording() {
      this.isRecoding=false;
      await this.recorder.stop();
      let recording = {};
      Object.assign(recording, this.recorder.lastRecording);
      console.log("recording",recording)
      this.value = recording;
      this.createURL()
      this.$emit('input', this.value);
    },
    createURL(){
      console.log("sdfdsf",this.recording)
      this.audioURL = null;
      if (this.value == null) return this.value;
      let reader = new FileReader();
      reader.onload = e => {
        this.audioURL = e.target.result;
      console.log("fgh",this.audioURL)
        /* Warning: Hack below as $nextTick wasn't sufficient */
        setTimeout(() => {
          this.$refs.audioEl.load();
        }, 100);
      };
      reader.readAsDataURL(this.value.blob);
    },
    async getAvailableDevices() {
      try {
        // First request default device, to grant permissions to let us access labels for input devices
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
    evaluate(){
      this.stopRecording();
      // this.pronunciation();
      // this.Recognition();
    },
    pre(){
      this.index--;
    },
    next(){
      this.index++;
    },
    getData(){
      http.get("/api/game/word")
      .then((res)=>{
        this.data=res.data.word;
      })
      .catch((err)=>{
        console.log(err);
      })
    },
    // recordAudio(){
    //   if (navigator.mediaDevices) {
    //     const constraints = {
    //       audio: true
    //     }

    //     navigator.mediaDevices.getUserMedia(constraints).then(stream => {
    //       this.mediaRecorder = new MediaRecorder(stream)
    //       this.mediaRecorder.start()

    //       this.mediaRecorder.ondataavailable = e => {
    //         this.audioURL = e.data;
    //         console.log(URL.createObjectURL(this.audioURL));
    //         this.audioData = new File([this.audioURL], "soundBlob", { lastModified: new Date().getTime(), type: "audio" });
    //         console.log("fsdfasdfasdf",this.audioData)
    //         // this.pronunciation();
    //         this.Recognition();
    //       }
            
    //     }).catch(err => {
    //       console.log('The following error occurred: ' + err)
    //     })
    //   }
    //   this.isRecoding=true;
    // },
    pronunciation(){
      var openApiURL = 'http://aiopen.etri.re.kr:8000/WiseASR/PronunciationKor'; 
      var requestJson = {
          'access_key': this.accessKey,
          'argument': {
              'language_code': this.languageCode,
              'audio': this.audioData.toString('base64')
          }
      };

      var request = require('request');
      var options = {
          url: openApiURL,
          body: JSON.stringify(requestJson),
          headers: {'Content-Type':'application/json; charset=UTF-8'}
      };
      request.post(options, function (error, response, body) {
          console.log('responseCode = ' + response.statusCode);
          console.log('responseBody = ' + body);
      });
    },
    Recognition(){
      var openApiURL = 'http://aiopen.etri.re.kr:8000/WiseASR/Recognition';
      var requestJson = {
          'access_key': this.accessKey,
          'argument': {
              'language_code': this.languageCode,
              'audio': this.audioData.toString('base64')
          }
      };

      var request = require('request');
      var options = {
          url: openApiURL,
          body: JSON.stringify(requestJson),
          headers: {'Content-Type':'application/json; charset=UTF-8'}
      };
      request.post(options, function (error, response, body) {
          console.log('responseCode = ' + response.statusCode);
          console.log('responseBody = ' + body);
      });
    }
  }
};
</script>

<style scoped src="../../css/Practice.css">
</style>
