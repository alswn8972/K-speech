<template>
  <div class="answer__wrap">
    <div class="answer__header">
      <div class="answer__profile">
        <div class="answer__profile__image">
          <img :src="answer.user.userImg" alt="" v-if="answer.user && answer.user.userImg!==''">
          <img src="../../assets/profile.png" alt="" v-else>
        </div>
        <div>
          <div class="answer__profile__name">
            {{answer.user && answer.user.userNickname}}
          </div>
          <div class="answer__profile__date">
            <font-awesome-icon :icon="['far', 'calendar-alt']" size="1x" />
            {{$moment(answer.comDate).format('YYYY-MM-DD')}}
          </div>
        </div>
      </div>
      <div v-if="user.userId===answer.user.userId" class="answer__btn__box">
        <button class="answer__btn__delete" @click="handleClickCommentDelete">
          <font-awesome-icon :icon="['far', 'trash-alt']" size="1x" />
          삭제
        </button>
      </div>
    </div>
    <div class="answer__problem" v-html="answer.comContent">
    </div>
  </div>
</template>

<script>
export default {
  name:'Answer',
  data(){
    return{
      user:{},
    }
  },
  props:{
    answer: Object,
  },
  created(){
    this.user=this.$store.getters.getUser
  },
  methods:{
    handleClickCommentDelete(){
      if(this.user.userId!==this.answer.user.userId) return;
      this.$emit('handleClickCommentDelete',this.answer.comId);
    },
  }
}
</script>

<style lang="scss" scoped>
.answer{
  &__wrap{
    width: 80%;
    margin: auto;
    margin-top: 0.5rem;
    margin-bottom: 1rem;
    padding: 0 1rem;
    border: 1px solid var(--color-grey-6);
    background-color: var(--color-white)  ;
  }
  &__header{
    display: flex;
    justify-content: space-between;
    // padding: 1em 0;
    align-items: center;
  }
  &__btn__delete{
    font-family: "AppleSDGothicNeoB";
    font-size: var(--font-size-14);
    padding: 0.25rem;
    color: var(--color-pink);
  }
  &__profile{
    padding: 1em 0;
    display: flex;
    align-items: center;
    &__image{
      width: 40px;
      height: 40px;
      border-radius: 50%;
      background-color: var(--color-pink);
      display: inline-block;
    }
    img{
      width: 40px;
      height: 40px;
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
  &__problem{
    font-family: "AppleSDGothicNeoR";
    padding: 1rem 0;
    font-size: var(--font-size-14);
    color: var(--color-grey-2);
    // padding: 1rem 1.5rem;
    // border: 1px solid var(--color-grey-4);
  }
}
</style>