<template>
  <div :id="id" class="content_wrap" @click="onClickContent">
    <div class="content_header"></div>
    <div class="content_title" v-if="routeName==='InfoBoard'">
      {{content.infoTitle.length>13 ? `${content.infoTitle.slice(0,11)}..` : content.infoTitle}}
    </div>
    <div class="content_title" v-else>
      {{content.proTitle.length>13 ? `${content.proTitle.slice(0,11)}..` : content.proTitle}}
    </div>
    <div class="content_footer">
      <div class="content_footer_date" v-if="routeName==='InfoBoard'">
        {{$moment(content.infoDate).format('YYYY-MM-DD')}}
      </div>
      <div class="content_footer_date" v-else>
        {{$moment(content.proDate).format('YYYY-MM-DD')}}
      </div>
      <div class="content_footer_name">
        {{content.user.userNickname}}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name:'Content',
  data(){
    return{
      routeName:"",
      colorList:['#FE9C9B','#FCB849','#69F5CE','#7A89FF','#60BDFF','#D06BF7','#F36B9D'],
    }
  },
  props:{
    content: Object,
    color: String,
    id: Number,

  },
  created(){
    this.routeName=this.$route.name;
  },
  mounted(){
    const wrap = document.getElementById(String(this.id));
    wrap.querySelector('.content_header').style.backgroundColor=this.colorList[this.id%7];
  },
  methods:{
    onClickContent(){
      this.$emit('handleClickContent',this.id);
    }
  }
}
</script>

<style scoped>
.content_wrap{
  background-color: white;
  border-radius: 10px;
  width: 22%;
  
  /* max-height: 40%; */
  margin: 0.5em;
  cursor: pointer;
}
.content_header{
  height: 30px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}
.content_title{
  padding: 1em;
  font-size: var(font-size-22);
  font-family: "AppleSDGothicNeoB";
  font-weight: 400;
}
.content_footer{
  display: flex;
  justify-content: space-between;
  padding: 1em;
}
.content_footer_date{
  font-family: "AppleSDGothicNeoL";
  font-size: var(--font-size-14);
}
.content_footer_name{
  font-family: "AppleSDGothicNeoL";
  font-size: var(--font-size-14);
}
@media screen and (max-width: 1024px) {
  .content_wrap{
    width: 29%;
  }
}
@media screen and (max-width: 800px) {
  .content_wrap{
    width: 40%;
  }
}
@media screen and (max-width: 500px) {
  .content_wrap{
    width: 100%;
  }
}
</style>