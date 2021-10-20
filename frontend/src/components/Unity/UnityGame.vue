<template>
    <div id="unity-game" :class="{'small-map':!showMap}" @click="goUnity">
        <div id="game-container">
            <div id="mini-map-alt" class="map-alt" v-if="!showMap">
                <div class="mini-alt">
                    <v-icon id="mini-alt-icon">fas fa-sign-in-alt</v-icon>
                    <p id="mini-alt-text">학교로 <br> 돌아가기</p>
                </div>
            </div>
            <div id="keydown-map-alt" class="map-alt" v-if="keydownAlt">
                <v-icon id="keydown-alt-icon">fas fa-exclamation-circle</v-icon>
                <p id="keydown-alt-text">캐릭터를 움직이기 위해선 게임 화면을 클릭해 주세요.</p>
            </div>
            <unity id="bts-unity"
            src = "./unity/Build/webGL.json"
            unityLoader = "./unity/Build/UnityLoader.js"
            ref = "hookInstance"
            :height = height
            :width = width
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
import http from '../../util/http-common.js';

export default {
    name : "UnityGame",
    data() {
        return {
            user: {},
            objectName : '',
            schoolName : '',
            linked : false,
            height : '700',
            width : '950',
            interval : '',
            mapHeight : 0,
            mapWidth : 0,
            unityFocus : false,
            keydownAlt : false,
            enterMap : false,
        }
    },
    components : {Unity},
    created(){
      if(this.$store.state.user===null){
        this.$router.push('/login');
      }
      this.schoolName=this.$store.state.schoolName;
      this.user=this.$store.getters.getUser;
    },
    mounted() {
        this.enterMap = false;
        this.schoolName = "";
        this.$store.commit('setIsSubmit',true);
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
        const unity = document.querySelector('#unity-game');
        unity.style.transform = `translate(${targetRect.left}px,102px)`;
        document.addEventListener(
            "click",
            function (event) {
                if (event.target.closest("#game-container")){ // 유니티 가능
                    if(this.$refs.hookInstance !== undefined) this.$refs.hookInstance.message('Game Manager','focusing',"true");
                    this.unityFocus = true;
                    this.keydownAlt = false;
                }else{ // 윈도우 인풋 가능
                    if(this.$refs.hookInstance !== undefined)  this.$refs.hookInstance.message('Game Manager','focusing',"false");
                    this.unityFocus = false;
                }
            }.bind(this)
        );
        document.addEventListener(
            "keydown",
            function (event) {
                if(this.$route.name === "Unity" && !this.unityFocus && this.enterMap){
                    this.keydownAlt = true;
                }
            }.bind(this)
        );
    },
    updated(){
        window.addEventListener('resize', () => {
            if(this.showMap && (this.mapHeight !== document.querySelector('#unity-game-container').getBoundingClientRect().height || this.mapWidth !== document.querySelector('#unity-game-container').getBoundingClientRect().width)){
                // const top = document.querySelector('#nav').getBoundingClientRect().height + 1;
                const target = document.querySelector('#unity-game-container')
                const targetRect = target.getBoundingClientRect();
                this.width = targetRect.width;
                this.height = window.innerHeight-102;
                const unity = document.querySelector('#unity-game');
                unity.style.transform = `translate(${targetRect.left}px,102px)`;
                this.mapHeight = document.querySelector('#unity-game-container').getBoundingClientRect().height;
                this.mapWidth = document.querySelector('#unity-game-container').getBoundingClientRect().width;
            }
        })
    },
    destroyed(){
        clearInterval(this.interval);
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
                this.width = targetRect.width;
                this.height = window.innerHeight-102;
                const unity = document.querySelector('#unity-game');
                unity.style.transform = `translate(${targetRect.left}px,102px)`;
            }else{
                this.width = '150';
                this.height = '100';
            }
        }
    },
    methods : {
        getUnityHook(){
            if(this.$refs.hookInstance !== undefined) this.$refs.hookInstance.message('LobbyManager','initPlayerName',this.user.userNickname);
            this.linked = true;
            this.objectName = "";
            this.interval = setInterval(()=>{
                if(document.getElementById('unity-object-name').innerHTML != this.objectName){
                    this.objectName = document.getElementById('unity-object-name').innerHTML;
                    switch (this.objectName) {
                        case "information": // 정보공유/코드공유 - blackboard
                            this.$router.push({name : 'InfoBoard'});
                            break;
                        case "qna": // Q&A게시판 - noticeboard
                            this.$router.push({name : 'QnaBoard'});
                            break;
                        case "computer": // 컴퓨터실 웹캠 - desk
                            this.$router.push({name : 'Computer'});
                            break;
                        case "rest": // 휴게실 웹캠 - vendingmachine
                            this.$router.push({name : 'Rest'});
                            break;
                        case "cook": // 급식실 웹캠 - table
                            this.$router.push({name : 'Cook'});
                            break;
                        case "calendar": // 공부 플래너
                            this.$router.push({name : 'Calendar'});
                            break;
                        case "youtube": // 쓴소리 영상 - advice
                            this.$router.push({name : 'Youtube'});
                            break;
                        case "guestbook":
                            this.$router.push({name : 'GuestBook'});
                            break;
                        case "award":
                            this.$router.push({name : 'Award'});
                            break;
                        default:
                            break;
                    }
                    document.getElementById('unity-object-name').innerHTML = "";
                }else if(document.getElementById('unity-school-name').innerHTML!=="" && document.getElementById('unity-school-name').innerHTML !== this.schoolName){
                    this.schoolName = document.getElementById('unity-school-name').innerHTML;
                    //get 해서 방번호 저장하기
                    http.get(`v1/room/${this.schoolName}`)
                      .then(res=>{
                        if(res.data.data==="존재하지 않는 방입니다."){
                          http.post(`v1/room/${this.schoolName}`)
                            .then(res2=>{
                              this.$store.commit('setSchoolId',res2.data.data);
                              this.$store.commit('setSchoolName',this.schoolName);
                            })
                            .catch(err=>{
                              console.error(err);
                            })
                        } else{
                          this.$store.commit('setSchoolId',res.data.data);
                          this.$store.commit('setSchoolName',this.schoolName);
                        }
                      })
                    this.unityFocus = false;
                    this.enterMap = true;
                }
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