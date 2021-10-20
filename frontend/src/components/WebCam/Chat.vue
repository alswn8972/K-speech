<template>
<div id="chat-container">
    <div id="chat-nav">
        <div class="icon" @click="showChange(0)">
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
                <div id="participant-counter">{{ data.participants }}명</div>
            </div>
        </div>
        
    </div>
    <div id="chat-main" v-if="showChatting || showUsers">
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
export default {
    name : "Chat",
    data() {
        return {
            sendMessage : "",
            showChatting : false,
            showUsers : false,
            user : {},
        }
    },
    props :{
        data : Object,
    },
    updated() {
        let container = this.$el.querySelector("#receive-container,#participant-container");
        if(container !== null) container.scrollTop = container.scrollHeight;
    },
    created() {
        this.user = this.$store.getters.getUser;
    },
    computed : {
        showBell : function(){
            if(this.showChatting){
                this.data.receiveMessageBell = false;
                return false;
            }
            return this.data.receiveMessageBell;
        },
    },
    methods: {
        send(){
            if(this.sendMessage.length > 0){
                this.$emit('sendMessage', this.sendMessage);
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
            }else{
                this.showChatting = false;
                this.showUsers = false;
            }
        }
    },
}
</script>

<style scoped src="../../css/Chat.css">
</style>