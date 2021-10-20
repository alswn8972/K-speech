<template>
  <div class="code_board_wrap">
    <div class="code_board_title">
      풀리지 않는 문제가 있다면 친구들과 반례를 공유해보세요
    </div>
    <div class="code_board_nav">
      <button class="code_board_create_btn" @click="handleClickModal">
        <font-awesome-icon :icon="['fas', 'pencil-alt']" size="1x" />
        문제 올리기
      </button>
      <Modal
        v-model="showModal"
        title="문제 작성"
        modal-class="scrollable-modal"
      >
        <div class="scrollable-content">
          <CreateProblem/>
        </div>
        <div class="row scrollable-modal-footer">
          <div class="modal_btn_box">
            <button
              class="modal_btn submit"
              type="button"
              @click="handleSubmit"
            >
              글 작성하기
            </button>            
          </div>
        </div>
      </Modal>
      <Search :keyword="keyword" :category="category" @handleSelect="handleSelect" @handleSearch="handleSearch"/>
    </div>
    <div class="code_board_list">
      <button type="button" class="code_borad_list_btn" @click="handleClickLeft">
        <font-awesome-icon :icon="['fas', 'chevron-left']" size="2x" />
      </button>
      <div class="code_board_contents">
        <Content 
          v-for="pro in keywordList.slice(proIdx*showCnt,proIdx*showCnt+showCnt)"
          :id="pro.proId"
          :key="pro.proId"
          :content="pro"
          @handleClickContent="handleClickContent"
        />
      </div>
      <button type="button" class="code_borad_list_btn" @click="handleClickRight">
        <font-awesome-icon :icon="['fas', 'chevron-right']" size="2x" />
      </button>
    </div>
  </div>
</template>

<script>
import Content from './Content.vue';
import CreateProblem from './CreateProblem.vue';
import http from '../../util/http-common.js';
import * as Hangul from 'hangul-js';
import Search from './Search.vue';
export default {
  name:'ProblemBoard',
  components:{
    Content,
    CreateProblem,
    Search
  },
  data : ()=>{
    return{
      windowSize:0,
      showModal: false,
      proIdx:0,
      showCnt:0,
      proList: [],
      keyword: "",
      category:"title",      
    }
  },
  created(){
    this.windowSize=window.innerWidth;
    if(this.windowSize>1024)
      this.showCnt=8;
    else if(this.windowSize>800)
      this.showCnt=6;
    else if(this.windowSize>500)
      this.showCnt=4;
    else
      this.showCnt=2;
    window.addEventListener('resize',this.handleWindowSize);
    http.get(`v1/pro/list/${this.$store.getters.getSchoolId}`)
    .then((res)=>{
      if(res.status===200){
        this.proList=res.data.data.reverse();
        this.proList.forEach(pro => {
          const dis1 = Hangul.disassemble(pro.proTitle, true);
          const dis2 = Hangul.disassemble(pro.user.userNickname, true);
          const cho1 = dis1.reduce(function (prev, elem) {
                elem = elem[0] ? elem[0] : elem;
                return prev + elem;
            }, "");
          const cho2 = dis2.reduce(function (prev, elem) {
                elem = elem[0] ? elem[0] : elem;
                return prev + elem;
            }, "");
          pro['proTitleCho']=cho1;
          pro['proUserCho']=cho2;
        })
      }
    })
    .catch((err)=>{
      console.error(err);
    })
  },
  beforeDestroy(){
    window.removeEventListener('resize',this.handleWindowSize);
  },
  mounted(){
  },
  computed:{
    keywordList(){
      if(this.keyword==="")
        return this.proList;
      const search1 = this.keyword;
      const search2 = Hangul.disassemble(search1).join("");
      if(this.category==="title"){
        return this.proList.filter(pro => 
          pro.proTitle.includes(search1) || pro.proTitleCho.includes(search2)
        )
      }
      return this.proList.filter(pro => 
        pro.user.userNickname.includes(search1) || pro.proUserCho.includes(search2)
      )
    }
  },  
  methods:{
    handleSearch(val){
      this.keyword=val;
    },
    handleSelect(val){
      this.category=val;
    },    
    handleClickContent(id,content){
      this.$router.push({
        name:"ProblemDetail",
        params:{
          id,
          content,
        }
      })
    }, 
    handleWindowSize(){
      this.windowSize=window.innerWidth;
      if(this.windowSize>1024)
        this.showCnt=8;
      else if(this.windowSize>800)
        this.showCnt=6;
      else if(this.windowSize>500)
        this.showCnt=4;
      else
        this.showCnt=2;
      while(this.infoIdx*this.showCnt>=this.proList.length)
        this.proIdx--;
    },
    handleClickModal(){
      this.showModal = true;
    },
    handleClickLeft() {
      if((this.proIdx-1)*this.showCnt < 0) return;
      this.proIdx--;
    },
    handleClickRight() {
      if((this.proIdx+1)*this.showCnt >=this.proList.length) return;
      this.proIdx++;
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
        roomId: this.$store.getters.getSchoolId,
        userId: this.$store.getters.getUserId,
      };
      http.post('v1/pro', JSON.stringify(data))
      .then((res)=>{
        if(res.status===200){
          this.proList=res.data.data.reverse();
          this.proList.forEach(pro => {
            const dis1 = Hangul.disassemble(pro.proTitle, true);
            const dis2 = Hangul.disassemble(pro.user.userNickname, true);
            const cho1 = dis1.reduce(function (prev, elem) {
                  elem = elem[0] ? elem[0] : elem;
                  return prev + elem;
              }, "");
            const cho2 = dis2.reduce(function (prev, elem) {
                  elem = elem[0] ? elem[0] : elem;
                  return prev + elem;
              }, "");
            pro['proTitleCho']=cho1;
            pro['proUserCho']=cho2;
          })          
          this.showModal=false;
        }
      })
      .catch((err)=>{
        console.error(err);
        this.showModal=false;
      })
    },
  },
};
</script>

<style lang="scss" scoped>
.code_board_wrap{
  margin: auto;
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
  width: 80%;
}
.code_board_title{
  font-size: var(--font-size-14);
  color: var(--color-grey-3);
  font-family: "AppleSDGothicNeoR";
  text-align: center;
  padding: 0.5em;
}
.code_board_nav{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5em;
}
.code_board_create_btn{
  color: var(--color-mainBlue);
  background-color: var(--color-white);
  border: 2px solid var(--color-mainBlue);
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
  font-family: "AppleSDGothicNeoSB";
}
.code_board_search{
  font-family: "AppleSDGothicNeoSB";
}
.code_board_list{
  background-color: #333333;
  border: 10px solid #9E5E3F;
  width: 100%;
  min-height: 400px;
  padding: 1.5em 2em;
  display: flex;
  justify-content: center;
  align-items: center;
}
.code_board_contents{
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}
.content_list{

}
.code_borad_list_btn{
  color: var(--color-white);
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
</style>
