<template>
  <div class="login">
    <div class="login_content">
      <div class="login_leftBox">
        <img class="login_img" src="../../../public/Image/login_img.png" />
      </div>
      <div class="login_rightBox">
        <div class="login_input">
          <h1 class="login_header">로그인</h1>
          <p class="text">환영합니다. 로그인 해주세요</p> 

          <form @submit="onSubmit" v-on:keyup.enter="onSubmit" class="login-form">
            <label class="login_mid_header" for="user-id">아이디</label>
            <v-text-field
              id="user-id"
              v-model="id"
              class="login_input_c"
              placeholder="최대 10자입니다. "
              single-line
              max=10
            ></v-text-field>

            <label class="login_mid_header" for="user-pw">비밀번호</label>
            <v-text-field
              id="user-pw"
              v-model="password"
              type=password
              class="login_input_c"
              placeholder="8자~11자입니다. "
              single-line
              max=11
            ></v-text-field>

            <input type="checkbox" id="checkbox" v-model="checked" />
            <label class="id_remember" for="checkbox">아이디 기억하기</label>

            <div class="text-center">
              <v-btn class="login_btn" rounded :color="classObject" dark @click="onSubmit">LOGIN</v-btn>
            </div>
          </form>
          <div class="moves">
            <router-link class="move" to="/join">회원가입</router-link> |
            <router-link class="move" to="/findpw">비밀번호 찾기</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import Swal from 'sweetalert2'
import PV from "password-validator";

export default {
  name: "Login",
  data: () => {
    return {
      checked: false,
      id: "",
      password: "",
      passwordSchema: new PV(),
      component : this
    }
  },
  created(){
    if(this.$store.getters.getUser!==null){
      this.$router.push({
        name:'Unity'
      })
    }
    this.component = this;
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(11)
      .has()
      .digits()
      .has()
      .letters();
    
    if(this.$cookie.get('rememberId') != undefined){
      this.id = this.$cookie.get('rememberId');
      this.checked = true;
    }
  },
  computed: {
    classObject: function(){
      if(this.id.length>0 && this.id.length<=10 && this.password.length>=8 && this.password.length<12)
        return "#04338C";
      return  "#6482B9";
    },

  },
  methods: {
    onSubmit(event){
      event.preventDefault();

      if(this.id.length<=0 || this.id.length>10){
        Swal.fire({
          icon: "error",
          text: "아이디는 최대 10자입니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }
      if(this.password.length<8 || this.password.length>=12){
        Swal.fire({
          icon: "error",
          text: "비밀번호는 8 ~ 11자입니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      if(this.checked){
        this.$cookie.set('rememberId', this.id , 1);
      }else{
        this.$cookie.delete('rememberId');
      }

      const user = {
        userId: this.id,
        userPw: this.password
      }

      http
      .post("v1/login", JSON.stringify(user))
      .then((res) => {
         const token = res.data["auth_token"];
         if(token){
            this.$store.commit('login',res.data.user);
            this.$router.push({
              name:'CamSetting'
            }); 
          }
          else{
            Swal.fire({
            icon: "error",
            text: res.data['message'],
            showConfirmButton: false,
            timer: 1000,
            });
          }
      })
      .catch((err) => {
        console.error(err);
      });
    }
  }
};
</script>

<style scoped src="../../css/Login.css">
</style>
