<template>
  <div id="nav">
    <div class="nav-container">
      <div class="logo">
        <img src="@/../public/Image/kspeech_logo.png" width="200px" height="120px" id="icon" v-if="this.$route.name !== 'About'"/>
      </div>
      <!-- <div id="user-container">
        <div v-if="user!==null" id="user-img">
          <img v-if="user.userImg===''" src="@/../public/Image/user_profile.png" id="user-icon" />
          <img v-else :src="user.userImg" id="user-icon" />
          <div id="user-level">
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
          </div>
        </div> -->
        <div id="menu-container" v-if="this.user === null">
            <div @click="handleAbout" style="margin-right:20px;" class="pixel2">About</div>
            <div @click="handleLogin" style="margin-right:20px;" class="pixel2">로그인</div>
            <div @click="handleJoin" style="margin-right:20px;" class="pixel2">회원가입</div>
          
        </div>
        <div id="menu-container" v-else>
          <div @click="handleAbout" style="margin-right:20px;" class="pixel2">About</div>
          <div @click="handleUnity" style="margin-right:20px;" class="pixel2">GO K-speech</div>
          <div @click="handleMypage" style="margin-right:20px;" class="pixel2">내 정보</div>
          <div @click="handleLogout" style="margin-right:20px;" class="pixel2">로그아웃</div>
        </div>
          
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Nav",
  data() {
    return {
      showMenu: false,
      user:{},
    };
  },
  created(){
    this.user=this.$store.getters.getUser;
    
  },
  mounted() {
    
    document.addEventListener(
      "click",
      function (event) {
        if (event.target.closest("#menu-container")) return;
        this.showMenu = false;
      }.bind(this)
    );
  },
  methods: {
    showMenus() {
      this.showMenu = !this.showMenu;
    },
    handleMypage(){
      this.$router.push('/mypage');
    },
    handleLogout(){
      this.$store.commit('logout');
      window.location.href="http://52.79.253.253:3000/"
    },
    handleAbout(){
        this.$router.push('/').catch(()=>{});;
    },
    handleLogin(){
      this.$router.push('/login');
    },
    handleJoin(){
      this.$router.push('/join');
    },
    handleUnity(){
      
        this.$router.push({name : 'Unity'}).catch(()=>{});;
    }
  },
};
</script>

<style scoped src="../../css/Nav.css">
</style>