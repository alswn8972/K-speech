<template>
  <unity src="./unity/Build/test.json" width="1000" height="600" unityLoader="./unity/Build/UnityLoader.js"></unity>  
</template>
<script>
import Unity from 'vue-unity-webgl';
//import http from '../../util/http-common.js';

export default {

    components : {Unity},
    created(){
      if(this.$store.state.user===null){
        this.$router.push('/login');
      }
      //this.schoolName=this.$store.state.schoolName;
      //this.user=this.$store.getters.getUser;
    }
}
</script>
<style scoped src="../../css/UnityGame.css">
</style>