<template>
  <div class="code__wrap">
    <div class="code__title">문제 제목 :
      <input type="text" id="code__title" v-model="content.proTitle" autofocus>
    </div>
    <div class="code__box">
      <textarea name="code__problem" id="code__problem" cols="30" rows="10" v-model="content.proContent"></textarea>
    </div>
    <div class="code__example">
      예제 입력
    </div>
    <div class="code__box">
      <textarea name="code__problem" id="code__input" cols="30" rows="10" v-model="content.proInput"></textarea>
    </div>
    <div class="code__example">
      예제 출력
    </div>
    <div class="code__box">
      <textarea name="code__problem" id="code__output" cols="30" rows="10" v-model="content.proOutput"></textarea>
    </div>

  </div>
</template>

<script>
export default {
  name:'UpdateProblem',
  props:{
    content: Object,
  },
}
</script>

<style lang="scss" scoped>
.code__wrap{
  width:80%;
  margin: 0 auto;

}
.code__title{
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
.code__box{
  margin-bottom: 1rem;
  border-radius: 1rem;
  border: 1px solid var(--color-grey-6);
  font-family: "AppleSDGothicNeoB";
  
  textarea{
    padding: 1rem;
    width: 100%;
  }
}
.code__example{
  padding: 1rem 0;
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-18);
  color: var(--color-grey-1);
}
</style>