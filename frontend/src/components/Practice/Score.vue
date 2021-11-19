<template>
  <div class="score">
    
    <div class="score_content">
      <div class="top" style="margin-bottom: 40px;">My Score</div>
      
       <v-row style="margin-left: 40px;">
         <v-col>
           <ul>
             <li v-for="(item,index) in content.slice(0,5)" :key="item.id" class="text" >
                {{item}} :
                {{score[index]}}
             </li>
             
           </ul>
         </v-col>
         <v-col>
            <ul>
             <li v-for="(item,index) in content.slice(5,10)" :key="item.id" class="text">
                
                 {{item}} :
                 {{score[index+5]}}
             </li>
           </ul>
         </v-col>
          <v-col>
            <ul>
             <li v-for="(item,index) in content.slice(10,15)" :key="item.id" class="text">
               {{item}} :
                 {{score[index+10]}}
             </li>
            
           </ul>
         </v-col>
       </v-row>
       <div class="top" style="margin-bottom: 10px;">
         <p> <i class="fas fa-cat"></i> Average : {{this.aver.toFixed(3)}}</p>
        </div>
        <div @click="goHome()" class="pixel2" >홈으로</div>
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
      aver : null,
      datas: [],
    }
  },
  created(){
    
    let content = this.$route.params.data;
    let result = this.$route.params.result;
    //console.log(content)
    //console.log(result)

    let temp = {
      score: null,
      content : null
    }

    for(let i=0; i<content.length; i++){
      this.score.push(result[i].score)
      this.content.push(content[i].content)
      if(result[i].score!=''){
        this.aver+=parseInt(result[i].score)
      }
    }
    //console.log(this.aver)
    
    this.aver/=content.length
    //console.log(this.aver)
  },
  methods: {
    goHome(){
      this.$router.push({
          name: 'About'
        })
    }
  
  }
};
</script>

<style scoped src="../../css/Score.css">

</style>
