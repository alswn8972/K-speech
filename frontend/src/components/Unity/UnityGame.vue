<template>
   <div id="unity-game" :class="{'small-map':!showMap}" @click="goUnity">
        <div id="game-container">
            <div id="mini-map-alt" class="map-alt" v-if="!showMap">
                <div class="mini-alt">
                    <v-icon id="mini-alt-icon">fas fa-undo-alt</v-icon>
                    <p id="mini-alt-text" >돌아가기</p>
                </div>
            </div>
            
             <!-- hideFooter : 하단의 Full screen을 표시유무 , ref : 유니티와의 커뮤니케이션-->
            <unity id="bts-unity" 
            style="margin-top : 10%;"
            src = "./unity/Build/unityWebgl.json"
            unityLoader = "./unity/Build/UnityLoader.js"
            :width=width
            :height=height
            ref = "hookInstance"
            :hideFooter="true"
            >
            </unity>
            <button id="link-btn" @click="getUnityHook" v-if="!linked" style="font-family: neodgm_pro;
  font-size: x-large;">계정 연동</button>
        </div>
        <div id="unity-room-name" hidden></div>
        <div id="unity-object-name" hidden></div>
    </div>

</template>
<script>
import Unity from 'vue-unity-webgl';

export default {

    components : {Unity},
     data() {
        return {
            user: {},
            nickname : '' ,
            objectName : '',
            RoomName : '',
            linked : false,
            height : "960",
            width : "580",
            interval : '',
            unityFocus : false,
            keydownAlt : false,
            enterMap : false,
            
        }
    },

    created(){
    //   if(this.$store.state.user===null){
    //     this.$router.push('/login');
    //   }
      this.RoomName=this.$store.state.RoomName;
      this.user=this.$store.getters.getUser;
      
    },
    mounted() {
        this.enterMap = false;
        this.schoolName = "";
        //로딩창
        this.$store.commit('setIsSubmit',true);
        //5초후 로딩창없애기
        setTimeout(()=>{
          this.$store.commit('setIsSubmit',false);
        },5000)


        this.mapHeight = document.querySelector('#unity-game-container').getBoundingClientRect().height;
        this.mapWidth = document.querySelector('#unity-game-container').getBoundingClientRect().width;
        
        // const top = document.querySelector('#nav').getBoundingClientRect().height + 1;
        const target = document.querySelector('#unity-game-container')
        const targetRect = target.getBoundingClientRect();
        this.width = targetRect.width;
        // this.height = document.querySelector('#unity').getBoundingClientRect().height-top;
        this.height = window.innerHeight-102;
        this.width=960
        this.height=540
        const unity = document.querySelector('#unity-game');
        //24인치 화면에 맞추다보니 위치가 가운데가 아닐 수 있다.
        //unity.style.transform = `translate(${targetRect.left}px,102px)`;
        unity.style.transform = `translate(470px
        ,130px)`;
       
    },
    computed : {
        showMap : function(){
            if(this.$route.name === 'Unity'){
                return true;
            }else{
                return false;
            }
        }
    },
    watch : {
        showMap : function(newVal){
            if(newVal){
                const top = document.querySelector('#nav').getBoundingClientRect().height + 1;
                const target = document.querySelector('#unity-game-container')
                
                const targetRect = target.getBoundingClientRect();
                
                this.width=960
                this.height=540
                const unity = document.querySelector('#unity-game');
                unity.style.transform = `translate(470px,130px)`;
                
            }else{
                this.width = '150';
                this.height = '100';
            }
        }
    },
    destroyed(){
        clearInterval(this.interval);
    },

    methods : {
        getUnityHook(){
            if(this.$refs.hookInstance !== undefined) this.$refs.hookInstance.message('NetworkManager','initNick',this.user.userNickName);
            this.linked = true;
            this.objectName = "";
            
            this.interval = setInterval(()=>{
                
                if(document.getElementById('unity-object-name').innerHTML != this.objectName){
                    this.objectName = document.getElementById('unity-object-name').innerHTML;
                    switch (this.objectName) {
                        case "Computer": 
                            this.$router.push({name : 'practice'});
                            break;
                        case "Rain":
                            this.$router.push({name : 'acidRain'});
                            break;
                        case "Rock":
                            this.$router.push({name : 'rock'});
                            break;
                        case "Ranking":
                            this.$router.push({name : 'Award'});
                            break;
                        case "meeting":
                            this.$router.push({name : 'Meeting'});
                            break;
                        default:
                            break;
                    }
                    document.getElementById('unity-object-name').innerHTML = "";
                }
                else if(document.getElementById('unity-room-name').innerHTML!=="" && document.getElementById('unity-room-name').innerHTML !== this.RoomName){
                    this.RoomName = document.getElementById('unity-room-name').innerHTML;
                    console.log(this.RoomName)
                    this.$store.commit('setRoomName',this.RoomName);
                   
                    this.unityFocus = false;
                    this.enterMap = true;
                }
            },1000);
        },
        goUnity(){
            if(!this.showMap){
                this.$router.push({name : "Unity"}).catch(()=>{});;
            }
        },
    }

}
</script>
<style scoped src="../../css/UnityGame.css">
</style>