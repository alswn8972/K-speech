<template>
  <div class="award">
    <div class="award_area">
      <h2 class="h2">명예의 전당</h2>
      <div class="laurel_img">
        <img src="../../../public/Image/laurel_wreath.png" alt="" />
      </div>
      <div class="award_inner">
        <div class="award_left">
          <div class="trophy_img">
            <img src="../../../public/Image/trophy.png" alt="" />
          </div>
          <div class="left_user user1">
            <div class="left_name">
              <div class="left_user_img">
                <img
                  :src="`${publicPath}Image/level/level_${users[0].userLank}.png`"
                />
              </div>
              <span class="left_user_name">{{ users[0].userNickname }}</span>
            </div>
            <div class="left_user_point">{{ users[0].userPoint }}p</div>
          </div>
          <div class="left_user user2">
            <div class="left_name">
              <div class="left_user_img">
                <img
                  :src="`${publicPath}Image/level/level_${users[1].userLank}.png`"
                />
              </div>
              <span class="left_user_name">{{ users[1].userNickname }}</span>
            </div>
            <div class="left_user_point">{{ users[1].userPoint }}p</div>
          </div>
          <div class="left_user user3">
            <div class="left_name">
              <div class="left_user_img">
                <img
                  :src="`${publicPath}Image/level/level_${users[2].userLank}.png`"
                />
              </div>
              <span class="left_user_name">{{ users[2].userNickname }}</span>
            </div>
            <div class="left_user_point">{{ users[2].userPoint }}p</div>
          </div>
        </div>
        <div class="award_right">
          <ul>
            <li v-for="(u, index) in users.slice(3, 10)" :key="index">
              <p class="ranking">{{ index + 4 }}위</p>
              <div class="level_img">
                <img
                  :src="`${publicPath}Image/level/level_${u.userLank}.png`"
                />
              </div>
              <span class="user_name">{{ u.userNickname }}</span>
              <span class="user_score">{{ u.userPoint }}p</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "award",
  components: {},
  data() {
    return {
      users: [],
      publicPath: process.env.BASE_URL,
    };
  },
  created() {
    if(this.$store.getters.getUser === null){
      this.$router.push('/login');
    } else if(this.$store.getters.getSchoolId === null) {
      this.$router.push({
        name: 'Unity',
      })
    }       
    http
      .get(`/v1/user/award/`)
      .then((res) => {
        this.users = res.data.data;
      })
      .catch((err) => console.error(err));
  },
  methods: {},
};
</script>

<style scoped src="../../css/Award.css">
