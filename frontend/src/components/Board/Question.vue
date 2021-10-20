<template>
  <div class="q__wrap" @click="handleClickQustion">
    <div class="q__profile">
      <div class="q__profile__image">
        <img :src="question.user.userImg" alt="" v-if="question.user.userImg!==''">
        <img src="../../assets/profile.png" alt="" v-else>
      </div>
      <div>
        <div class="q__profile__name">{{question.user.userNickname}}</div>
        <div class="q__profile__date">
          <font-awesome-icon :icon="['far', 'calendar-alt']" size="1x" />
          {{$moment(question.qnaData).format('YYYY-MM-DD')}}
        </div>
      </div>
    </div>
    <div class="q__question__box">
      <div class="q__question">
        <div class="q__question__title">
          {{question.qnaTitle}}
        </div>
        <div :id="question.qnaId" class="q__question__content">
        </div>
        <div class="q__question__answer">
          <font-awesome-icon :icon="['far', 'comment-dots']" size="1x" />
          {{question.commentDTOList && question.commentDTOList.length}}개
        </div>
      </div>
      <div class="q__question__img">
        <img v-if="imgSrc!==''" :src="imgSrc" alt="">
        <div v-if="imgCnt>0" class="q__question__img__cnt">+{{imgCnt}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name:'Question',
  data(){
    return{
      imgSrc:'',
      imgCnt:0,
    }
  },
  props:{
    question: Object,
  },
  created(){
  },
  mounted(){
    const content = document.getElementById(String(this.question.qnaId));
    content.innerHTML=this.question.qnaContent;
    const children = content.childNodes;
    let text='';
    children.forEach((tag)=>{
      if(tag.nodeName==='IMG'){
        if(this.imgSrc===''){
          this.imgSrc=tag.src;
        }
        this.imgCnt++;
      }
      if(tag.innerText!==' ')
        text+=tag.innerText;
    })
    text=text.replace(/(\r\n\t|\n|\r\t)/gm,"");
    text=text.replace(/(\s*)/g,"");
    if(text.length>40){
      content.innerText=text.slice(0,40)+'...';
    } else{
      content.innerText=text
    }
  },
  methods:{
    handleClickQustion(){
      this.$emit('handleClickQustion',this.question.qnaId);
    },
  },
}
</script>

<style lang="scss" scoped>
.q__wrap{
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 80%;
  height: 220px;
  margin: 2rem auto;
  border-radius: 0.5rem;
  padding: 1.5rem;
  border: 1px solid var(--color-grey-8);
  cursor: pointer;
  .q__profile{
    // padding-bottom: 1em;
    display: flex;
    align-items: center;
    &__image{
      width: 40px;
      height: 40px;
      border-radius: 50%;
      display: inline-block;
      img{
        width: 40px;
        height: 40px;
      }
    }
    &__name{
      padding-left: 1rem;
      font-size: var(--font-size-20);
      font-family: "AppleSDGothicNeoB";
    }
    &__date{
      padding-left: 1rem;
      font-family: "AppleSDGothicNeoB";
      font-size: var(--font-size-14);
      color: var(--color-grey-2);
    }
  }
  .q__question__box{
    display:flex;
    align-items: center;
    .q__question{
      width: 90%;
      padding-right: 0.5rem;
      &__title{
        padding: 0.5rem 0;
        font-family: "AppleSDGothicNeoB";
        font-size: var(--font-size-20);
        color: var(--color-grey-1);
      }
      &__content{
        padding: 0.5rem 0;
        font-family: "AppleSDGothicNeoR";
        font-size: var(--font-size-14);
        color: var(--color-grey-2);
      }
      &__answer{
        padding: 0.5rem 0;
        font-family: "AppleSDGothicNeoB";
        color: var(--color-green);
      }
    }
    .q__question__img{
      position:relative;
      width: 70px;
      height: 70px;
      img{
        width: 70px;
        height: 70px;
      }
      &__cnt{
        position: absolute;
        font-family: "AppleSDGothicNeoR";
        font-size: var(--font-size-14);
        color: var(--color-white);
        background-color: var(--color-grey-1);
        padding: 0.25rem;
        bottom: 0;
        right: 0;
      }
    }
  }
}

// @media screen and (max-height: 800px) {
//   .scrollable-modal .vm-content .scrollable-content{
//     max-height: 500px;
//   }
// }
// @media screen and (max-height: 670px) {
//   .scrollable-modal .vm-content .scrollable-content{
//     max-height: 400px;
//   }
// }
// @media screen and (max-height: 570px) {
//   .scrollable-modal .vm-content .scrollable-content{
//     max-height: 300px;
//   }
// }
// @media screen and (max-height: 470px) {
//   .scrollable-modal .vm-content .scrollable-content{
//     max-height: 250px;
//   }
// }
</style>