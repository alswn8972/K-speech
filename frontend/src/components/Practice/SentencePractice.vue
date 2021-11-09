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
      /** @type {Recorder} */
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
      audio : null,
      recognition : new(window.SpeechRocognition || window.webkitSpeechRecognition)(),
      availableDevices: [],
      value: [{
        type: Array,
        default: []
      }],
      strings: {
        noMicrophone: 'No suitable recording device could be found.'
      },
    }
  },
  created(){
    // 단어, 문장 데이터
    if (this.$route.params.gameType!=null) {
      this.gameType = this.$route.params.gameType;
      this.$store.commit('setGameType', this.gameType);
    }
    this.gameType = this.$store.getters.getGameType;
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
    pauseRecording() {
      this.recorder.pause();
    },
    deleteRecording(recording) {
      this.$emit('input', this.value.filter(x => !Object.is(x, recording)));
    },
    async stopRecording() {
      this.isRecoding=false;
      this.recognition.stop()

      this.recognition.onresult = event=> {
        // The SpeechRecognitionEvent results property returns a SpeechRecognitionResultList object
        // The SpeechRecognitionResultList object contains SpeechRecognitionResult objects.
        // It has a getter so it can be accessed like an array
        // The first [0] returns the SpeechRecognitionResult at position 0.
        // Each SpeechRecognitionResult object contains SpeechRecognitionAlternative objects
        // that contain individual results.
        // These also have getters so they can be accessed like arrays.
        // The second [0] returns the SpeechRecognitionAlternative at position 0.
        // We then return the transcript property of the SpeechRecognitionAlternative object
        var sd = event.results[0][0].transcript;
        console.log("ddddddddddddddddddd",sd)
      }


      console.log("인식", this.recognition)
      await this.recorder.stop();
      
      let recording = {};
      Object.assign(recording, this.recorder.lastRecording);
      this.value.push(recording);

      this.createURL()
      this.$emit('input', this.value);
    },
    createURL(){
      this.audioURL = null;
      if (this.value == null) return this.value;
      let reader = new FileReader();

      reader.onload = e => {
        this.audioURL = e.target.result;
      };
      reader.readAsDataURL(this.recorder.lastRecording.blob)

      let pcmReader = new FileReader();
      pcmReader.onload = e => {
        this.audioData = e.target.result;
        this.pronunciation();
        // setTimeout(function() {
        //   console.log("hi")
        // }, 5000);
        //   console.log("zz")

        // this.recognition();
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
        if(this.gameType=='word') this.data=res.data.word
        else this.data=res.data.sentence
      })
      .catch((err)=>{
        console.log(err);
      })
    },
    pronunciation(){
      this.audio = new Audio(this.audioURL)
      this.audio.play(this.audioURL)
      var openApiURL = 'http://aiopen.etri.re.kr:8000/WiseASR/PronunciationKor'; 
      var requestJson = {
          'access_key': this.accessKey,
          'argument': {
              'language_code': this.languageCode,
              // 'script': this.data[this.index].pron,
              'script': "해치웠나",
              'audio': this.audioData.substr(22)
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
    // recognition(){
    //   // this.recognition = new(window.SpeechRocognition || window.webkitSpeechRecognition)();
    //   // if(!recognition){
    //   //   alert("ㅈ됨")
    //   // }else{
    //   //   alert("굿")

    //   // }

    //   // const fs = require('fs');
    //   // const speech = require('@google-cloud/speech');

    //   // // Creates a client
    //   // const client = new speech.SpeechClient();

    //   // const encoding='LINEAR16'; 
    //   // const sampleRateHertz=16000;
    //   // const languageCode='ko-KR';

    //   // const projectId='your project id'; //아래 설명
    //   // //

    //   // //아래는 그대로 사용

    //   // const config={
    //   // 	encoding: encoding,
    //   // 	sampleRateHertz: sampleRateHertz,
    //   // 	languageCode: languageCode
    //   // };

    //   // const audio={
    //   // 	content: this.audioData.substr(22)
    //   // };

    //   // const request={
    //   // 	config: config,
    //   // 	audio: audio
    //   // };

    //   // client.recognize(request)
    //   // 	.then((results) => {
    //   // 		const transcription=results[0].results[0].alternatives[0].transcript;
    //   // 		console.log('Transcription : ', transcription);
    //   // 	})
    //   // 	.catch((err) =>{
    //   // 		console.error('Error:',err);
    //   // 	});



    //   // console.log("recognition")
    //   // var openApiURL = 'http://aiopen.etri.re.kr:8000/WiseASR/Recognition';
    //   // var requestJson = {
    //   //     'access_key': this.accessKey,
    //   //     'argument': {
    //   //         'language_code': this.languageCode,
    //   //         'audio': this.audioData.substr(22)
    //   //     }
    //   // };

    //   // var request = require('request');
    //   // var options = {
    //   //     url: openApiURL,
    //   //     body: JSON.stringify(requestJson),
    //   //     headers: {'Content-Type':'application/json; charset=UTF-8'}
    //   // };
    //   // request.post(options, function (error, response, body) {
    //   //     console.log('responseCode = ' + response.statusCode);
    //   //     console.log('responseBody = ' + body);
    //   // });
    // }
  }
};
</script>

<style scoped src="../../css/Practice.css">
</style>
