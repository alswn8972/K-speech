<template>
  <div class = "acid">
    <div class="rain">
      <div id="box_r">
        <div class="title">
          <a class="font">{{this.score}} 점</a>
          <div class= "right"> 
          
          <span v-for="idx in this.life" :key="idx">
            <img src="../../assets/heart.png" style="width: 30px" />
          </span>

          </div>
        </div>
        <div class = "row words">
          <div class= "col-4 word">
            <div class = "content" v-for="(item,index) in boxes.slice(0,5)">
              {{item.content}}
            </div>
          </div>
          <div class= "col-4 word">
            <div class = "content" v-for="(item,index) in boxes.slice(5,10)">
              {{item.content}}
            </div>
          </div>
          <div class= "col-4 word">
            <div class = "content" v-for="(item,index) in boxes.slice(10,15)">
              {{item.content}}
            </div>
          </div>
        </div>
        <audio-recorder class = "audio" v-model="audioRecordings" :index="this.index" :data="this.data"></audio-recorder>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import http from "@/util/http-game"
import httpUser from "@/util/http-common";
import AudioRecorder from '../audio/AudioRecorder.vue'
import Swal from 'sweetalert2'

var placeLetterInterval = 4500;
var placeLetterTimer, moveLettersTimer;
var box;

export default {
  components: {
    AudioRecorder
  },
  name: "RockGame",
  data() {
    return {
      audioRecordings: new Array(1),
      gamecnt : 0,
      index : 0,
      letter : "",
      score: 0,
      life: 5,
      boxes : [],
      gameIsOver: false,
      count: 0,
      data : [{
        content:"",
        pron:""
      }],
      word:null
    };
  },
  created() {
    http.get("game/word")
      .then((res)=>{
        this.boxes=res.data.word
      })
      .catch((err)=>{
        console.log(err);
      })
      this.startGame();
  },
  watch: {
    audioRecordings: function (newVal, oldVal) {
      if(newVal[0].mypron==null) return
      newVal[0].mypron=newVal[0].mypron.replace(/(\s*)/g, "")
      
      for (var i = 0; i < this.boxes.length; i++) {
        if (this.boxes[i].content == newVal[0].mypron) {
          this.score+=10
          this.gamecnt +=1
          this.boxes[i].content="     ";
          
          if(this.gamecnt>=15){
            http.get("game/word")
              .then((res)=>{
                this.boxes=res.data.word
              })
              .catch((err)=>{
                console.log(err);
              })
            this.gamecnt=0
          }
          return;
        }
      }
      this.life =this.life-1;
      if (this.life == 0) {
        this.gameIsOver = true;
        this.endGame();
      }
    }
  },
  methods: {
    endGame: function () {
      const rank = {
          userNick: this.$store.getters.getUser.userNickName,
          score : this.score,
          type : 4,
        }

      http.post("game/save/score", JSON.stringify(rank))
            .then((res) => {
              // console.log(res)
            }).catch((err)=>{
          console.log(err);
        })

        const result = {
          userId: this.$store.getters.getUser.userId,
          date : new Date(),
          score : this.score,
          type : 4,
        }

        httpUser.post("api/users/game/result", JSON.stringify(result))
            .then((res) => {              
            }).catch((err)=>{
          console.log(err);
        })

      Swal.fire({
          html: this.$store.getters.getUser.userNickName+"님의 점수는 "+this.score+"점 입니다.<br>다시 한 번 플레이하시겠습니까?",
          showConfirmButton: true,
          showCancelButton: true,
          cancelButtonColor: '#d33',
        }).then((result) => {
          if (result.isConfirmed) {
            this.resetGame();
          }
        });
    },
    
    resetGame: function () {
      this.score = 0;
      this.life = 5;
      http.get("game/word")
      .then((res)=>{
        this.boxes=res.data.word
      })
      .catch((err)=>{
        console.log(err);
      })
      this.startGame()
    },
    startGame: function () {
      this.life = 5;
    },
  },
  destroyed() {
    this.resetGame();
  },
};
</script>

<style>
.content{
  height:15%;
  text-align:center;
}
.words{
  height : 100%;
}
.word{
  height : 75%;
  margin-top : 15%;
}
.font{
  font-family: "neodgm_pro";
  font-weight: bold;
  font-size: x-large;
}
.score{
  width: 80%;
  height: 800px;
}

#box_r {
  flex-direction: row;
  width: 100%;
  height: 100%;
  flex: 70%;
  border-radius: 20px;
  box-shadow: 5px 5px 5px rgba(128, 128, 128, 0.733);
  background-image: url("../../assets/rock.png");
  background-size : cover;
  position: relative;
  overflow: hidden;
}
.title_r{
  text-align: center;
  position: relative;
  flex-direction: row;
  padding: 1.5rem;
  width: 100%;
  height: 70px;
}
.right{
  text-align : right;
}
.audio{
  position: absolute;
  bottom: 50px;
  left: 50%;
}
</style>