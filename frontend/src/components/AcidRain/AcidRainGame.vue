<template>
<div class="rain">
  <div id="box">
    <!-- 오디오 (정답, 오답, 게임오버) -->
    

    <!-- 게임 오버 모달 -->
    

    <!-- <img class="ground" src="../../assets/rain.gif" /> -->
    <span id="menu" class="menu"> </span>
    <audio-recorder v-model="audioRecordings" :index="this.index" :data="this.data"></audio-recorder>
  </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import http from "@/util/http-game"
import AudioRecorder from '../audio/AudioRecorder.vue'

// 상자 생성 속도, 상자 이동 속도
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
      // console.log("노금", newVal[0].mypron)
      // console.log("sd",this.boxes[1].innerText)
      if(this.boxes.length<2) return;
      for (var i = 1; i < this.boxes.length; i++) {
        if (this.boxes[i].innerText == newVal[0].mypron) {
          this.boxes[i].remove();
          // console.log("zzzzzzzzzzzzz",this.boxes[i].innerText)
          if (this.life == 0) {
            this.gameIsOver = true;
            this.endGame();
          }
          break;
        }
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
      for (var i = 0; i < this.boxes.length; i++) {
        this.boxes[i].style.bottom = parseInt(this.boxes[i].style.bottom) - 3 - this.count + "px";
        if (parseInt(this.boxes[i].style.bottom) <= 5) {
          this.life = this.life - 1;
          this.boxes[i].remove();
          console.log("life",this.life)
          if (this.life == 0) {
            this.gameIsOver = true;
            this.endGame();
          }
        }
      }
    },
    // 게임 종료
    endGame: function () {
      // 글자 생성, 이동, 사용자 입력 정지
      clearInterval(moveLettersTimer);
      clearInterval(placeLetterTimer);
    },
    // 리셋
    resetGame: function () {
      // 게임 오버 모달 숨기기
      this.gameIsOver = false;
      // 점수, 생명 리셋
      this.score = 0;
      this.life = 5;
      // 나와 있는 모든 글자 제거
      var boxes = document.querySelectorAll("#quiz");
      for (var i = 0; i < this.boxes.length; i++) 
        this.boxes[i].remove();

      this.endGame();
    },
    // 게임 시작
    startGame: function () {
      this.life = 5;
      placeLetterTimer = setInterval(this.placeLetter, placeLetterInterval);
      moveLettersTimer = setInterval(this.moveLetters, 100);
    },
  },
  destroyed() {
    this.endGame();
  },
};
</script>

<style>
.rain{
  width: 80%;
  height: 800px;
  background-color: #f4f1eb;
  flex: 70%;
  border-radius: 20px;
  margin: 2% 10%;
  box-shadow: 5px 5px 5px rgba(128, 128, 128, 0.733);
  position: relative;
  overflow: hidden;
}

#box {
  width: 100%;
  height: 100%;
  /* padding: 20px 30px;
  position: relative;
  margin: auto; */
  flex: 70%;
  border-radius: 20px;
  /* margin: 2vh 2vh 0 2vh; */
  box-shadow: 5px 5px 5px rgba(128, 128, 128, 0.733);
  background-image: url("../../assets/rain.gif");
  background-size : cover;
  position: relative;
  overflow: hidden;
  font-family: "Georgia";
  font-size: 20px;
  color: white;
}

#box > .ground {
  /* background: url("@/assets/ground.png"); */
  /* top: 100%; */
  z-index: 2;
  object-fit: contain;
  width: 100%;
  /* height: 37%; */
  position: absolute;
  bottom: 0px;
  left: 0px;

  /* margin: 44% 0 0 0; */
}

.tree-ground {
  width: 100%;
}

.bg {
  width: 100%;
  height: 100%;
  z-index: 1;
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
.modal-content {
  /* height: 100% !important; */
  border: 0px !important;
}
.modal-body {
  background-color: #f4f1eb;
}
.modal-dialog {
  max-width: 1000px !important;
  /* position: absolute;
  vertical-align: middle;
  top: 40%; */
  width: 75% !important;
  height: 45%;
  top: 25%;
  /* margin: 25% 22%; */
  padding: 1% 2% 1.3% 1%;
  /* object-fit: contain; */
  border-radius: 1.31rem;
  box-shadow: 0rem 0.5rem 1rem 0.06rem rgba(0, 0, 0, 0.43);
  border: solid 0.06rem #a4a4a3;
  background-color: #f4f1eb;

  font-family: SDSamliphopangcheTTFBasic;
  font-size: 30px;
  font-weight: bold;
  font-stretch: normal;
  font-style: normal;
  line-height: 0.71;
  letter-spacing: normal;
  text-align: center;
  color: #b59e7a;
}

.figure-container {
  fill: transparent;
  stroke: #fff;
  stroke-width: 4px;
  stroke-linecap: round;
}
.figure-part {
  display: block;
}
.topic-difficulty {
  display: column;
}
.topic-difficulty .topic {
  font-size: 2.5rem;
  font-family: "SDSamliphopangche_Basic";
  color: #fff;
  position: absolute;
  top: 700%;
  left: 59%;
  z-index: 3;
}
.topic-difficulty .difficulty {
  font-size: 1.5rem;
  font-family: "SDSamliphopangche_Basic";
  color: #fff;
  position: absolute;
  top: 820%;
  left: 60%;
  z-index: 3;
}
.wrong-letters-container {
  position: absolute;
  top: 40%;
  right: 20%;
  display: flex;
  flex-direction: row;
  text-align: right;
}
.wrong-letters-container p {
  margin: 0 0 5px;
  padding: 0px 15px;
}
.wrong-letters-container span {
  font-size: 24px;
}
.word {
  display: flex;
  position: absolute;
  bottom: 50px;
  left: 50%;
  transform: translateX(-50%);
}
/* .word-letters-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: row;
  text-align: right;
} */
.word-letters-container #nextstage {
  position: absolute;
  top: 8%;
  left: 70%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: row;
  text-align: right;
}

.word-letters-container p {
  margin: 0 0 5px;
}
.word-letters-container span {
  font-size: 24px;
}
.letter-container {
  display: flex;
  justify-content: center;
}
.letter {
  width: 2.75rem;
  height: 2.625rem;
  background: #957457;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: white;
  border-radius: 6px;
  font-family: "SDSamliphopangche_Basic";
  font-size: 2rem;
  border: solid 2px #1cb959;
  margin: 3px;
}
.letter-blank {
  width: 2.75rem;
  height: 2.625rem;
  background: #e5d2bd;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: white;
  border-radius: 6px;
  font-family: "SDSamliphopangche_Basic";
  font-size: 2rem;
  border: solid 2px #a38468;
  margin: 3px;
}
.keyboard {
  display: column;
}

.keyboard-line {
  display: flex;
  /* margin: auto; */
  justify-content: center;
}

.keyword {
  width: 2.75rem;
  height: 2.625rem;
  margin: 3px;
  color: white;
  background: #957457;
  border-radius: 6px;
  font-family: "SDSamliphopangche_Basic";
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2rem;
}
.pressed {
  width: 40px;
  margin: 3px;
  color: white;
  /* background: gray; */
  border-radius: 20%;
  background: #e5d2bd;
  /* transform: scale(1, 2); */
}
.answer {
  width: 40px;
  margin: 3px;
  height: 2.625rem;
  color: white;
  /* background: gray; */
  border-radius: 20%;
  background: #e5d2bd;
  /* transform: scale(1, 2); */
  border: solid #69c995 2px;
}
.modal-close-btn {
  position: absolute;
  width: 15%;
  height: 15%;
  left: 80%;
  border: none;
  background-color: grey;
  box-shadow: 0rem 0.38vh 0.56rem 0 rgba(0, 0, 0, 0.3);
  object-fit: contain;
  border-radius: 2rem;
}

.close-btn-txt {
  font-family: SDSamliphopangche_Basic;
  font-size: 3.5vh;
}

.game-over-text {
  font-family: SDSamliphopangche_Basic;
  font-size: 6vh;
  margin-bottom: 3%;
  font-weight: bolder;
  font-stretch: normal;
  font-style: normal;
  line-height: 0.63;
  letter-spacing: normal;
  text-shadow: 0rem 0.38rem 0.56rem rgba(0, 0, 0, 0.3);
  text-align: center;
  color: #1e3663;
}

.modal-cardFont {
  color: #b59e7a;
  font-size: 5vh;
  font-family: "SDSamliphopangche_Basic";
  margin-bottom: 0;
  padding: 0;
  margin-top: 1.5vh;
}

.modal-score {
  position: relative;
  left: 27.5%;
  width: 40%;
  height: 6vh;
  background-color: #e5d2bd;
  margin-bottom: 0;
  margin-top: 1%;
  margin-left: 3vh;
  margin-right: 3vh;
  border-radius: 30px;
  border: solid 4px #b49f7b;
  box-shadow: 5px 5px 5px rgba(128, 128, 128, 0.733);
  color: #957457;
  font-family: "SDSamliphopangche_Basic";
  font-size: 5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

.modal-rank-cardFont {
  color: #b59e7a;
  font-size: 4vh;
  font-family: "SDSamliphopangche_Basic";
  margin-bottom: 0;
  padding: 0;
  margin-top: 1.5vh;
}

.modal-rank-score {
  position: relative;
  left: 20%;
  width: 50%;
  height: 5vh;
  background-color: #e5d2bd;
  margin-bottom: 0;
  margin-top: 0.5%;
  margin-left: 3vh;
  margin-right: 3vh;
  border-radius: 30px;
  border: solid 4px #b49f7b;
  box-shadow: 5px 5px 5px rgba(128, 128, 128, 0.733);
  color: #957457;
  font-family: "SDSamliphopangche_Basic";
  font-size: 4vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

.best-score-img {
  position: absolute;
  left: -10%;
  width: 25%;
}

.modal-hscore-cardFont {
  color: #b59e7a;
  font-size: 4vh;
  font-family: "SDSamliphopangche_Basic";
  margin-bottom: 0;
  padding: 0;
  margin-top: 1.5vh;
}

.modal-hscore-score {
  position: relative;
  left: 20%;
  width: 50%;
  height: 5vh;
  background-color: #e5d2bd;
  margin-bottom: 0;
  margin-top: 0.5%;
  margin-left: 3vh;
  margin-right: 3vh;
  border-radius: 30px;
  border: solid 4px #b49f7b;
  box-shadow: 5px 5px 5px rgba(128, 128, 128, 0.733);
  color: #957457;
  font-family: "SDSamliphopangche_Basic";
  font-size: 4vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}

.row {
  display: flex;
}

.column {
  flex: 50%;
}

.modal-restart-btn {
  width: 40%;
  height: 40%;
  /* border: solid 0.5vh #76300b; */
  border: none;
  background-color: #fe6e27;
  box-shadow: 0rem 0.38vh 0.56rem 0 rgba(0, 0, 0, 0.3);
  object-fit: contain;
  border-radius: 2rem;
}

.restart-btn-txt {
  font-family: SDSamliphopangche_Basic;
  font-size: 3.5vh;
}

.modal-halloffame-btn {
  width: 40%;
  height: 40%;
  /* border: solid 0.5vh #76300b; */
  border: none;
  background-color: #68bbf7;
  box-shadow: 0rem 0.38vh 0.56rem 0 rgba(0, 0, 0, 0.3);
  object-fit: contain;
  border-radius: 2rem;
}

.halloffame-btn-txt {
  font-family: SDSamliphopangche_Basic;
  font-size: 3.5vh;
}
</style>