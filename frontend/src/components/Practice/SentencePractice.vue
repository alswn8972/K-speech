<template>
  <div class="practice">
  <div class="pre_button" @click="pre"></div>
    <div class="practice_content" >
      <div class="practice_title">
        <a>{{this.data[index].content}}</a>
        <audio-recorder v-model="audioRecordings" :index="this.index" :data="this.data"></audio-recorder>
      </div>
      <div class="practice_body">
        <div><p>올바른 발음 : {{this.data[index].pron}}</p></div>
        <div><p>내 발음 : {{audioRecordings[this.index].mypron}}</p></div>
        <div><p>발음 점수 : {{audioRecordings[this.index].score}}</p></div>
      </div>
      <p>{{index+1}}/ {{this.data.length}}</p>
    </div>
    <div class="next_button" @click="next"></div>
  </div>
</template>

<script>
import AudioDeviceSelectDialog from '../audio/AudioDeviceSelectDialog.vue';
import http from "@/util/http-game";
import Recorder from '../../assets/js/recorder';
import axios from "axios";
import Swal from 'sweetalert2'
import AudioRecorder from '../audio/AudioRecorder.vue'

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
        content:"",
        pron:""
      }],
    }
  },
  created(){
    console.log(this.$store.getters.getUser)
    if (this.$route.params.gameType!=null) {
      this.gameType = this.$route.params.gameType;
      this.$store.commit('setGameType', this.gameType);
    }
    this.gameType = this.$store.getters.getGameType;
    this.getData();

    let evalResult = {
        score : "",
        mypron : ""
      }
    for(let i=0; i<15; i++)
      this.audioRecordings[i]=evalResult
  },
  methods: {
    pre(){
      this.index--;
    },
    next(){
      console.log(this.audioRecordings)
      console.log(this.audioRecordings[this.index])
      
      if(this.index==this.data.length-1){
        let gType=0;
        if(this.gameType=="sentence") gType=2;
        else if (this.gameType=="word") gType=1;

        const rank = {
              userNick: this.user.userPhone,
              score : this.audioRecordings[this.index].score.replace(".",""),
              type : gType,
            }

          http
          .post("/save/score", JSON.stringify(rank))
          .then((res) => {
            if(res.data.status === "success"){
              console.log(res)
            }else{
              Swal.fire({
                icon: "error",
                text: "인증번호 발송을 실패했습니다",
                showConfirmButton: false,
                timer: 1000,
              });
            };
          })

        Swal.fire({
          icon: "success",
          html: "테스트를 모두 완료하셨습니다.<br>점수페이지로 이동하시겠습니까?",
          showConfirmButton: true,
          showCancelButton: true,
          cancelButtonColor: '#d33',
        }).then((result) => {
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
