<template>
  <div class="infodetail__wrap">
    <div class="infodetail__header">
      <div class="infodetail__header__left">
        <button class="infodetail__header__listbtn" @click="handleClickList">
          <font-awesome-icon :icon="['fas', 'chevron-left']" size="1x"/>
          글 목록보기
        </button>
      </div>
      <div v-if="user.userId===content.user.userId" class="infodetail__header__right">        
        <button class="infodetail__header__editbtn" @click="handleClickEdit">
          <font-awesome-icon :icon="['fas', 'pencil-alt']" size="1x" />
          수정
        </button>

        <Modal
          v-model="showModal"
          title="글 수정"
          modal-class="scrollable-modal"
        >
          <div class="scrollable-content">
            <UpdateEditor :content="content"/>
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

        <button class="infodetail__header__deletebtn" @click="handleClickDelete">
          <font-awesome-icon :icon="['far', 'trash-alt']" size="1x" />
          삭제
        </button>
      </div>
    </div>

    <div class="infodetail__profile">
      <div class="infodetail__profile__image">
        <img :src="content.user.userImg" alt="" v-if="content.user && content.user.userImg!==''">
        <img src="../../assets/profile.png" alt="" v-else>
      </div>
      <div>
        <div class="infodetail__profile__name">{{content.user.userNickname}}</div>
        <div class="infodetail__profile__date">
          <font-awesome-icon :icon="['far', 'calendar-alt']" size="1x" />
          {{$moment(content.infoDate).format('YYYY-MM-DD')}}
        </div>
      </div>
    </div>

    <div class="infodetail__title">
      {{content.infoTitle}}
    </div>
    <div class="infodetail__content">
        <div class="editor__content"></div>
    </div>
  </div>
</template>

<script>
import UpdateEditor from './UpdateEditor.vue';
import http from '../../util/http-common.js';
// import Swal from 'sweetalert2'
export default {
  name:'ContentDetail',
  components:{
    UpdateEditor,
  },
  data(){
    return{
      user:{},
      content:{
        infoContent:'',
        infoData:'',
        infoId: Number(),
        user: {
          userId:'',
          userImg:'',
          userLank:'',
          userNickname:'',
          userPhone:'',
          userPoint:'',
          userPw:'',
        },
      },
      showModal: false,
    }
  },
  created(){
    this.user=this.$store.getters.getUser;
    http.get(`v1/info/detail/${this.$route.params.id}`)
    .then((res)=>{
      if(res.status===200){
        this.content=res.data.data;
        document.querySelector('.editor__content').innerHTML=this.content.infoContent;
      }
    })
    .catch((err)=>{
      console.error(err);
    })
  },
  mounted(){
    
  },
  methods:{
    handleClickList(){
      this.$router.push({
        name:'InfoBoard'
      })
    },
    handleClickEdit(){
      this.showModal = true;
      const content=this.content.infoContent;
      const title=this.content.infoTitle;
      setTimeout(function(){
        document.querySelector('.editor__content .ProseMirror').innerHTML=content;
        document.querySelector('.editor__title__input').value=title;
      },1);
    },
    handleClickDelete(){
      http.delete(`v1/info/${this.content.infoId}`)
      .then((res)=>{
        if(res.status===200){
          this.$router.push({
            name:'InfoBoard'
          })
        }
      })
      .catch((err)=>{
        console.error(err);
      })
    },
    handleSubmit(){
      const mirror = document.querySelector('.ProseMirror');
      let html = mirror.innerHTML;
      const input = document.querySelector('.editor__title__input');      
      const data = {
        infoTitle : input.value,
        infoContent : html,
        infoId: this.content.infoId,
        userId : this.user.userId,
      };
      http.put('v1/info',JSON.stringify(data))
      .then((res)=>{
        this.content=res.data.data;
        document.querySelector('.editor__content').innerHTML=this.content.infoContent;
        this.showModal=false;
      })
      .catch((err)=>{
        console.error(err);
        this.showModal=false;
      })
    },    
  }
}
</script>

<style lang="scss" scoped>
.infodetail__wrap{
  width: 80%;
  margin: auto;
}
.infodetail__header{
  display: flex;
  justify-content: space-between;
  padding: 1em 0;
  align-items: center;
}
.infodetail__header__left .infodetail__header__listbtn{
  font-size: var(--font-size-14);
  font-family: "AppleSDGothicNeoR";
  border: 1px solid var(--color-grey-2);
  padding: 0.25rem;
  color: var(--color-grey-2);
}
.infodetail__header__right .infodetail__header__editbtn{
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-14);
  padding: 0.25rem;
  color: var(--color-grey-2);
}
.infodetail__header__right .infodetail__header__deletebtn{
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-14);
  padding: 0.25rem;
  color: var(--color-pink);
}
.infodetail__profile{
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

.infodetail__title{
  padding: 1rem 0;
  font-family: "AppleSDGothicNeoB";
  font-size: var(--font-size-20);
}
.infodetail__content{
  margin: 1rem 0;
  padding: 1rem 1.5rem;
  border: 1px solid var(--color-grey-4);
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
  color: var(--color-white) !important;
}
</style>