<template>
  <div class="watch" @dragstart="handleDragStart" @dragend="handleDragEnd" draggable="true">
    <div class="watch__navbar">
      <button class="watch__navbar__btn" v-if="windowMode" @click="handleWindowMode">
        <font-awesome-icon :icon="['far', 'window-minimize']" size="1x"/>
      </button>
      <button class="watch__navbar__btn" v-else @click="handleWindowMode">
        <font-awesome-icon :icon="['far', 'window-maximize']" size="1x"/>
      </button>
    </div>
    <div class="watch__time__box">
      <div class="watch__time" >
        <span class="watch__time__min">00</span>
        <span>:</span>
        <span class="watch__time__sec">00</span>
        <span>.</span>
        <span class="watch__time__mSec">00</span>
      </div>
      <div class="watch__btn__box">
        <button v-if="!isStarted && !isStoped" class="watch__btn watch__btn__start" @click="handleStart">START</button>
        <button v-if="isStarted && !isStoped" class="watch__btn watch__btn__stop" @click="handleStop">STOP</button>
        <button v-if="!isStarted && isStoped" class="watch__btn watch__btn__restart" @click="handleRestart">RESTART</button>
        <button v-if="!isStarted && isStoped" class="watch__btn watch__btn__reset" @click="handleReset">RESET</button>
      </div>
    </div>
  </div>
</template>

<script>
let min=0, sec=0, mSec=0;
let stTime=0, edTime=0, timerStart=null;
function addZero(num){
  return num < 10 ? '0'+num : ''+ num
}
export default {
  name:'StopWatch',
  data(){
    return{
      windowMode:true,
      isStarted: false,
      isStoped: false,
    }
  },
  mounted(){
    const watch = document.querySelector('.watch');
    watch.style.transform=`translate(${window.innerWidth-430}px,100px)`;
  },
  methods:{
    handleWindowMode(){
      this.windowMode=!this.windowMode;
      const watchBox=document.querySelector('.watch__time__box');
      if(!this.windowMode)
        watchBox.style.display='none';
      else
        watchBox.style.display='flex';
    },
    handleDragEnd(e){
      const watch = document.querySelector('.watch');
      watch.style.transform=`translate(${e.pageX-125}px,${e.pageY-100}px)`;
    },
    handleDragStart(e){
      // const watch = document.querySelector('.watch__init');
      // if(watch)
      //   watch.classList.toggle('watch__init');
      //   watch.style.transform=`translate(${e.pageX-125}px,${e.pageY-75}px)`;
    },
    handleStart(){
      stTime=Date.now();
      timerStart = setInterval(function() {
        const nowTime = new Date(Date.now() - stTime)
        min = addZero(nowTime.getMinutes())
        sec = addZero(nowTime.getSeconds())
        mSec = addZero(Math.floor(nowTime.getMilliseconds() / 10))
        document.querySelector('.watch__time__min').innerText=min;
        document.querySelector('.watch__time__sec').innerText=sec;
        document.querySelector('.watch__time__mSec').innerText=mSec;
      }, 1);
      this.isStarted=true;
      this.isStoped=false;
    },
    handleStop(){
      clearInterval(timerStart);
      edTime=Date.now();
      this.isStarted=false;
      this.isStoped=true;
    },
    handleRestart(){
      stTime+=Date.now()-edTime;
      timerStart = setInterval(function() {
        const nowTime = new Date(Date.now() - stTime)
        min = addZero(nowTime.getMinutes())
        sec = addZero(nowTime.getSeconds())
        mSec = addZero(Math.floor(nowTime.getMilliseconds() / 10))
        document.querySelector('.watch__time__min').innerText=min;
        document.querySelector('.watch__time__sec').innerText=sec;
        document.querySelector('.watch__time__mSec').innerText=mSec;
      }, 1);
      this.isStarted=true;
      this.isStoped=false;
    },
    handleReset(){
      stTime=0;
      min=0;
      sec=0;
      mSec=0;
      timerStart=null;
      document.querySelector('.watch__time__min').innerText='00';
      document.querySelector('.watch__time__sec').innerText='00';
      document.querySelector('.watch__time__mSec').innerText='00';
      this.isStarted=false;
      this.isStoped=false;
    },
  }
}
</script>

<style lang="scss" scoped>
.watch{
  width: 250px;
  // min-height: 150px;
  background-color: var(--color-mainBlue);
  border: 2px solid var(--color-mainBlue);
  border-radius: 16px;
  // display:flex;
  // flex-direction: column;
  // justify-content: space-around;
  // align-items: center;
  position: absolute;
  z-index: 1000;
  &__navbar{
    width: 100%;
    height: 30px;
    display: flex;
    border-radius: 16px;
    justify-content: flex-end;
    border: 3px solid var(--color-mainBlue);
    background-color: var(--color-mainBlue);

    &__btn{
      padding: 0 8px;
      background-color: var(--color-white);
      border-radius: 16px;
      color: var(--color-mainBlue);
    }
  }
  &__time{
    font-family: "AppleSDGothicNeoB";
    text-align: center;
    &__box{
      border-bottom-left-radius: 16px;
      border-bottom-right-radius: 16px;
      background-color: var(--color-white);
      height: 120px;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }
    &__min{
      font-size: var(--font-size-28);
    }
    &__sec{
      font-size: var(--font-size-28);
    }
    &__mSec{
      font-size: var(--font-size-24);
    }
  }
  &__btn{
    font-size: var(--font-size-16);
    color: var(--color-white);
    padding: 0.5rem 1rem;
    border-radius: 16px;
    &__box{
      display: flex;
      justify-content: center;
      align-items: center;
      
    }
    &__start{
      background-color: var(--color-mainBlue);
      border: 2px solid var(--color-mainBlue);
    }
    &__stop{
      background-color: var(--color-red);
      border: 2px solid var(--color-red);
    }
    &__restart{
      background-color: var(--color-mainBlue);
      border: 2px solid var(--color-mainBlue);
      margin-right: 1rem;
    }
    &__reset{
      color: var(--color-mainBlue);
      background-color: var(--color-white);
      border: 2px solid var(--color-mainBlue);
    }
  }
}
</style>