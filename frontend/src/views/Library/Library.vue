<template>
    <div id="library">
        <Nav/>
        <div class="tooltip">
            <audio controls loop>
              <!-- <source src="../../../public/music/2021_pop.mp3"> -->
              <source src="../../../public/music/study_music.mp3">
            </audio>
            <span class="tooltiptext">음악으로 학습효과에 도움을 받아보세요.</span>
        </div>
        <div id="library-container">
            <router-view/>
        </div>
    </div>
</template>

<script>
import Nav from "@/common/Nav/Nav"
export default {
    name : 'Library',
    components : {
      Nav,
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
#library{
  display: flex;
  height : 100vh;
  flex-direction: column;
}
#library-container{
    width : 80%;
    margin : auto;
    flex : 1;
}
.tooltip {
  margin-left: 70%;
  margin-top: 10px;
  height: 0px;
  position: relative;
  display: inline-block;
}
.tooltip .tooltiptext {
  visibility: hidden;
  width: 240px;
  background-color: #555;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 0;
  position: absolute;
  z-index: 999;
  bottom: 125%;
  left: 50%;
  margin-left: -60px;
  opacity: 0;
  transition: opacity 0.3s;
  font-size: var(--font-size-14);
    font-family: "AppleSDGothicNeoB";
}

.tooltip .tooltiptext::after {
  content: "";
  position: absolute;
  top: 100%;
  left: 50%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: #555 transparent transparent transparent;
}

.tooltip:hover .tooltiptext {
  visibility: visible;
  opacity: 1;
}
</style>