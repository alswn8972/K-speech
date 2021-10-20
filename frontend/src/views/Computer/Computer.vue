<template>
  <div id="computer">
    <Nav />
    <web-cam :location="location"/>
  </div>  
</template>

<script>
import Nav from "@/common/Nav/Nav";
import WebCam from '@/common/WebCam/WebCam';
export default {
  name : "Computer",
  components : {
      Nav,
      WebCam,
  },
  data() {
    return {
      location : "computer",
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
  }
}
</script>

<style scoped>
#computer{
  display: flex;
  height : 100vh;
  overflow: hidden;
  flex-direction: column;
}
</style>