<template>
<div id="chat-container">
    <div id="chat-nav">
        <div class="icon">
            <img src="@/../public/Image/bts_favicon.png" id="bts-icon">
        </div>
        <div class="icon-bottom">
            <div @click="showChange(1)" class="messenger">
                <v-icon id="message-icon" :class="{active : showChatting}">fas fa-comments</v-icon>
                <v-icon class="bell" v-if="showBell">fas fa-bell</v-icon>
            </div>
            <div @click="showChange(2)">
                <v-icon id="participant-icon" :class="{active : showUsers}">fas fa-users</v-icon>
            </div>
            <div id="live-container">
                <div id="live-circle"></div>
                <div id="participant-counter">{{ data.subscribers.length+1 }}명</div>
            </div>
        </div>
        
    </div>
    <div id="chat-main">
        <div id="chat-title">
            <p v-if="showChatting">Chat</p>
            <p v-if="showUsers">User</p>
        </div>
        <div id="receive-container" v-if="showChatting">
            <div class="message"  v-for="(message, index) in data.receiveMessage" :key="index">
            <table class="message-table" v-if="message.sender.userId !== user.userId">
                <tr class="user-profile">
                <td rowspan="2">
                    <img class="user-img" v-if="message.sender.userImg===''" src="@/../public/Image/user_profile.png"/>
                    <img class="user-img" v-else :src="message.sender.userImg"/>
                </td>
                <td class="user-name">{{message.sender.userNickname}}</td>
                </tr>
                <tr>
                <td><p class="user-message">{{message.message}}</p></td>
                </tr>
            </table>
            <table class="message-table my-message" v-else>
                <tr class="user-profile">
                <td class="user-name">{{message.sender.userNickname}}</td>
                <td rowspan="2">
                    <img class="user-img" v-if="message.sender.userImg===''" src="@/../public/Image/user_profile.png" />
                    <img class="user-img" v-else :src="message.sender.userImg"/>
                </td>
                </tr>
                <tr>
                <td><p class="user-message">{{message.message}}</p></td>
                </tr>
            </table>
            </div>
        </div>
        <div id="participant-container" v-if="showUsers">
            <div class="participant-info my-info">
                <img class="user-img" v-if="user.userImg===''" src="@/../public/Image/user_profile.png" />
                <img class="user-img" v-else :src="user.userImg"/>
                <p class="inline-p">{{user.userNickname}} (me)</p>
            </div>
            <div class="participant-info" v-for="(sub, index) in data.subscribers" :key="index">
                <img class="user-img" v-if="JSON.parse(sub.stream.connection.data).userImg===''" src="@/../public/Image/user_profile.png" />
                <img class="user-img" v-else :src="JSON.parse(sub.stream.connection.data).userImg"/>
                <p class="inline-p">{{JSON.parse(sub.stream.connection.data).userNickname}}</p>
            </div>
            <!-- <div class="participant-info" v-for="(user, index) in users" :key="index">
                <v-icon color="var(--color-white)">fas fa-user</v-icon>
                <p class="inline-p">{{user}}</p>
            </div> -->
        </div>
        <div id="input-container" v-if="showChatting">
            <div id="input-form">
            <input id="sendMessage" class="input-message" type="text" v-model="sendMessage" @keyup.enter="send">
            <v-icon id="btnSendMessage" @click="send">fas fa-location-arrow</v-icon>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import Inko from 'inko';
axios.defaults.headers.post["Content-Type"] = "application/json";
const OPENVIDU_SERVER_URL = "https://k4b107.p.ssafy.io:4443";
const OPENVIDU_SERVER_SECRET = "BACK_TO_SCHOOL";
export default {
    name : "User",
    data() {
        return {
            participants : 1,
            users : [],
            usersName : "",
            interval : '',
            sendMessage : "",
            showChatting : false,
            showUsers : true,
            user : {},
            data : {
                OV : undefined,
                roomName : '',
                receiveMessage : [],
                subscribers : [],
                publisher : undefined,
                receiveMessageBell : false,
            }
        }
    },
    computed : {
        showBell : function(){
            if(this.showChatting){
                return false;
            }
            return this.data.receiveMessageBell;
        }
    },
    created() {
        if(this.$store.state.user===null){
            this.$router.push('/login');
            return ;
        }
        this.user = this.$store.getters.getUser;
        this.schoolName =  this.$store.getters.getSchoolName;
        if(this.schoolName === null){
            this.schoolName = 'unity-system-Lobby'
        }
        let inko = new Inko();
        let name = this.schoolName.replaceAll(" ","");
        this.data.roomName = inko.ko2en(name);

        this.joinSession();
    },
    destroyed(){
        if (this.data.session) this.data.session.disconnect();
        this.data.session = undefined;
        this.data.publisher = undefined;
        this.data.subscribers = [];
        this.data.OV = undefined;
        this.data.receiveMessage = [];
    },
    updated() {
        let container = this.$el.querySelector("#receive-container, #participant-container");
        container.scrollTop = container.scrollHeight;
    },
    computed:{ 
        check_schoolName : function(){
            return this.$store.getters.getSchoolName;
        },
        showBell : function(){
            if(this.showChatting){
                this.data.receiveMessageBell = false;
                return false;
            }
            return this.data.receiveMessageBell;
        },
    },
    watch:{ 
        check_schoolName(val){ 
            this.leaveSession();

            this.schoolName =  val;
            if(this.schoolName === null){
                this.schoolName = 'unity-system-Lobby'
            }
            let inko = new Inko();
            let name = this.schoolName.replaceAll(" ","");
            this.data.roomName = inko.ko2en(name);

            this.joinSession();
        } 
    },
    methods: {
        joinSession() {
            this.data.OV = new OpenVidu();
            this.data.session = this.data.OV.initSession();

            this.data.session.on("streamCreated", ({ stream }) => {
            const subscriber = this.data.session.subscribe(stream);
            this.data.subscribers.push(subscriber);
            this.data.participants = this.data.subscribers.length+1;
            });
            this.data.session.on("streamDestroyed", ({ stream }) => {
            const index = this.data.subscribers.indexOf(stream.streamManager, 0);
            if (index >= 0) {
                this.data.subscribers.splice(index, 1);
            }
            this.data.participants = this.data.subscribers.length+1;
            });
            this.data.session.on("signal:my-chat", (event) => {
            this.data.receiveMessage.push({sender : JSON.parse(event.from.data), message : event.data});
            this.data.receiveMessageBell = true;
            });

            this.getToken(this.data.roomName).then((token) => {
            this.data.session
                .connect(token, this.user)
                .then(() => {
                let publisher = this.data.OV.initPublisher(undefined, {
                    audioSource: undefined,
                    videoSource: undefined,
                    publishAudio: false,
                    publishVideo: false,
                    insertMode: "APPEND",
                });
                this.data.publisher = publisher;

                this.data.session.publish(this.data.publisher);
                })
                .catch((error) => {
                console.error(
                    "There was an error connecting to the session:",
                    error.code,
                    error.message
                );
                });
            });

            window.addEventListener("beforeunload", this.data.leaveSession);
        },
        leaveSession() {
            if (this.data.session) this.data.session.disconnect();

            this.data.session = undefined;
            this.data.publisher = undefined;
            this.data.subscribers = [];
            this.data.OV = undefined;
            this.data.receiveMessage = [];
            window.removeEventListener("beforeunload", this.data.leaveSession);
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
        send() {
            if(this.sendMessage.length > 0){
                this.data.session.signal({
                    data: this.sendMessage,
                    to: [],
                    type: "my-chat",
                }).then(() => {
                  
                }).catch((error) => {
                    console.error(error);
                });
                this.sendMessage = '';
            }
        },
        showChange(type){
            if(type == 1){
                this.showChatting = true;
                this.showUsers = false;
                this.data.receiveMessageBell = false;
            }else if(type == 2){
                this.showChatting = false;
                this.showUsers = true;
            }
        }
    },
}
</script>

<style scoped src="../../css/Chat.css">
</style>