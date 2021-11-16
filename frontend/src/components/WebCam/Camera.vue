<template>
  <div id="webcam-container">
    <div id="webcam-title">
      <div id="school-title">
        <img src="@/../public/Image/school_icon.png" id="school-icon">
        <p>{{schoolName}}</p>
      </div>
    </div>
    <div id="webcam-main">
      <div id="share-container" v-if="data.share.active">
        <div id="share-screen" v-if="data.share.screen">
          <user-video class="flex-item screen-video" :stream-manager="data.share.screen"></user-video>
        </div>
        <div id="youtube-container" v-if="youtubeShare.active">
          <div id="mostPopular-title">
              <v-icon color="red" id="mostPopular-icon">fab fa-youtube</v-icon>
              <p>인기 동영상</p>
              <v-icon id="btnLeaveYoutube" @click="leaveYoutube">fas fa-times</v-icon>
          </div>
          <youtube-list :youtubeShare="youtubeShare" v-on:showVideoDetail="showVideoDetail" v-if="youtubeShare.showList" :maxHeight="maxHeight"/>
          <youtube-detail :videoDetail="youtubeShare.videoDetail" v-if="youtubeShare.showDetail" :maxHeight="maxHeight"/>
        </div>
      </div>
      <div id="video-container" :class="{'flex-column': data.share.active, 'screen-share' : data.share.active}">
        <div id="prev">
          <button class="webcam-button page-button" @click="page -= 1;" v-if="prev">
            <v-icon v-if="!data.share.active">fas fa-chevron-left</v-icon>
            <v-icon v-else>fas fa-angle-up</v-icon>
          </button>
        </div>
        <div id="videos" :class="{'flex-column': data.share.active}">
          <user-video :class="{publisher : true, 'flex-item': true, 'width-40': setWidth40, 'width-30' : setWidth30}" :stream-manager="data.publisher" v-if="page == 0"></user-video>
          <user-video :class="{subscribers : true, 'flex-item': true, 'width-40': setWidth40, 'width-30' : setWidth30}" v-for="(sub, idx) in pageSub" :key="idx" :stream-manager="sub"></user-video>
        </div>
        <div id="next">
          <button class="webcam-button page-button" @click="page += 1;" v-if="next">
            <v-icon v-if="!data.share.active">fas fa-chevron-right</v-icon>
            <v-icon v-else>fas fa-angle-down</v-icon>
          </button>
        </div>
      </div>
    </div>
    <div id="webcam-nav">
      <button id="btnSetvideo" @click="updateStream(0)" class="webcam-button">
          <div v-if="!data.setting.publishVideo"><v-icon id="unpublish-video">fas fa-video-slash</v-icon></div>
          <div v-else><v-icon id="publish-video">fas fa-video</v-icon></div>
      </button>
      <button id="btnSetAudio" @click="updateStream(1)" class="webcam-button">
          <div v-if="!data.setting.publishAudio"><v-icon id="unpublish-audio">fas fa-microphone-slash</v-icon></div>
          <div v-else><v-icon id="publish-audio">fas fa-microphone</v-icon></div>
      </button>
      <button id="btnShareScreen" @click="shareScreen" class="webcam-button">
        <div v-if="!screenShare"><v-icon id="unpublish-screen">fas fa-upload</v-icon></div>
        <div v-else><v-icon id="publish-screen">fas fa-upload</v-icon></div>
      </button>
      <button id="btnShareYoutube" @click="getYoutubeVideo" class="webcam-button" v-if="location != 'computer'">
        <div v-if="!youtubeShare.active"><v-icon id="unpublish-youtube">fab fa-youtube</v-icon></div>
        <div v-else><v-icon id="publish-youtube">fab fa-youtube</v-icon></div>
      </button>
      <button id="btnLeaveSession" @click="leaveSession" class="webcam-button"><v-icon id="leave-session">fas fa-phone-alt</v-icon></button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import UserVideo from "@/components/WebCam/UserVideo";
import YoutubeList from "@/components/Youtube/YoutubeList"
import YoutubeDetail from '@/components/Youtube/YoutubeDetail';
axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "Camera",
  components: {
    UserVideo,
    YoutubeList,
    YoutubeDetail
  },
  data() {
    return {
      page : 0,
      youtubeShare : {
        active : false,
        showList : false,
        showDetail : false,
        videoDetail : undefined,
      },
      screenShare : false,
      schoolName : '',
      maxHeight : 0,
    }
  },
  props :{
    data : Object,
    location : String,
  },
  created() {
    this.schoolName = this.$store.getters.getSchoolName;
  },
  mounted() {
    const target = document.querySelector('#webcam-main')
    const targetRect = target.getBoundingClientRect();
    this.maxHeight = targetRect.height;
  },
  updated(){
    const container = document.querySelector('#mostPopular-title');
    if(container !== null) container.setAttribute('style', `z-index:unset;`);
    const screen = document.querySelector('.screen-video video');
    if(screen !== null) screen.setAttribute('style', `max-height:${this.maxHeight-80}px;`);
  },
  computed : {
    setWidth40 : function(){
      if(this.data.subscribers.length < 2 && !this.data.share.active){
        return true;
      }
      return false;
    },
    setWidth30 : function(){
      if(this.data.subscribers.length >= 2 && !this.data.share.active){
        return true;
      }
      return false;
    },
    next : function(){
      if((!this.data.share.active && this.data.subscribers.length+1 - (this.page+1)*6 > 0 ) || (this.data.share.active && this.data.subscribers.length+1 - (this.page+1)*4 > 0 )){
        return true;
      }
      return false;
    },
    prev : function(){
      if(this.page > 0){
        return true;
      }
      return false;
    },
    totalPage : function(){
      let remain = (this.data.subscribers.length+1)%6;
      if(remain != 0){
        return (this.data.subscribers.length+1)/6+1;
      }
      return (this.data.subscribers.length+1)/6;
    },
    pageSub : function(){
      if(this.page == 0){
        if(!this.data.share.active){
          return this.data.subscribers.slice(0,5);
        }
        return this.data.subscribers.slice(0,3);
      }else{
        if(!this.data.share.active){
          return this.data.subscribers.slice(this.page*5, Math.min(this.page*5+6,this.data.subscribers.length));
        }
        return this.data.subscribers.slice(this.page*3, Math.min(this.page*3+4,this.data.subscribers.length));
      }
    },
  },
  methods: {
    updateMainVideoStreamManager(stream) {
      if (this.data.mainStreamManager === stream) return;
      this.data.mainStreamManager = stream;
    },
    updateStream(type) {
      this.$emit('updateStream', type);
    },
    shareScreen() {
      let screen = this.data.OV.initPublisher(undefined, {
        resolution: "1280x720",
        videoSource: "screen",
        publishAudio : this.data.setting.publishAudio,
      });

      screen.once("accessAllowed", () => {
        screen.stream
          .getMediaStream()
          .getVideoTracks()[0]
          .addEventListener("ended", () => {
            this.data.session.unpublish(screen);
            this.screenShare = false;
            this.data.share.active = false;
            this.data.share.screen = undefined;
            this.data.session.publish(this.data.publisher);
          });
        
        this.data.session.unpublish(this.data.publisher);
        this.screenShare = true;
        if(this.youtubeShare.active){
          this.youtubeShare.active = false;
          this.youtubeShare.showList = false;
          this.youtubeShare.showDetail = false;
        }
        this.data.share.active = true;
        this.data.share.screen = screen;
        this.data.session.publish(this.data.share.screen);
      });
      screen.once("accessDenied", () => {
        console.warn("ScreenShare: Access Denied");
      });
    },
    leaveSession() {
        this.data.share.active = false;
        this.youtubeShare.active = false;
        this.youtubeShare.showList = false;
        this.youtubeShare.showDetail = false;
        this.youtubeShare.videoDetail = undefined;
        this.$emit('leaveSession');
    },
    getYoutubeVideo(){
      if(this.data.share.screen === undefined){
        if(!this.youtubeShare.active){
          this.data.share.active = true;
          this.youtubeShare.active = true;
          this.youtubeShare.showList = true;
        }else{
          this.data.share.active = false;
          this.youtubeShare.active = false;
          this.youtubeShare.showList = false;
          this.youtubeShare.showDetail = false;
          this.youtubeShare.videoDetail = undefined;
        }
          this.data.share.screen = undefined;
          this.screenShare = false;
      }
    },
    showVideoDetail(video){
      this.youtubeShare.showList = false;
      this.youtubeShare.showDetail = true;
      this.youtubeShare.videoDetail = video;
    },
    leaveYoutube(){
      if(this.youtubeShare.showDetail){
        this.youtubeShare.showList = true;
        this.youtubeShare.showDetail = false;
      }else{
        this.data.share.active = false;
        this.youtubeShare.active = false;
        this.youtubeShare.showList = false;
      }
    }
  },
};
</script>

<style scoped src="../../css/Camera.css">
</style>