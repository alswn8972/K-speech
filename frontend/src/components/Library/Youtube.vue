<template>
    <div id="library-youtube">
        <div id="youtube-container">
          <div id="mostPopular-title">
              <v-icon color="red" id="mostPopular-icon">fab fa-youtube</v-icon>
              <p>쓴소리 영상</p>
          </div>
          <youtube-list :youtubeShare="youtubeShare" :location="location" v-on:showVideoDetail="showVideoDetail" v-if="youtubeShare.showList"/>
        </div>
        <v-dialog v-model="dialog" width="60%">
            <v-card>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-icon @click="dialog = false">fas fa-times</v-icon>
            </v-card-actions>
            <v-divider></v-divider>
    
            <v-card-text>
                <youtube-detail :videoDetail="youtubeShare.videoDetail" v-if="youtubeShare.showDetail"/>
            </v-card-text>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
import YoutubeList from '../Youtube/YoutubeList';
import YoutubeDetail from '../Youtube/YoutubeDetail';
export default {
    name : "Youtube",
    components : {
        YoutubeList,
        YoutubeDetail
    },
    data() {
        return {
            youtubeShare : {
                active : false,
                showList : true,
                showDetail : false,
                videoDetail : undefined,
            },
            location : "library",
            dialog : false,
            player :  null,
        }
    },
    watch:{
      dialog: function(){
        if(!this.dialog){
          const a = document.querySelector('#player');
          a && a.remove();
        }
      }
    },
    methods: {
        showVideoDetail(video){
            this.dialog = true;
            this.youtubeShare.showDetail = true;
            this.youtubeShare.videoDetail = video;
        }
    },

}
</script>

<style scoped>
@import '../../css/Youtube.css';
#library-youtube{
    width : 80%;
    margin: 10px auto 0px;
}
#youtube-modal{
    width : 80%;
}
</style>