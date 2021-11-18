<template>
  <div id="nav">
    <div class="nav-container">
      <div class="logo">
        <img src="@/../public/Image/kspeech_logo.png" width="200px" height="120px" id="icon" v-if="this.$route.name !== 'About'"/>
      </div>
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
      window.location.href="https://k5d104.p.ssafy.io/"
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