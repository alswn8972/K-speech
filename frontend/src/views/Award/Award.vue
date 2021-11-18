
<template>
  <!--배경-->
  <div class="mypage">
    <!--가운데 내용-->
    <div class="mypage_content">
      <div class="top" style="margin-bottom : 5%;">
        <p class="main">Ranking</p>
        
        <v-img
        class="img"
        max-height="150"
        max-width="100"
        src="../../assets/trophy.png"
      ></v-img>
      </div>
        <v-row>
          <v-col>
            <p class="pont">Rain Game</p>
            
            <div v-if="rain_rank.length>0">
              <ul style="list-style:none; margin-top : 10px;">
                <li v-for="(item,index) in rain_rank.slice(0,3)" :key="item.id" class="text" style="margin-top : 10px;">
                  <i class="fas fa-medal" v-if="index==0" style="color: #FFFA82;"></i>
                  <i class="fas fa-medal" v-if="index==1" style="color: #969696	;"></i>
                  <i class="fas fa-medal" v-if="index==2" style="color: #A05C37	;"></i>
                  <span> User : {{item.userNick}}, Score : {{item.score}}</span>
                  </li>
              </ul>
            </div>
            <div v-else>
              <p class="text">게임에 대한 기록이 없습니다.</p>
            </div>
          </v-col>
          <v-col>
            <p class="pont">Rock Game</p>
       
            <div v-if="rock_rank.length>0">
             <ul style="list-style:none; margin-top : 10px;">
                <li v-for="(item,index) in rock_rank.slice(0,3)" :key="item.id" class="text" style="margin-top : 10px;">
                  <i class="fas fa-medal" v-if="index==0" style="color: #FFFA82;"></i>
                  <i class="fas fa-medal" v-if="index==1" style="color: #969696	;"></i>
                  <i class="fas fa-medal" v-if="index==2" style="color: #A05C37	;"></i>
                  <span> User : {{item.userNick}}, Score : {{item.score}}</span>
                  </li>
              </ul>
            </div>
            <div v-else>
              <p class="text">게임에 대한 기록이 없습니다.</p>
              
            </div>
          </v-col>
          <v-col>
            <p class="pont">Word Game</p>
            
            <div v-if="word_rank.length>0">
              <ul style="list-style:none; margin-top : 10px;">
                <li v-for="(item,index) in word_rank.slice(0,3)" :key="item.id" class="text" style="margin-top : 10px;">
                  <i class="fas fa-medal" v-if="index==0" style="color: #FFFA82;"></i>
                  <i class="fas fa-medal" v-if="index==1" style="color: #969696	;"></i>
                  <i class="fas fa-medal" v-if="index==2" style="color: #A05C37	;"></i>
                  <span> User : {{item.userNick}}, Score : {{item.score}}</span>
                  </li>
              </ul>
            </div>
            <div v-else>
              <p class="text">게임에 대한 기록이 없습니다.</p>
            </div>
          </v-col>
          <v-col>
            <p class="pont">Sentence Game</p>
            
            <div v-if="sentence_rank.length>0">
            <ul style="list-style:none;">
               <ul style="list-style:none; margin-top : 10px;">
                <li v-for="(item,index) in sentence_rank.slice(0,3)" :key="item.id" class="text" style="margin-top : 10px;">
                  <i class="fas fa-medal" v-if="index==0" style="color: #FFFA82;"></i>
                  <i class="fas fa-medal" v-if="index==1" style="color: #969696	;"></i>
                  <i class="fas fa-medal" v-if="index==2" style="color: #A05C37	;"></i>
                  <span> User : {{item.userNick}}, Score : {{item.score}}</span>
                  </li>
              </ul>
              </ul>
            </div>
            <div v-else>
              <p class="text">게임에 대한 기록이 없습니다.</p>
            </div>
          </v-col>
          </v-row>
          <v-row>
            <v-col>
                <div @click="goHome()" class="pixel2" >홈으로</div>
            </v-col>
          </v-row>
          
          
          
      </div>
    </div>
  
</template>

<script>
import http from "@/util/http-game";

export default {
  name: "Award",
  data() {
    return {
     rain_rank : [],
     rock_rank : [],
     sentence_rank : [],
     word_rank : [],
     
     user : null,
     data : null,
    };
  },
  created(){
    
    http.get("game/total/ranking")
            .then((res) => {
              //console.log(res)
              this.data=res.data;
              this.rain_rank=res.data.RainRank;
              this.rock_rank=res.data.RockRank;
              this.sentence_rank=res.data.SentenceRank;
              this.word_rank=res.data.WordRank;
              
            }).catch((err)=>{
          console.log(err);
    })
    
    
  },

  methods:{
    goHome(){
      this.$router.push({name : 'About'}).catch(()=>{});;
    },
    handleUnity(){
        this.$router.push({name : 'Unity'}).catch(()=>{});;
    }
  },
  components: {},
};
</script>


<style scoped src="../../css/MyPage.css">

</style>

