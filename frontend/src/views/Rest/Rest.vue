<template>
  <div id="rest">
    <Nav />
    <web-cam :location="location"/>
  </div>  
</template>

<script>
import Nav from "@/common/Nav/Nav";
import WebCam from '@/common/WebCam/WebCam';
export default {
  name : "Rest",
  components : {
      Nav,
      WebCam,
  },
  data() {
    return {
      location : "rest"
    }
  },
}
</script>

<style scoped>
#rest{
  display: flex;
  height : 100vh;
  overflow: hidden;
  flex-direction: column;
}
</style>