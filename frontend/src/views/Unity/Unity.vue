<template>
    <div id="unity">
        <Nav/>
        <div id="unity-container">
            <User/>
            <div id="unity-game-container">
                <div id="game-container"></div>
            </div>
        </div>
    </div>
</template>

<script>
import User from '@/components/Unity/User';
import Nav from '@/common/Nav/Nav';
export default {
    name : "unity",
    components : {
        User,
        Nav
    },
    data() {
        return {
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
    },
}
</script>

<style scoped src="../../css/Unity.css">
</style>