<template>
  <div class="guestbook">
    <h1 class="h1">출석체크</h1>
    <div class="gb_area">
      <div class="left_btn" v-on:click="goLeft">
        <i class="fas fa-chevron-left fa-2x"></i>
      </div>
      <div class="gb_book">
        <div class="div_txt">
            <span class="attend">등교🌞</span>
            <span class="finish">하교🌙</span>
            <span class="attend1">등교🌞</span>
            <span class="finish1">하교🌙</span>
        </div>

        <table class="gb_ul ul1">
          <tr v-for="(u, index) in users.slice(nowNum*24,(nowNum*24)+12)" :key="index">
            <td class="user_nickname">{{u.user.userNickname}}</td>
            <td class="attend_date">{{u.attendDate}}</td>
            <td class="finish_date">{{u.finishDate}}</td>
          </tr>
        </table>

        <table class="gb_ul ul2">
          <tr v-for="(u, index) in users.slice((nowNum*24)+12,(nowNum*24)+24)" :key="index">
            <td class="user_nickname">{{u.user.userNickname}}</td>
            <td class="attend_date">{{u.attendDate}}</td>
            <td class="finish_date">{{u.finishDate}}</td>
          </tr>
        </table>
      </div>
      <div class="right_btn" v-on:click="goRight">
        <i class="fas fa-chevron-right fa-2x"></i>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "GuestBook",
  components: {},
  data() {
    return {
      schoolId: 0,
      user: {},
      users: [],
      nowNum: 0,
    };
  },
  created() {
    if(this.$store.getters.getUser === null){
      this.$router.push('/login');
    } else if(this.$store.getters.getSchoolId === null) {
      this.$router.push({
        name: 'Unity',
      })
    }   

    this.user=this.$store.getters.getUser;
    this.schoolId = this.$store.getters.getSchoolId;

     http
      .post(`/v1/gb/${this.schoolId}/${this.user.userId}`)
      .then((res) => {
        console.log("오셈"+JSON.stringify(res.data.data));
        this.users = res.data.data;
      })
      .catch((err) => console.error(err));
  },
  methods: {
    goLeft(){
      this.nowNum--;
      if(this.nowNum<0)this.nowNum++;
      
    },
    goRight(){
      this.nowNum++;
      if(this.nowNum>this.users.length/24) this.nowNum--;
    },
  },
};
</script>

<style scoped src="../../css/GuestBook.css">
