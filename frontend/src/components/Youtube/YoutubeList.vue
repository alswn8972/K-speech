<template>
    <div id="youtube-main">
        <div id="youtube-videos" :class="{share : youtubeShare.active, scroll: youtubeShare.active}">
            <div @click="showVideoDetail(video)" :class="{youtube : true, 'flex-item':true,'share-youtube' : youtubeShare.active}" v-for="(video, index) in videoList" :key="index">
                <img class="youtube-thumbnails" :src="video.snippet.thumbnails.medium.url">
                <div class="youtube-play"><v-icon class="play">fas fa-play</v-icon></div>
                <p class="youtube-title" v-html="video.snippet.title"/>
                <p class="youtube-channel" v-html="video.snippet.channelTitle"/>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
const YOUTUBE_KEY = 'AIzaSyDk0tZMTdba8SItcs7xoWiNxu8ThAs0L1M';

export default {
    name : "YoutubeList",
    data() {
        return {
            videoList : [],
        }
    },
    props : {
        youtubeShare : Object,
        location : String,
        maxHeight : Number,
    },
    created() {
        if(this.location == "library"){
            this.getYoutubeVideosStudy();
        }else{
            this.getYoutubeVideosMostPopular();
        }
    },
    mounted() {
        document.querySelector('#youtube-videos').setAttribute('style',`max-height : ${this.maxHeight-80}px !important`);
    },
    methods: {
        getYoutubeVideosMostPopular(){
            let http = 'https://www.googleapis.com/youtube/v3/videos?';
            http += `key=${YOUTUBE_KEY}&part=snippet&chart=mostPopular&regionCode=kr&maxResults=50`

            axios.get(http).then(( data ) => {
                this.videoList = data.data.items;
            }).catch((error) => {
                console.error(error);
            })
        },
        getYoutubeVideosStudy(){
            let http = 'https://youtube.googleapis.com/youtube/v3/search?';
            http += `key=${YOUTUBE_KEY}&part=snippet&type=video&order=viewCount&maxResults=50&q=공부 쓴소리`

            axios.get(http).then(( data ) => {
                this.videoList = data.data.items;
            }).catch((error) => {
                console.error(error);
            })
        },
        showVideoDetail(video){
            this.$emit('showVideoDetail', video);
        },
    },

}
</script>

<style scoped src="../../css/Youtube.css">
</style>