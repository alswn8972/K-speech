<template>
  <div id="app" data-app>
    <loader :isLoading="$store.getters.getIsSubmit"/>
    <router-view />
    <UnityGame v-if="$store.state.user!==null && showUnity"/>
  </div>
</template>
<script>
import UnityGame from '@/components/Unity/UnityGame';
import Loader from './common/Loader/Loader.vue';
export default {
  name : "App",
  data() {
    return {
    }
  },
  components : {
    UnityGame,
    Loader,
  },
  created(){
    this.$store.commit('setIsSubmit',false)
  },
  computed : {
    showUnity : function(){
      if(this.$route.name === "MyPage" || this.$route.name === "CamSetting" || this.$route.name === "About" || this.$route.name==="Login"){
        return false;
      }
      return true;
    }
  }
}
</script>
<style>
@import "./css/style.css";
* {
  box-sizing: border-box;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
}
</style>
