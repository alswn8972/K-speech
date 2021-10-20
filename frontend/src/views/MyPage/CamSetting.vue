<template>
  <div id="CamSetting">
    <camera-setting></camera-setting>
  </div>
</template>

<script>
import CameraSetting from '@/components/WebCam/CameraSetting';
export default {
  name : "CamSetting",
  components : {
    CameraSetting,
  },
  created(){
    if(this.$store.getters.getUser === null){
      this.$router.push('/login');
    } 
  }
}
</script>

<style>

</style>