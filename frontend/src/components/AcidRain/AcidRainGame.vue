<template>
  <div class = "acid">
    <div class="rain">
      <div id="box">
        <div class="title">
          <a class="font">{{this.score}} 점</a>
          <div class= "right"> 
          
          <span v-for="idx in this.life" :key="idx">
          <img src="../../assets/heart.png" style="width: 30px" />
          </span>
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
  name: "AcidRainGame",
  data() {
    return {
      audioRecordings: new Array(1),
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
        this.word=res.data.word
      })
      .catch((err)=>{
        console.log(err);
      })
    this.startGame();
    this.placeLetter();
    this.moveLetters();
  },
  watch: {
    audioRecordings: function (newVal, oldVal) {
      if(this.boxes.length<2) return;
      for (var i = 2; i < this.boxes.length; i++) 
        if (this.boxes[i].innerText == newVal[0].mypron) {
          this.score+=10
          this.boxes[i].remove();
          if (this.life == 0) {
            this.gameIsOver = true;
            this.endGame();
          }
          break;
        }
    }
  },
  mounted() {
    box = document.getElementById("box");
  },
  methods: {
    placeLetter: function () {
      this.count = this.count + 0.4;
      if(this.word==null) return

      let i = (Math.random()*100/15).toFixed(0);; 
      this.letters = this.word[i].content
      const abc = document.querySelectorAll("#box > div")
      
      var newLetter = document.createElement("div");
      newLetter.id = "quiz";
      newLetter.innerHTML = this.letters;

      newLetter.style.left = 5 + Math.random() * 85 + "%";
      newLetter.style.bottom = 700+ "px";

      box.appendChild(newLetter);
    },
    // 글자 이동
    moveLetters: function () {
      this.boxes = document.querySelectorAll("#box > div");
      for (var i = 2; i < this.boxes.length; i++) {
        this.boxes[i].style.bottom = parseInt(this.boxes[i].style.bottom) - 3 - this.count + "px";
        if (parseInt(this.boxes[i].style.bottom) <= 0) {
          this.life = this.life - 1;
          this.boxes[i].remove();
          if (this.life == 0) {
            this.gameIsOver = true;
            this.endGame();
          }
        }
      }
    },
    // 게임 종료
    endGame: function () {
      const rank = {
          userNick: this.$store.getters.getUser.userNickName,
          score : this.score,
          type : 3,
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
          type : 3,
        }

        httpUser.post("api/users/game/result", JSON.stringify(result))
            .then((res) => {              
            }).catch((err)=>{
          console.log(err);
        })

      clearInterval(moveLettersTimer);
      clearInterval(placeLetterTimer);
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
      this.gameIsOver = false;
      this.score = 0;
      this.life = 5;
      this.count = 0;
      
      var boxes = document.querySelectorAll("#quiz");
      for (var i = 2; i < this.boxes.length; i++) {
        this.boxes[i].remove();
      }

      clearInterval(moveLettersTimer);
      clearInterval(placeLetterTimer);

      this.startGame()
    },
    
    startGame: function () {
      this.life = 5;
      placeLetterTimer = setInterval(this.placeLetter, placeLetterInterval);
      moveLettersTimer = setInterval(this.moveLetters, 100);
    },
  },
  destroyed() {
    this.resetGame();
  },
};
</script>

<style>
.font{
  font-family: "neodgm_pro";
    font-weight: bold;
    font-size: x-large;
}
.acid{
  min-height: 100vh;
  background-color:#eeb66c;
  font-family: "neodgm_pro";
  font-weight: bold;
  font-size: x-large;
  display: flex;
  justify-content: center;
}
.rain{
  width: 70%;
  height: 750px;
  background-color: #f4f1eb;
  flex: 70%;
  border-radius: 20px;
  margin: 2% 15%;
  box-shadow: 5px 5px 5px rgba(128, 128, 128, 0.733);
  position: relative;
  overflow: hidden;
}
.score{
  width: 80%;
  height: 800px;
}

#box {
  flex-direction: row;
  width: 100%;
  height: 100%;
  flex: 70%;
  border-radius: 20px;
  box-shadow: 5px 5px 5px rgba(128, 128, 128, 0.733);
  background-image: url("../../assets/rain.png");
  background-size : cover;
  position: relative;
  overflow: hidden;
}

#quiz{
  display: inline-block;
  position: absolute;
  height: 50px;
  width: 100px;
  text-align: center;
  transition-property: bottom;
  transition-duration: 100ms;
  transition-timing-function: linear;
  transition-delay: 0;
  z-index: 200;
}
.title{
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