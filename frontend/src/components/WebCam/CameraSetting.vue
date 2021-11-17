
<template>
  <!--배경-->
  <div class="camSetting">
    <!--가운데 내용-->
    <div class="camSetting_content">
      <div class="content_inner">
        <p style="font-family: neodgm_pro; font-size: xx-large;">화상 설정</p>
        
        <div class="boxes">
          <div class="left_box">
            <user-video id="preview-camera" class="publisher flex-item" :stream-manager="publisher"></user-video>
          </div>
          <div class="right_box">
           
            <ul class="box_ul">
              <li>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="1">
                      <i class="fas fa-video icon fa-lg"></i>
                    </v-col>
                    <v-col cols="12" sm="11">
                      <v-select
                        v-model="selectWebCam"
                        :items="webcam"
                        label="카메라 설정"
                        item-text="label"
                        item-value="deviceId"
                        filled
                        @change="changeCamera"
                      ></v-select>
                    </v-col>
                  </v-row>
                </v-container>
              </li>
              <li>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="1">
                      <i class="fas fa-microphone icon fa-lg"></i>
                    </v-col>
                    <v-col cols="12" sm="11">
                      <v-select
                        v-model="selectAudio"
                        :items="mic"
                        label="마이크 설정"
                        item-text="label"
                        item-value="deviceId"
                        filled
                        @change="changeAudio"
                      ></v-select>
                    </v-col>
                  </v-row>
                </v-container>
              </li>
              <li>
                <v-container>
                  <div class="device_btn"><v-icon @click="findDevices">fas fa-sync-alt</v-icon></div>
                </v-container>
              </li>
            </ul>
          </div>
        </div>
        
        <div @click="changeSetting" class="pixel2" style="margin-top : 60px;" >설정완료</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/WebCam/UserVideo";

const OPENVIDU_SERVER_URL = "https://k5d104.p.ssafy.io:443";
const OPENVIDU_SERVER_SECRET = "k5d104";
axios.defaults.headers.post['Content-Type'] = 'application/json';

export default {
  name: "CameraSetting",
  components : {
    UserVideo,
  },
  data() {
    return {
      OV : undefined,
      roomName : 'setting',
      session : undefined,
      publisher : undefined,
      webcam: [],
      mic: [],
      selectWebCam : '',
      selectAudio : '',
      setting: {
        audioSource: undefined,
        videoSource: undefined,
        publishAudio: false,
        publishVideo: false,
        resolution: "640x480",
        frameRate: 30,
        insertMode: "APPEND",
        mirror: false,
      },
      user : {},
    };
  },
  created(){
    this.joinSession();
    this.user = this.$store.getters.getUser;
    
  },
  destroyed(){
    this.leaveSession();
  },
  methods: {
    changeSetting(){
      // 설정 변경
      this.leaveSession();
      this.$router.push({
        name:'Unity',
      })
    },
    findDevices(){
      this.OV.getDevices().then(devices => {
        let videoDevices = devices.filter(device => device.kind === 'videoinput');
        let audioDevices = devices.filter(device => device.kind === 'audioinput');

        this.webcam = videoDevices;
        this.mic = audioDevices;
      });
    },
    changeCamera(){
      this.publisher.stream.outboundStreamOpts.publisherProperties.videoSource = this.selectWebCam;
      this.publisher.publishVideo(true);
      this.$store.commit('setVideo',this.selectWebCam);
    },
    changeAudio(){
      this.publisher.stream.outboundStreamOpts.publisherProperties.audioSource = this.selectAudio;
      this.publisher.publishAudio(true);
      this.$store.commit('setAudio',this.selectAudio);
    },
    joinSession() {
      this.OV = new OpenVidu();
      this.session = this.OV.initSession();
      this.getToken(this.roomName).then((token) => {
        this.session
          .connect(token, this.user)
          .then(() => {
            this.findDevices();
            let publisher = this.OV.initPublisher(undefined, this.setting);
            this.publisher = publisher;

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });
    },
    leaveSession() {
      if (this.session) this.session.disconnect();
      this.session = undefined;
      this.publisher = undefined;
      this.OV = undefined;
    },
    getToken(roomName) {
      return this.createSession(roomName).then((sessionId) =>
        this.createToken(sessionId)
      );
    },
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
  },
};
</script>


<style scoped src="../../css/CamSetting.css">
</style>