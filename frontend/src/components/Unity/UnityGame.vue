<template>
   <div id="unity-game" :class="{'small-map':!showMap}" @click="goUnity">
        <div id="game-container">
            <div id="mini-map-alt" class="map-alt" v-if="!showMap">
                <div class="mini-alt">
                    <v-icon id="mini-alt-icon">fas fa-sign-in-alt</v-icon>
                    <p id="mini-alt-text">돌아가기</p>
                </div>
            </div>
            <div id="keydown-map-alt" class="map-alt" v-if="keydownAlt">
                <v-icon id="keydown-alt-icon">fas fa-exclamation-circle</v-icon>
                <p id="keydown-alt-text">캐릭터를 움직이기 위해선 게임 화면을 클릭해 주세요.</p>
            </div>
             <!-- hideFooter : 하단의 Full screen을 표시유무 , ref : 유니티와의 커뮤니케이션-->
            <unity id="bts-unity" 
            src = "./unity/Build/unityWebgl.json"
            unityLoader = "./unity/Build/UnityLoader.js"
            :width=width
            :height=height
            ref = "hookInstance"
            :hideFooter="true"
            >
            </unity>
            <button id="link-btn" @click="getUnityHook" v-if="!linked">계정 연동</button>
        </div>
        <div id="unity-school-name" hidden></div>
        <div id="unity-object-name" hidden></div>
    </div>

</template>
<script>
import Unity from 'vue-unity-webgl';
//import http from '../../util/http-common.js';

export default {

    components : {Unity},
     data() {
        return {
            user: {},
            nickname : '' ,
            objectName : '',
            schoolName : '',
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
      //this.schoolName=this.$store.state.schoolName;
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
        // document.addEventListener(
        //     "click",
        //     function (event) {
        //         if (event.target.closest("#game-container")){ // 유니티 가능
        //             if(this.$refs.hookInstance !== undefined) this.$refs.hookInstance.message('Game Manager','focusing',"true");
        //             this.unityFocus = true;
        //             this.keydownAlt = false;
        //         }else{ // 윈도우 인풋 가능
        //             if(this.$refs.hookInstance !== undefined)  this.$refs.hookInstance.message('Game Manager','focusing',"false");
        //             this.unityFocus = false;
        //         }
        //     }.bind(this)
        // );
        // document.addEventListener(
        //     "keydown",
        //     function (event) {
        //         if(this.$route.name === "Unity" && !this.unityFocus && this.enterMap){
        //             this.keydownAlt = true;
        //         }
        //     }.bind(this)
        // );
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
                //직사각형 만들기
                const targetRect = target.getBoundingClientRect();
                // this.width = targetRect.width;
                // this.height = window.innerHeight-102;
                this.width=960
                this.height=540
                const unity = document.querySelector('#unity-game');
                unity.style.transform = `translate(470px,130px)`;
                //unity.style.transform = `translate(${targetRect.left}px+20px,102px)`;
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
            //닉네임 연동
            if(this.$refs.hookInstance !== undefined) this.$refs.hookInstance.message('NetworkManager','initNick',this.user.userNickName);
            this.linked = true;
            this.objectName = "";
            
            this.interval = setInterval(()=>{
                //console.log("Dddd",document.getElementById('unity-object-name').innerHTML);
                if(document.getElementById('unity-object-name').innerHTML != this.objectName){
                    this.objectName = document.getElementById('unity-object-name').innerHTML;
                    console.log("objectName",this.objectName);
                    switch (this.objectName) {
                        case "Computer": // 정보공유/코드공유 - blackboard
                            this.$router.push({name : 'practice'});
                            break;
                        case "Ranking":
                            this.$router.push({name : 'Award'});
                            break;
                        default:
                            break;
                    }
                    document.getElementById('unity-object-name').innerHTML = "";
                }
                // else if(document.getElementById('unity-school-name').innerHTML!=="" && document.getElementById('unity-school-name').innerHTML !== this.schoolName){
                //     this.schoolName = document.getElementById('unity-school-name').innerHTML;
                //     //get 해서 방번호 저장하기
                //     http.get(`v1/room/${this.schoolName}`)
                //       .then(res=>{
                //         if(res.data.data==="존재하지 않는 방입니다."){
                //           http.post(`v1/room/${this.schoolName}`)
                //             .then(res2=>{
                //               this.$store.commit('setSchoolId',res2.data.data);
                //               this.$store.commit('setSchoolName',this.schoolName);
                //             })
                //             .catch(err=>{
                //               console.error(err);
                //             })
                //         } else{
                //           this.$store.commit('setSchoolId',res.data.data);
                //           this.$store.commit('setSchoolName',this.schoolName);
                //         }
                //       })
                //     this.unityFocus = false;
                //     this.enterMap = true;
                // }
            },1000);
        },
        goUnity(){
            if(!this.showMap){
                this.$router.push({name : "Unity"});
            }
        },
    }

}
</script>
<style scoped src="../../css/UnityGame.css">
</style>