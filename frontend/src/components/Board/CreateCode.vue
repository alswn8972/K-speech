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
          {{content.user.userNickname}}
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

    <div class="code__example">
      <div class="code__example__box">
        <div class="code__example__title">
          예제 입력
        </div>
        <div class="code__example__input">
          <div v-for="(input,idx) in content.proInput.split('\n')" :key="idx">{{input}}</div>
        </div>
      </div>
      <div class="code__example__box">
        <div class="code__example__title">
          예제 출력
        </div>
        <div class="code__example__output" >
          <div v-for="(output,idx) in content.proOutput.split('\n')" :key="idx">{{output}}</div>
        </div>
      </div>
    </div>
    <div class="code__editor__box">
      <select name="code__languages" id="code__languages" v-model="mode" class="code__select">
        <option value="c">C</option>
        <option value="cpp">C++</option>
        <option value="java">JAVA</option>
        <option value="python">PYTHON</option>
      </select>
      <select name="code__languages" id="code__languages" v-model="theme" class="code__select">
        <option value="vs">VS</option>
        <option value="vs-dark">VS-DARK</option>
      </select>
      <m-monaco-editor v-model="code" :mode="mode" :theme="theme" @init="handleInit"></m-monaco-editor>
    </div>
    <div class="code__example">
      <div class="code__example__box">
        <div class="code__example__title">
          입력
        </div>
        <textarea name="" id="" cols="30" rows="10" class="code__example__input" v-model="input" placeholder="Input 값을 입력해주세요"></textarea>
      </div>
      <div class="code__example__box">
        <div class="code__example__title">
          출력
        </div>
        <textarea name="" id="" cols="30" rows="10" class="code__example__output" v-model="output" readonly></textarea>
      </div>
    </div>
    <div class="code__btn__box">
      <button class="code__btn code__btn__white" @click="handleCompile">컴파일</button>
      <button class="code__btn code__btn__blue" @click="handleSubmit">제출하기</button>      
    </div>
  </div>
</template>

<script>
import UpdateProblem from './UpdateProblem.vue';
import http from '@/util/http-common.js';
import Swal from 'sweetalert2'
export default {
  name:'CreateCode',
  components:{
    UpdateProblem,
    Swal,
  },
  data(){
    return{
      isLoading:true,
      content:{
        "proId": null,
        "user": {
          "userId": "",
          "userPw": "",
          "userNickname": "",
          "userPhone": "",
          "userImg": "",
          "userLank": "",
          "userPoint": null
        },
        "proTitle": "",
        "proContent": "",
        "proInput": "",
        "proOutput": "",
        "proDate": ""
      },
      code:"",
      memory:"",
      time:"",
      result:"",
      input:"",
      output:"",
      mode:"python",
      theme:"vs-dark",
    }
  },
  created(){
    http.get(`v1/pro/detail/${this.$route.params.id}`)
    .then((res)=>{
      if(res.status===200){
        this.content=res.data.data;
      }
    })
    .catch((err)=>{
      console.error(err);
    })
  },
  computed:{
    wrapClass(){
      if(this.isLoading===false)
        return "code__wrap"
      return "code__wrap darkness"
    }
  },
  mounted(){

  },
  beforeDestroy(){
  },
  methods:{
    handleInit(editor,editorDom,monaco){
      this.editor=editor;
    },
    handleClickList(){
      this.$router.push({
        name:'ProblemDetail',
        params:{
          id:this.content.proId, 
        }
      });
    },
    handleSubmit(){
      const compiler={
        'c':11,
        'cpp':1,
        'java':10,
        'python':116
      };
      this.code=this.editor.getValue();
      this.$store.commit('setIsSubmit',true);
      const data={
        source: this.code,
        compilerId: compiler[this.mode],
        input: this.input,
      }
      http.post('v1/compile',JSON.stringify(data))
        .then((res)=>{
          const json = JSON.parse(res.data.data);
          setTimeout(()=>{
            http.get(`v1/result/${json.id}`)
              .then((res)=>{
                const json2 = JSON.parse(res.data.data);
                if(json2.result.status.code===15){
                  http.get(`v1/output/${json.id}`)
                    .then((res)=>{
                      const output1=res.data.data.replace(/(\r\n\t|\n|\r\t)/gm,"").replace(/(\s*)/g,"");
                      const output2=this.content.proOutput.replace(/(\r\n\t|\n|\r\t)/gm,"").replace(/(\s*)/g,"");
                      if(output1===output2){
                        this.result="성공";
                        http.post('v1/solve', JSON.stringify({proId : this.content.proId, userId: this.$store.getters.getUserId})) 
                          .then(res=>{
                            if(res.data.data===1){
                              const user = this.$store.getters.getUser;
                              const point = user.userPoint+50;
                              const rankList = ['bronze3','bronze2','bronze1','silver3','silver2','silver1','gold3','gold2','gold1'];
                              const rank = rankList[point%500];
                              this.$store.commit('setUser',{...user, userPoint: point, userLank: rank});
                            }
                          })
                          .catch(err=>{
                            this.$store.commit('setIsSubmit',false);
                            console.error(err);
                          })
                      } else{
                        this.result="실패";
                      }
                      this.memory = json2.result.memory;
                      this.time = json2.result.time;
                      const data = {
                        codeContent: this.code,
                        codeLan : this.mode,
                        codeMemory: `${this.memory}kB`,
                        codeTime: `${this.time}s`,
                        result: this.result,
                        roomId: this.$store.getters.getSchoolId,
                        proId: this.content.proId,
                        userId: this.$store.getters.getUserId,
                      };                      
                      http.post('v1/code', JSON.stringify(data))
                        .then(res=>{
                          if(res.status===200){
                            this.$store.commit('setIsSubmit',false);
                            this.$router.push({
                              name: 'ProblemDetail',
                              params:{
                                id: this.content.proId,
                              }
                            })
                          }
                        })
                        .catch(err => {
                          console.error(err);
                          this.$store.commit('setIsSubmit',false);
                        })
                    })
                    .catch((err)=>{
                      console.error(err);
                      this.$store.commit('setIsSubmit',false);
                    })
                }
              })
              .catch((err)=>{
                console.error(err);
                this.$store.commit('setIsSubmit',false);
              })

          },5000)
        })
        .catch((err)=>{
          console.error(err);
          this.$store.commit('setIsSubmit',false);
        })
    },
    handleCompile(){
      const compiler={
        'c':11,
        'cpp':1,
        'java':10,
        'python':116
      };
      this.code=this.editor.getValue();
      const data={
        source: this.code,
        compilerId: compiler[this.mode],
        input: this.input,
      }
      http.post('v1/compile',JSON.stringify(data))
        .then((res)=>{
          const json = JSON.parse(res.data.data);
          this.output+='소스 코드를 컴파일 중 입니다...\n';
          setTimeout(()=>{
            http.get(`v1/result/${json.id}`)
              .then((res)=>{
                const json2 = JSON.parse(res.data.data);
                if(json2.result.status.code===15){
                  http.get(`v1/output/${json.id}`)
                    .then((res)=>{
                      this.output+=`메모리 용량 : ${json2.result.memory}kB, 실행 시간 : ${json2.result.time}s\n`
                      this.output+=`${res.data.data}\n`;
                    })
                    .catch((err)=>{
                      console.error(err);
                    })
                } else{
                  http.get(`v1/error/${json.id}`)
                    .then((res)=>{
                      this.output+=`error : ${json2.result.status.name}\n`;
                      this.output+=`${res.data.data}\n`;
                    })
                    .catch((err)=>{
                      console.error(err);
                    })
                }
              })
              .catch((err)=>{
                console.error(err);
              })
          },5000)
        })
        .catch((err)=>{
          console.error(err);
        })
    },
  }
}
</script>
<style lang="scss">
.monaco-editor,
.overflow-guard{
  width: 100% !important;
}
// .minimap{
//   width:50% !important;
//   canvas{
//     width:50% !important;
//   }
//   &-slider{
//     width: 50% !important;
//     &-horizontal{
//       width: 50% !important;
//     }
//   }
// }
</style>
<style lang="scss" scoped>
.code__wrap{
  position:relative;
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
.code__example{
  padding: 2rem 0;
  display: flex;
  justify-content: space-between;
  font-family: "AppleSDGothicNeoB";
  &__box{
    width: 48%;
  }
  &__title{
    padding: 1rem 0;
    font-size: var(--font-size-18);
  }
  &__input, &__output{
    padding: 1rem;
    width: 100%;
    background-color: var(--color-grey-7);
    border: 1px solid var(--color-grey-8);
    div{
      padding: 0.25rem 0;
    }
  }
}
.code__editor{
  &__box{
    padding: 0.5rem;
    border: 1px solid var(--color-grey-8);
  }
}
.m-monaco-editor div{
  width: 100% !important;
}
.code__btn{
  font-family: "AppleSDGothicNeoB";
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
  border: 2px solid var(--color-mainBlue);
  margin-left: 1rem;
  &__box{
    display: flex;
    justify-content: flex-end;
    margin-bottom: 1rem;
  }
  &__white{
    color: var(--color-mainBlue);
    background-color: var(--color-white);
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