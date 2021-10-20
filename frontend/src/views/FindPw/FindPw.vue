<template>
  <div class="findPw">
      <router-view></router-view>
  </div>
</template>

<script>
export default {
    name: "findPw",
    created(){
        if(this.$store.getters.getUser!==null){
          this.$router.push({
            name:'Unity'
          })
        }      
    }    
}
</script>

<style>

</style>