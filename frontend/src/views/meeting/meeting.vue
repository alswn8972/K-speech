<template>
  <div id="meeting">
    <web-cam :location="location"/>
  </div> 
</template>

<script>
//import Nav from "@/common/Nav/Nav";
import WebCam from '@/common/WebCam/WebCam';

export default {
  name : "meeting",
  components : {
      //Nav,
      WebCam,
  },
  data() {
    return {
      location : "meeting",
    }
  },
  created(){
    if(this.$store.getters.getUser === null){
      this.$router.push('/login');
    }
    // else if(this.$store.getters.getSchoolId === null) {
    //   this.$router.push({
    //     name: 'Unity',
    //   })
    // }   
  }
}
</script>

<style scoped>
#meeting{
  display: flex;
  height : 100vh;
  overflow: hidden;
  flex-direction: column;
}
</style>