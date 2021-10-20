<template>
  <div class="question__wrap">
    <div class="question__title">질문 제목 :
      <input type="text" id="question__title" v-model="question.qnaTitle" autofocus>
    </div>
    <div class="question__box">
      <textarea v-model="question.qnaContent" name="question__content" id="question__content" cols="30" rows="15"></textarea>
    </div>
  </div>
</template>

<script>
export default {
  name:'UpdateQuestion',
  props:{
    question: Object,
  },  
}
</script>

<style lang="scss" scoped>
.question{
  &__wrap{
    width:80%;
    margin: 0 auto;
  }
  &__title{
    display: flex;
    align-items: center;
    padding: 1rem 0;
    font-family: "AppleSDGothicNeoB";
    font-size: var(--font-size-20);
    color: var(--color-grey-1);
    input{
      width: 70%;
      padding: 0.5rem;
    }
  }

  &__box{
    margin-bottom: 1rem;
    border-radius: 1rem;
    border: 1px solid var(--color-grey-6);
    font-family: "AppleSDGothicNeoB";
    
    textarea{
      padding: 1rem;
    }
  }
}
</style>