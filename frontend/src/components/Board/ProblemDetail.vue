<template>
  <div class="detail__wrap">
    <div class="detail__header">
      <div class="detail__header__left">
        <button class="detail__header__listbtn" @click="handleClickList">
          <font-awesome-icon :icon="['fas', 'chevron-left']" size="1x"/>
          글 목록보기
        </button>
      </div>
      <div v-if="user.userId && content.user && user.userId===content.user.userId" class="detail__header__right">        
        <button class="detail__header__editbtn" @click="handleClickEdit">
          <font-awesome-icon :icon="['fas', 'pencil-alt']" size="1x" />
          수정
        </button>
        <Modal
          v-model="showModal"
          title="글 수정"
          modal-class="scrollable-modal"
        >
          <div class="scrollable-content">
            <UpdateProblem :content="content"/>
          </div>
          <div class="row scrollable-modal-footer">
            <div class="modal_btn_box">
              <button
                class="modal_btn"
                type="button"
                @click="handleSubmit"
              >
                글 수정하기
              </button>            
            </div>
          </div>
        </Modal>

        <button class="detail__header__deletebtn" @click="handleClickDelete">
          <font-awesome-icon :icon="['far', 'trash-alt']" size="1x" />
          삭제
        </button>
      </div>
    </div>

    <div class="detail__profile">
      <div class="detail__profile__image">
        <img :src="content.user.userImg" alt="" v-if="content.user && content.user.userImg!==''">
        <img src="../../assets/profile.png" alt="" v-else>
      </div>
      <div>
        <div class="detail__profile__name">
          {{content.user && content.user.userNickname}}
        </div>
        <div class="detail__profile__date">
          <font-awesome-icon :icon="['far', 'calendar-alt']" size="1x" />
          {{content.proDate && $moment(content.proDate).format('YYYY-MM-DD')}}
        </div>
      </div>
    </div>
    <div class="detail__title">
      {{content.proTitle && content.proTitle}}
    </div>
    <div class="detail__problem">
      {{content.proTitle && content.proContent}}
    </div>
    <div class="detail__btn__box">
      <button class="modal_btn" @click="handleClickCreate">코드 작성하기</button>
    </div>
    <div class="detail__example">
      <div class="detail__example__box">
        <div class="detail__example__title">
          예제 입력
        </div>
        <div class="detail__example__input">
          <div v-for="(input,idx) in content.proInput && content.proInput.split('\n')" :key="idx">{{input}}</div>
        </div>
      </div>
      <div class="detail__example__box">
      <div class="detail__example__title">
          예제 출력
        </div>
        <div class="detail__example__output">
          <div v-for="(output,idx) in content.proInput && content.proOutput.split('\n')" :key="idx">{{output}}</div>
        </div>
      </div>
    </div>
    <div class="detail__code__table">
      <div class="detail__code__table__cnt">
        제출 {{content.codeList && content.codeList.length}}개
      </div>
      <table>
        <th>닉네임</th>
        <th>메모리</th>
        <th>시간</th>
        <th>통과여부</th>
        <th>언어</th>
        <th>제출날짜</th>
        <tr v-for="(code, idx) in content.codeList.slice((this.currIdx-1)*5,this.currIdx*5)" :key="idx" @click="function(){ handleClickCode(content.proId,code.codeId) }">
          <td>
            <img
              v-if="user.userLank==='bronze1'"
              src="@/../public/Image/level/level_bronze1.png"
              id="level-icon"
            />
            <img
              v-else-if="user.userLank==='bronze2'"
              src="@/../public/Image/level/level_bronze2.png"
              id="level-icon"
            />
            <img
              v-else-if="user.userLank==='bronze3'"
              src="@/../public/Image/level/level_bronze3.png"
              id="level-icon"
            />
            <img
              v-else-if="user.userLank==='silver1'"
              src="@/../public/Image/level/level_silver1.png"
              id="level-icon"
            />
            <img
              v-else-if="user.userLank==='silver2'"
              src="@/../public/Image/level/level_silver2.png"
              id="level-icon"
            />
            <img
              v-else-if="user.userLank==='silver3'"
              src="@/../public/Image/level/level_silver3.png"
              id="level-icon"
            />
            <img
              v-else-if="user.userLank==='gold1'"
              src="@/../public/Image/level/level_gold1.png"
              id="level-icon"
            />
            <img
              v-else-if="user.userLank==='gold2'"
              src="@/../public/Image/level/level_gold2.png"
              id="level-icon"
            />
            <img
              v-else-if="user.userLank==='gold3'"
              src="@/../public/Image/level/level_gold3.png"
              id="level-icon"
            />
            {{code.user.userNickname}}
          </td>
          <td v-if="code.codeMemory.length>1">{{code.codeMemory.slice(0,code.codeMemory.length-2)}}<span>{{code.codeMemory.slice(code.codeMemory.length-2,)}}</span></td>
          <td v-else><span>{{code.codeMemory}}</span></td>
          <td v-if="code.codeTime.length>1">{{code.codeTime.slice(0,code.codeTime.length-1)}}<span>{{code.codeTime.slice(code.codeTime.length-1,)}}</span></td>
          <td v-else><span>{{code.codeTime}}</span></td>
          <td>{{code.codeResult}}</td>
          <td>{{code.codeLan}}</td>
          <td>{{$moment(code.codeDate).format('YYYY-MM-DD')}}</td>
        </tr>
      </table>
      <div class="pagenation">
        <button class="pagenation__btn pagenation__btn__left" @click="handleClickLeft">
          <font-awesome-icon :icon="['fas', 'chevron-left']" size="1x"/>
        </button>
        <button class="pagenation__btn pagenation__btn__number" v-for="(idx) in btnList.slice(startIdx,startIdx+5)" :key="idx*(-1)" :id="idx" @click="handleClickNumber">{{idx}}</button>
        <button class="pagenation__btn pagenation__right" @click="handleClickRight">
          <font-awesome-icon :icon="['fas', 'chevron-right']" size="1x"/>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import UpdateProblem from './UpdateProblem.vue';
import http from '../../util/http-common.js';
export default {
  name:'ProblemDetail',
  components:{
    UpdateProblem,
  },
  data(){
    return{
      user:{},
      content:{
        codeList:[],
      },
      showModal: false,
      startIdx:1,
      currIdx:1,
      endIdx:0,
      btnList:[],
    }
  },
  created(){
    this.user=this.$store.getters.getUser;
    http.get(`v1/pro/detail/${this.$route.params.id}`)
    .then((res)=>{
      if(res.status===200){
        this.content=res.data.data;
        this.endIdx=Math.ceil(this.content.codeList.length/5);
        this.content.codeList=this.content.codeList.reverse();
        this.btnList=Array(this.endIdx+1).fill().map((a,idx)=> idx);
        setTimeout(()=>{
          document.getElementById(String(this.currIdx)).classList.add('active');
        },100)
      }
    })
    .catch((err)=>{
      console.error(err);
    })
  },
  methods:{
    handleClickLeft(){
      if(this.currIdx===1) return;
      this.currIdx-=1
      if(this.currIdx<this.startIdx){
        this.startIdx--;
        setTimeout(()=>{
          document.getElementById(String(this.currIdx+1)).classList.remove('active');
          document.getElementById(String(this.currIdx)).classList.add('active');
        },30)
      } else{
        document.getElementById(String(this.currIdx+1)).classList.remove('active');
        document.getElementById(String(this.currIdx)).classList.add('active');
      }
      
    },
    handleClickRight(){
      if(this.currIdx===this.endIdx) return;
      this.currIdx+=1
      if(this.currIdx>this.startIdx+4){
        this.startIdx++;
        setTimeout(()=>{
          document.getElementById(String(this.currIdx-1)).classList.remove('active');
          document.getElementById(String(this.currIdx)).classList.add('active');
        },30)
      } else{
        document.getElementById(String(this.currIdx-1)).classList.remove('active');
        document.getElementById(String(this.currIdx)).classList.add('active');
      }

    },
    handleClickNumber(e){
      const num = e.target.innerText;
      document.getElementById(num).classList.add('active');
      document.getElementById(String(this.currIdx)).classList.remove('active');
      this.currIdx=Number(num);
    },
    handleClickList(){
      this.$router.push({
        name:'ProblemBoard'
      });
    },
    handleClickEdit(){
      this.showModal = true;
    },
    handleClickDelete(){
      http.delete(`v1/pro/${this.content.proId}`)
      .then((res)=>{
        if(res.status===200){
          this.$router.push({
            name:'ProblemBoard'
          })
        }
      })
      .catch((err)=>{
        console.error(err);
      })
    },
    handleSubmit(){
      const proTitle=document.querySelector('#code__title').value;
      const proContent=document.querySelector('#code__problem').value;
      const proInput=document.querySelector('#code__input').value;
      const proOutput=document.querySelector('#code__output').value;
      const data = {
        proTitle,
        proContent,
        proInput,
        proOutput,
        proId: this.content.proId,
        userId: this.user.userId,
      };
      http.put('v1/pro', JSON.stringify(data))
      .then((res)=>{
        if(res.status===200){
          this.content=res.data.data;
          this.showModal=false;
        }
      })
      .catch((err)=>{
        console.error(err);
        this.showModal=false;
      })
    },
    handleClickCreate(){
      this.$router.push({
        name:'CreateCode',
      })
    },
    handleClickCode(id,codeId){
      this.$router.push({
        name:'CodeDetail',
        params:{
          id,
          codeId,
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.detail__wrap{
  width: 80%;
  margin: auto;
}
.detail__header{
  display: flex;
  justify-content: space-between;
  padding: 1em 0;
  align-items: center;
}
.detail__header__left .detail__header__listbtn{
  font-size: var(--font-size-14);
  font-family: "AppleSDGothicNeoR";
  border: 1px solid var(--color-grey-2);
  padding: 0.25rem;
  color: var(--color-grey-2);
}
.detail__header__right .detail__header__editbtn{
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-14);
  padding: 0.25rem;
  color: var(--color-grey-2);
}
.detail__header__right .detail__header__deletebtn{
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-14);
  padding: 0.25rem;
  color: var(--color-pink);
}
.detail__profile{
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
.detail__title{
  padding: 1rem 0;
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-20);
}
.detail__problem{
  font-family: "AppleSDGothicNeoB";
  padding: 1rem 0;
  // padding: 1rem 1.5rem;
  // border: 1px solid var(--color-grey-4);
}
.detail__btn__box{
  display: flex;
  justify-content: flex-end;
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
.detail__example{
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
    background-color: var(--color-grey-7);
    border: 1px solid var(--color-grey-8);
    div{
      padding: 0.25rem 0;
    }    
  }
}
.detail__code__table{
  width: 100%;
  margin-bottom: 2rem;
  &__cnt{
    padding: 1rem 0;
    font-family: "AppleSDGothicNeoB";
    
  }
  table{
    width: 100%;
    border-collapse: collapse;
    margin: 0;
    th,td{
      text-align: center;
      margin: 0;
      border: 1px solid var(--color-grey-8);
      padding: 0.5rem 0;
      color: var(--color-grey-2);
    }
    tr{
      cursor: pointer;
    }
    th{
      font-family: "AppleSDGothicNeoM";
      font-size: var(--font-size-14);
      
    }
    td{
      font-family: "AppleSDGothicNeoB";
      span{
        color: var(--color-red);
      }
      img{
        transform: translate(-6px,6px);
        width: 25px;
        height: 25px;
      }
    }
    td:nth-child(1){
      font-size: var(--font-size-18);
      font-family: "AppleSDGothicNeoB";
    }
    td:nth-child(6){
      font-size: var(--font-size-14);
      color: var(--color-grey-3);
    }
  }
}
.pagenation{
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem 0;
  &__btn{
    font-family: "AppleSDGothicNeoB";
    padding: 0.25rem 0.5rem;
    border: 1px solid var(--color-grey-4);
    margin: 0 0.25rem;
    color: var(--color-grey-1);
    vertical-align: middle;
    text-align: center;
  }
  &__btn:hover{
    background-color: var(--color-mainBlue);
    color: var(--color-white);
  }
}
.active{
  background-color: var(--color-mainBlue);
  color: var(--color-white);
}
</style>