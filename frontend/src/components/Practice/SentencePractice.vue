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
import httpUser from "@/util/http-common";
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
    // console.log(this.$store.getters.getUser)
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
      if(this.index==this.data.length-1){
        let gType=0;
        if(this.gameType=="sentence") gType=2;
        else if (this.gameType=="word") gType=1;

        let scoreAverage = 0;
        for(let i=0; i<this.audioRecordings.length; i++)
          scoreAverage+=Number(this.audioRecordings[i].score);
        //  console.log("zzzzzzzzzzz",(scoreAverage/this.audioRecordings.length*20).toFixed(0))
        
        const rank = {
          userNick: this.$store.getters.getUser.userNickName,
          score : scoreAverage,
          type : gType,
        }

        http.post("game/save/score", JSON.stringify(rank))
            .then((res) => {
              console.log(res)
            }).catch((err)=>{
          console.log(err);
        })

        const result = {
          userId: this.$store.getters.getUser.userId,
          date : new Date(),
          score : scoreAverage,
          type : gType,
        }

        httpUser.post("api/users/game/result", JSON.stringify(result))
            .then((res) => {
              console.log(res)
              
            }).catch((err)=>{
          console.log(err);
        })

        Swal.fire({
          icon: "success",
          html: "테스트를 모두 완료하셨습니다.<br>점수페이지로 이동하시겠습니까?",
          showConfirmButton: true,
          showCancelButton: true,
          cancelButtonColor: '#d33',
        }).then((result) => {
          if (result.isConfirmed) {
            this.$router.push({name: 'score', params: {data : this.data, result : this.audioRecordings}});
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
