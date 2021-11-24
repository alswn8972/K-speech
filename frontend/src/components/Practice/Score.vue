<template>
  <div class="score">
    <div class="score_title">
      점수
    </div>
    <div class="score_content">

      <!--<div> 점수 </div>-->
        <div class="score_left">
          <div v-for="item in content">
            {{item}}
          </div>
        </div>
        <div class="score_right">
          <div v-for="item in score">
            {{item}}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Score",
  data: () => {
    return {
      content : [],
      score : [],
      aver : null
    }
  },
  created(){
    let content = this.$route.params.data;
    let result = this.$route.params.result;
    console.log(content)
    console.log(result)

    let temp = {
      score: null,
      content : null
    }

    for(let i=0; i<content.length; i++){
      this.score.push(result[i].score)
      this.content.push(content[i].content)
      this.aver+=result[i].score
    }
    this.aver/=content.length
  },
  methods: {
  
  }
};
</script>

<style scoped src="../../css/Score.css">
</style>
