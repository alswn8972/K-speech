<template>
  <div class="practice">
  <div class="pre_button" @click="pre"></div>
    <div class="practice_content" >
      <div class="practice_title">
        <a>{{this.data[index].content}}</a>
        <!--<div v-if="!isRecoding" class="practice_record" @click="onRecording"> </div>
        <div v-else class="practice_recording" @click="stopRecording"> </div>-->
        <audio-recorder v-model="audioRecordings" :index="this.index" :data="this.data"></audio-recorder>
      </div>
      <div class="practice_body">
        <p>올바른 발음 : {{this.data[index].pron}}</p>
        <p>내 발음 : {{this.audioRecordings[this.index].mypron}}</p>
        <p>발음 점수 : {{this.audioRecordings[this.index].score}}</p>
      </div>
      <p>{{index+1}}/ {{this.data.length}}</p>
    </div>
    <div class="next_button" @click="next"></div>
    <!--<audio-device-select-dialog
      v-model="showSelectableDevices"
      :devices="availableDevices"
      @record="startRecording"
    ></audio-device-select-dialog>-->
    <!--<recording-list :recordings="value" @delete="deleteRecording"></recording-list>-->
  </div>
</template>

<script>
import AudioDeviceSelectDialog from '../audio/AudioDeviceSelectDialog.vue';
import http from "@/util/http-game";
import Recorder from '../../assets/js/recorder';
import axios from "axios";
import Swal from 'sweetalert2'
import AudioRecorder from '../audio/AudioRecorder.vue'
// import RecordingList from '../audio/RecordingList.vue';

export default {
  name: "sentencePractice",
  components: {
    AudioRecorder,
    AudioDeviceSelectDialog,
  },
  data: () => {
    return {
      /** @type {Recorder} */
      audioRecordings: new Array(15),
      isRecoding: false,
      index : 0,
      data : [{
            content:0,
            pron:0
          }],
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

  },
  methods: {
    pre(){
      this.index--;
    },
    next(){
      console.log("해치웠나",this.audioRecordings)
      if(this.index==this.data.length-1){
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
  }
};
</script>

<style scoped src="../../css/Practice.css">
</style>
