<template>
  <div class="practice">
  <div class="pre_button" @click="pre"></div>
    <div class="practice_content" >
      <div class="practice_title">
        <a>{{this.data[index].content}}</a>
        <div v-if="!isRecoding" class="practice_record" @click="onRecording"> </div>
        <div v-else class="practice_recording" @click="stopRecording"> </div>
        
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
import http from "@/util/http-game";
import Recorder from '../../assets/js/recorder';
import axios from "axios";
import Swal from 'sweetalert2'
// import RecordingList from '../audio/RecordingList.vue';

export default {
  name: "sentencePractice",
  components: {
    AudioDeviceSelectDialog,
  },
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
      audioData : null,
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
    if (this.$route.params.gameType!=null) {
      this.gameType = this.$route.params.gameType;
      this.$store.commit('setGameType', this.gameType);
    }
    this.gameType = this.$store.getters.getGameType;
    console.log("storestore",this.$store.getters.getGameType);
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
      this.value = recording;

      console.log("recorder",this.recorder)
      console.log("ondataavailable",this.recorder._mediaRecorder.ondataavailable)
      console.log("recorder.lastRecording",this.recorder.lastRecording)
      console.log("recorder.lastRecording.blob",this.recorder.lastRecording.blob)
      console.log("url",URL.createObjectURL(this.recorder.lastRecording.blob));
      this.audioData = new File([this.recorder.lastRecording.blob], "soundBlob", { lastModified: new Date().getTime(), type: "audio" }).toString('base64');
      
      console.log("value.blob.toString('base64')",this.value.blob.toString('base64'))
      this.createURL()
      this.$emit('input', this.value);
    },
    createURL(){
      this.audioURL = null;
      if (this.value == null) return this.value;
      let reader = new FileReader();
      reader.onload = e => {
        console.log("e",e)
        this.audioURL = e.target.result;
        // console.log("sdsd",console.log(URL.createObjectURL(e.target)))
        this.pronunciation();

        setTimeout(() => {
          this.$refs.audioEl.load();
        }, 100);
      };
      reader.readAsDataURL(this.value.blob);
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
    pre(){
      this.index--;
    },
    next(){
      if(this.index==14){
        Swal.fire({
          icon: "success",
          html: "테스트를 모두 완료하셨습니다.<br>점수페이지로 이동하시겠습니까?",
          showConfirmButton: true,
          showCancelButton: true,
          cancelButtonColor: '#d33',
          // timer: 1000,
        }).then((result) => {
          /* Read more about isConfirmed, isDenied below */
          if (result.isConfirmed) {
            this.$router.push({name: 'score'});
          }
        });
        
        return
      }
      this.index++;
    },
    getData(){
      http.get("game/"+this.gameType)
      .then((res)=>{
        console.log(res.data)
        if(this.gameType=='word') this.data=res.data.word
        else this.data=res.data.sentence
      })
      .catch((err)=>{
        console.log(err);
      })
    },
    pronunciation(){
      // var fs = require('fs');
      // var contents = fs.readFileSync("sliderImages", "utf8");
      var audio = new Audio(this.audioURL)
      console.log("audio",audio)
      var openApiURL = 'http://aiopen.etri.re.kr:8000/WiseASR/PronunciationKor'; 
      var requestJson = {
          'access_key': this.accessKey,
          'argument': {
              'language_code': this.languageCode,
              'audio': audio
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
