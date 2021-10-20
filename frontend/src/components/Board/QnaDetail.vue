<template>
  <div class="qna__wrap">
    <div class="qna__question__wrap">
      <div class="qna__question__header">
        <div class="qna__question__header__left">
          <button class="qna__question__header__listbtn" @click="handleClickList">
            <font-awesome-icon :icon="['fas', 'chevron-left']" size="1x"/>
            글 목록보기
          </button>
        </div>
        <div v-if="user.userId===question.user.userId" class="qna__question__header__right">        
          <button class="qna__question__header__editbtn" @click="handleClickEdit">
            <font-awesome-icon :icon="['fas', 'pencil-alt']" size="1x" />
            수정
          </button>
          <Modal
            v-model="showModal"
            title="질문 수정"
            modal-class="scrollable-modal"
          >
            <div class="scrollable-content">
              <UpdateEditor :content="question"/>
            </div>
            <div class="row scrollable-modal-footer">
              <div class="modal_btn_box">
                <button
                  class="modal_btn"
                  type="button"
                  @click="handleSubmit"
                >
                  질문 수정하기
                </button>            
              </div>
            </div>
          </Modal>

          <button class="qna__question__header__deletebtn" @click="handleClickDelete">
            <font-awesome-icon :icon="['far', 'trash-alt']" size="1x" />
            삭제
          </button>
        </div>
      </div>
      <div class="qna__question__box">
        <div class="qna__question__profile">
          <div class="qna__question__profile__image">
            <img :src="question.user.userImg" alt="" v-if="question.user && question.user.userImg!==''">
            <img src="../../assets/profile.png" alt="" v-else>
          </div>
          <div>
            <div class="qna__question__profile__name">
              {{question.user && question.user.userNickname}}
            </div>
            <div class="qna__question__profile__date">
              <font-awesome-icon :icon="['far', 'calendar-alt']" size="1x" />
              {{$moment(question.qnaDate).format('YYYY-MM-DD')}}
            </div>
          </div>
        </div>
        <div class="qna__question__title">
          {{question.qnaTitle}}
        </div>
        <div class="qna__question__problem" v-html="question.qnaContent">
        </div>
      </div>
    </div>
    <div class="qna__answer__wrap">
      <div class="qna__answer__header">
        <div class="qna__answer__profile">
          <div class="qna__answer__profile__image">
            <img :src="user.userImg" alt="" v-if="user && user.userImg!==''">
            <img src="../../assets/profile.png" alt="" v-else>
          </div>
          <div class="qna__answer__profile__name">
            {{user && user.userNickname}}
          </div>
        </div>
        <div class="qna__answer__btn__box">
          <button class="modal_btn" @click="handleClickCreateAnswer">답글 작성하기</button>
        </div>
      </div>
      <div class="qna__answer__form">
        <CreateAnswer/>
      </div>
      <div class="qna__answer__count" v-if="question.commentDTOList && question.commentDTOList.length>0">
        <span class="qna__answer__count__text">답변 </span>
        <span class="qna__answer__count__cnt">{{question.commentDTOList.length}}개</span>
      </div>
      <Answer
        v-for="answer in question.commentDTOList.slice(0,idx)"
        :key="answer.comId"
        :answer="answer"
        @handleClickCommentDelete="handleClickCommentDelete"
      />
      <div class="qna__answer__more" v-if="idx < question.commentDTOList.length">
        <button class="qna__answer__more__btn" @click="handleMore">
          더 보기<br/>
          <font-awesome-icon :icon="['fas', 'chevron-down']" size="1x"/>
        </button>
      </div>      
    </div>
  </div>
</template>

<script>
import UpdateQuestion from './UpdateQuestion.vue';
import http from '../../util/http-common.js';
import UpdateEditor from './UpdateEditor.vue';
import CreateAnswer from './CreateAnswer.vue';
import Answer from './Answer.vue';
export default {
  name:'QnaDetail',
  components:{
    UpdateQuestion,
    UpdateEditor,
    CreateAnswer,
    Answer,
  },
  data(){
    return{
      question:{},
      showModal: false,
      user:{},
      idx:4,
    }
  },
  created(){
    this.user=this.$store.getters.getUser;
    http.get(`v1/qna/detail/${this.$route.params.id}`)
    .then((res)=>{
      if(res.status===200){
        this.question=res.data.data;
      }
    })
    .catch((err)=>{
      console.error(err);
    })
  },
  mounted(){
    // document.querySelector('.editor__content').innerHTML=this.content.problem;
  },
  methods:{
    handleMore(){
      this.idx+=4;
    },    
    handleClickList(){
      this.$router.push({
        name:'QnaBoard'
      });
    },
    handleClickEdit(){
      this.showModal = true;
      const content=this.question.qnaContent;
      const title=this.question.qnaTitle;
      setTimeout(function(){
        document.querySelector('.scrollable-content .editor__content .ProseMirror').innerHTML=content;
        document.querySelector('.editor__title__input').value=title;
      },1);
    },
    handleClickDelete(){
      if(this.user.userId!==this.question.user.userId)  return;
      http.delete(`v1/qna/${this.question.qnaId}`)
      .then((res)=>{
        this.$router.push({
          name:'QnaBoard',
        })
      })
      .catch((err)=>{
        console.error(err);
      })
    },
    handleSubmit(){
      const qnaContent = document.querySelector('.ProseMirror').innerHTML;
      const qnaTitle = document.querySelector('.editor__title__input').value;    
      const data = {
        qnaTitle,
        qnaContent,
        qnaId:this.question.qnaId,
        roomId: this.$store.getters.getSchoolId,
        userId: this.user.userId,
      };
      http.put('v1/qna', JSON.stringify(data))
      .then((res)=>{
        if(res.data.status==="success"){
          this.question=res.data.data;
          this.showModal=false;
        } else{
          console.error(res.data.data);
        }
      })  
      .catch((err)=>{
        console.error(err);
        this.showModal=false;
      })
    },
    handleClickCreateAnswer(){
      const comContent = document.querySelector('.ProseMirror').innerHTML;
      const data = {
        comContent,
        qnaId:this.question.qnaId,
        roomId: this.$store.getters.getSchoolId,
        userId: this.user.userId,
      };
      http.post('v1/comment', JSON.stringify(data))
      .then((res)=>{
        if(res.data.status==="success"){
          this.question.commentDTOList=res.data.data;
        } else{
          console.error(res.data.data);
        }
      })
      .catch((err)=>{
        console.error(err);
      })
      document.querySelector('.ProseMirror').innerHTML='';
    },
    handleClickCommentDelete(id){
      http.delete(`v1/comment/${id}`)
      .then((res)=>{
        if(res.data.status==="success"){
          this.question.commentDTOList=this.question.commentDTOList.filter(comment => comment.comId!==id);
        }
      })
      .catch((err)=>{
        console.error(err);
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.qna__question{
  &__wrap{
    width: 80%;
    margin: auto;
    margin-bottom: 2rem;
  }
  &__header{
    display: flex;
    justify-content: space-between;
    padding: 1em 0;
    align-items: center;
  }
  &__header__left &__header__listbtn{
    font-size: var(--font-size-14);
    font-family: "AppleSDGothicNeoR";
    border: 1px solid var(--color-grey-2);
    padding: 0.25rem;
    color: var(--color-grey-2);
  }
  &__header__right &__header__editbtn{
    font-family: "AppleSDGothicNeoB";
    font-size: var(--font-size-14);
    padding: 0.25rem;
    color: var(--color-grey-2);
  }
  &__header__right &__header__deletebtn{
    font-family: "AppleSDGothicNeoB";
    font-size: var(--font-size-14);
    padding: 0.25rem;
    color: var(--color-pink);
  }
  &__box{
    padding: 0 1rem;
    border: 1px solid var(--color-grey-6);
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
  &__title{
    padding: 1rem 0;
    font-family: "AppleSDGothicNeoB";
    font-size: var(--font-size-20);
  }
  &__problem{
    font-family: "AppleSDGothicNeoR";
    padding: 1rem 0;
    font-size: var(--font-size-14);
    color: var(--color-grey-2);
    // padding: 1rem 1.5rem;
    // border: 1px solid var(--color-grey-4);
  }
  &__btn__box{
    display: flex;
    justify-content: flex-end;
  }
}
.qna__answer{
  &__wrap{
    background-color: #FAFBFC;
    padding-bottom: 2rem;
  }
  &__header{
    width: 80%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1em 0;
    margin:auto;
  }
  &__form{
    padding-bottom: 2rem;
    width: 80%;
    margin: auto;
  }
  &__profile{
    
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
  }
  &__btn__box{
    display: flex;
    justify-content: flex-end;
  }
  &__count{
    width: 80%;
    margin: auto;
    &__text{
      font-family: "AppleSDGothicNeoR";
    }
    &__cnt{
      font-family: "AppleSDGothicNeoB";
    }
  }
  &__more{
    display: flex;
    justify-content: center;
    align-items: center;
    &__btn{
      font-family: "AppleSDGothicNeoB";
    }
  }
}
.modal_btn_box{
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal_btn{
  padding: 0.5rem 1rem;
  border-radius: var(--font-size-12);
  border: 2px solid var(--color-mainBlue);
  background-color: var(--color-white);
  font-family: "AppleSDGothicNeoB";
  color: var(--color-mainBlue);
}
</style>