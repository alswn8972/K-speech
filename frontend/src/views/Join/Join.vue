<template>
  <div class="join">
      <router-view></router-view>
  </div>
</template>

<script>
export default {
    name: "Join",
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