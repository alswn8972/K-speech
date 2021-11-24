
<template>
  <!--배경-->
  <div class="mypage">
    <!--가운데 내용-->
    <div class="mypage_content">
      <div class="top">
        <p class="main">My Page</p>
        
        <p class="pont" style="margin-top: 20px;">닉네임 : {{user.userNickName}} </p>
        <v-row>
          <v-col>
            <router-link to="/camSetting" style="text-align :center; font-size : large;">
              화상설정 >>
            </router-link>
          </v-col>
        </v-row>
        <v-img
        class="img"
        max-height="150"
        max-width="250"
        src="../../assets/picture3.png"
        style="margin-bottom: 20px;"
      ></v-img>
      
      </div>
        <v-row>
          <v-col>
            <p class="pont">Rain Game</p>
            
            <div v-if="rain_rank.length>0">
              <ul style="list-style:none;">
                <li>
                  <i class="fas fa-medal"> Rank</i>
                  {{rain_rank[0]}}</li>
                <li><i class="fas fa-star"> Score</i> {{rain_rank[1].score}}</li>
              </ul>
            </div>
            <div v-else>
              <p class="text">게임에 대한 기록이 없습니다.</p>
            </div>
          </v-col>
          <v-col>
            <p class="pont">Rock Game</p>
       
            <div v-if="rock_rank.length>0">
             <ul style="list-style:none;">
                <li>
                  <i class="fas fa-medal"> Rank</i>
                  {{rock_rank[0]}}</li>
                <li><i class="fas fa-star"> Score</i> {{rock_rank[1].score}}</li>
              </ul>
            </div>
            <div v-else>
              <p class="text">게임에 대한 기록이 없습니다.</p>
              
            </div>
          </v-col>
          <v-col>
            <p class="pont">Word Game</p>
            
            <div v-if="word_rank.length>0">
              <ul style="list-style:none;">
                <li>
                  <i class="fas fa-medal"> Rank</i>
                  {{word_rank[0]}}</li>
                <li><i class="fas fa-star"> Score</i> {{word_rank[1].score}}</li>
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
                <li>
                  <i class="fas fa-medal"> Rank</i>
                  {{sentence_rank[0]}}</li>
                <li><i class="fas fa-star"> Score</i> {{sentence_rank[1].score}}</li>
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
            <!-- <v-col>
                <div @click="handleUnity()" class="pixel2">게임하러가기</div>
            </v-col> -->
          </v-row>
          
          
          
      </div>
    </div>
  
</template>

<script>
import http from "@/util/http-game";

export default {
  name: "MyPage",
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
    this.user=this.$store.getters.getUser;
    // this.user.userNickName="dfsfsafsadf";
    http.get("game/total/ranking")
            .then((res) => {
              //console.log(res)
              this.data=res.data;
              for(let i=0;i<res.data.RainRank.length;i++){
                if(res.data.RainRank[i].userNick==this.user.userNickName){
                  this.rain_rank.push(i+1)
                  this.rain_rank.push(res.data.RainRank[i])
                  break
                }
              }
              for(let i=0;i<res.data.RockRank.length;i++){
                if(res.data.RockRank[i].userNick==this.user.userNickName){
                   this.rock_rank.push(i+1)
                  this.rock_rank.push(res.data.RockRank[i])
                  break
                }
              }
              for(let i=0;i<res.data.SentenceRank.length;i++){
                if(res.data.SentenceRank[i].userNick==this.user.userNickName){
                    this.sentence_rank.push(i+1)
                  this.sentence_rank.push(res.data.SentenceRank[i])
                  break
                }
              }
              for(let i=0;i<res.data.WordRank.length;i++){
                if(res.data.WordRank[i].userNick==this.user.userNickName){
                  //console.log("dfsfsafsadf")
                  this.word_rank.push(i+1)
                  this.word_rank.push(res.data.WordRank[i])
                  //console.log( "추출",this.word_rank)
                  break
                }
              }
              //console.log(this.data)
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

