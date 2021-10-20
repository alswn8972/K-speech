<template>
  <div class="qna__wrap">
    <div class="qna__header">
      <button class="qna__btn qna__btn__white" @click="handleClickModal">
        <font-awesome-icon :icon="['fas', 'pencil-alt']" size="1x"/>
        질문하기
      </button>
      <Modal
        v-model="showModal"
        title="질문 작성"
        modal-class="scrollable-modal"
      >
        <div class="scrollable-content">
          <CreateEditor/>
        </div>
        <div class="row scrollable-modal-footer">
          <div class="modal_btn_box">
            <button
              class="modal_btn submit"
              type="button"
              @click="handleSubmit"
            >
              질문 작성하기
            </button>
          </div>
        </div>
      </Modal>
      <Search :keyword="keyword" :category="category" @handleSelect="handleSelect" @handleSearch="handleSearch"/>
    </div>
    <div class="qna__q__box">
      <Question
        v-for="question in keywordList.slice(0,idx)"
        :key="question.qnaId"
        :question="question"
        @handleClickQustion="handleClickQuestion"
      />
    </div>
    <div class="qna__more" v-if="idx < keywordList.length">
      <button class="qna__btn__more" @click="handleMore">
        더 보기<br/>
        <font-awesome-icon :icon="['fas', 'chevron-down']" size="1x"/>
      </button>
    </div>
  </div>
</template>

<script>
import Question from './Question.vue'
import http from '../../util/http-common.js';
import CreateEditor from '../../components/Board/CreateEditor.vue';
import * as Hangul from 'hangul-js';
import Search from './Search.vue';
export default {
  components: { 
    Question,
    CreateEditor,
    Search,    
  },
  name:'QnaBoard',
  data(){
    return{
      qnaList:[],
      showModal: false,
      keyword: "",
      category:"title",
      idx: 4,
    }
  },
  created(){
    if(this.$store.getters.getUser === null){
      this.$router.push('/login');
    } else if(this.$store.getters.getSchoolId === null) {
      this.$router.push({
        name: 'Unity',
      })
    }
    http.get(`v1/qna/list/${this.$store.getters.getSchoolId}`)
    .then((res)=>{
      if(res.status===200){
        this.qnaList=res.data.data.reverse();
        this.qnaList.forEach(qna => {
          const dis1 = Hangul.disassemble(qna.qnaTitle, true);
          const dis2 = Hangul.disassemble(qna.user.userNickname, true);
          const cho1 = dis1.reduce(function (prev, elem) {
                elem = elem[0] ? elem[0] : elem;
                return prev + elem;
            }, "");
          const cho2 = dis2.reduce(function (prev, elem) {
                elem = elem[0] ? elem[0] : elem;
                return prev + elem;
            }, "");
          qna['qnaTitleCho']=cho1;
          qna['qnaUserCho']=cho2;
        })
      }
    })
    .catch((err)=>{
      console.error(err);
    })
  },
  computed:{
    keywordList(){
      if(this.keyword==="")
        return this.qnaList;
      const search1 = this.keyword;
      const search2 = Hangul.disassemble(search1).join("");
      this.idx=4;
      if(this.category==="title"){
        return this.qnaList.filter(qna => 
          qna.qnaTitle.includes(search1) || qna.qnaTitleCho.includes(search2)
        )
      }
      return this.qnaList.filter(qna => 
        qna.user.userNickname.includes(search1) || qna.qnaUserCho.includes(search2)
      )
    }
  },  
  methods:{
    handleMore(){
      this.idx+=4;
    },
    handleSearch(val){
      this.keyword=val;
    },
    handleSelect(val){
      this.category=val;
    },    
    handleClickQuestion(id){
      this.$router.push({
        name:'QnaDetail',
        params:{
          id,
        }
      })
    },
    handleClickModal(){
      this.showModal = true;
    },
    handleSubmit(){
      const qnaContent = document.querySelector('.ProseMirror').innerHTML;
      const qnaTitle = document.querySelector('.editor__title__input').value;    
      const data = {
        qnaTitle,
        qnaContent,
        roomId: this.$store.getters.getSchoolId,
        userId: this.$store.getters.getUserId,
      };
      http.post('v1/qna', JSON.stringify(data))
      .then((res)=>{
        if(res.status===200){
          this.qnaList=res.data.data.reverse();
          this.qnaList.forEach(qna => {
            const dis1 = Hangul.disassemble(qna.qnaTitle, true);
            const dis2 = Hangul.disassemble(qna.user.userNickname, true);
            const cho1 = dis1.reduce(function (prev, elem) {
                  elem = elem[0] ? elem[0] : elem;
                  return prev + elem;
              }, "");
            const cho2 = dis2.reduce(function (prev, elem) {
                  elem = elem[0] ? elem[0] : elem;
                  return prev + elem;
              }, "");
            qna['qnaTitleCho']=cho1;
            qna['qnaUserCho']=cho2;
          })
          this.showModal=false;
        }
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
.qna__wrap{
  margin: auto;
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
  width: 80%;
}
.qna__header{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5em;
  
  &__search{
    font-family: "AppleSDGothicNeoSB";
  }
}
.qna__more{
  display: flex;
  justify-content: center;
  align-items: center;
}
.qna__btn{
  font-family: "AppleSDGothicNeoB";
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
  border: 2px solid var(--color-mainBlue);
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
  &__more{
    font-family: "AppleSDGothicNeoB";
  }
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