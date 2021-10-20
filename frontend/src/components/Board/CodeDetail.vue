<template>
  <div class="code__wrap">
    <div class="code__header">
      <div class="code__header__left">
        <button class="code__header__listbtn" @click="handleClickList">
          <font-awesome-icon :icon="['fas', 'chevron-left']" size="1x"/>
          제출 목록보기
        </button>
      </div>
    </div>

    <div class="code__profile">
      <div class="code__profile__image">
        <img :src="content.user.userImg" alt="" v-if="content.user && content.user.userImg!==''">
        <img src="../../assets/profile.png" alt="" v-else>
      </div>
      <div>
        <div class="code__profile__name">
          {{content.user && content.user.userNickname}}
        </div>
        <div class="code__profile__date">
          <font-awesome-icon :icon="['far', 'calendar-alt']" size="1x" />
          {{$moment(content.proDate).format('YYYY-MM-DD')}}
        </div>
      </div>
    </div>
    <div class="code__title">
      {{content.proTitle}}
    </div>
    <div class="code__problem">
      {{content.proContent}}
    </div>
    <div class="code__editor__box">
      <select name="code__languages" id="code__languages" v-model="code.codeLan" class="code__select" disabled>
        <option value="c">C</option>
        <option value="cpp">C++</option>
        <option value="java">JAVA</option>
        <option value="python">PYTHON</option>
      </select>
      <m-monaco-editor v-if="code.codeContent" v-model="code.codeContent" :mode="code.codeLan" theme="vs-dark" :readOnly="trueState"></m-monaco-editor>
    </div>
    <div v-if="user.userId===code.user.userId" class="code__btn__box">
      <button class="code__btn code__btn__blue" @click="handleClickUpdate">수정하기</button>      
    </div>
  </div>
</template>

<script>
import http from '@/util/http-common.js';
export default {
  name:'CodeDetail',
  components:{
  },
  data(){
    return{
      content:{},
      code:{},
      trueState:true,
      user:{},
    }
  },
  created(){
    this.user=this.$store.getters.getUser;
    http.get(`v1/pro/detail/${this.$route.params.id}`)
    .then(res=>{
      this.content=res.data.data;
      for(let i=0; i<this.content.codeList.length; i++){
        if(this.content.codeList[i].codeId===Number(this.$route.params.codeId)){
          this.code=this.content.codeList[i];
          break;
        }
        
      }
    })
    .catch(err=>{
      console.error(err);
    })
  },
  mounted(){
  },
  methods:{
    handleClickList(){
      this.$router.push({
        name:'ProblemDetail',
        params:{
          id:this.$route.params.id,
        }
      })
    },
    handleClickUpdate(){
      this.$router.push({
        name:'UpdateCode',
        params:{
          id: this.$route.params.id,
          codeId: this.$route.params.codeId,
          userId: this.user.userId,
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.code__wrap{
  width: 80%;
  margin: auto;
}
.code__header{
  display: flex;
  justify-content: space-between;
  padding: 1em 0;
  align-items: center;
}
.code__header__left .code__header__listbtn{
  font-size: var(--font-size-14);
  font-family: "AppleSDGothicNeoR";
  border: 1px solid var(--color-grey-2);
  padding: 0.25rem;
  color: var(--color-grey-2);
}
.code__header__right .code__header__editbtn{
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-14);
  padding: 0.25rem;
  color: var(--color-grey-2);
}
.code__header__right .code__header__deletebtn{
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-14);
  padding: 0.25rem;
  color: var(--color-pink);
}
.code__profile{
  padding: 1em 0;
  display: flex;
  align-items: center;
  
  &__image{
      width: 40px;
      height: 40px;
      border-radius: 50%;
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

.code__title{
  padding: 1rem 0;
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-20);
}
.code__problem{
  font-family: "AppleSDGothicNeoB";
  padding: 1rem 0;
  // padding: 1rem 1.5rem;
  // border: 1px solid var(--color-grey-4);
}
.modal_btn_box{
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal_btn{
  padding: 1rem 2rem;
  border-radius: var(--font-size-12);
  background-color: var(--color-mainBlue);
  font-family: "AppleSDGothicNeoB";
  color: var(--color-white);
}
.code__editor{
  &__box{
    padding: 0.5rem;
    border: 1px solid var(--color-grey-8);
    margin: 1rem 0;
  }
}
.code__btn{
  font-family: "AppleSDGothicNeoB";
  padding: 1rem 3rem;
  border-radius: 0.25rem;
  border: 2px solid var(--color-mainBlue);
  margin-left: 1rem;
  &__box{
    display: flex;
    justify-content: flex-end;
    margin-bottom: 1rem;
  }
  &__blue{
    color: var(--color-white);
    background-color: var(--color-mainBlue);
  }
}
.code__select{
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-14);
  padding: 0.5rem 1rem;
}
.code__block{
  width: 100%;
}
</style>