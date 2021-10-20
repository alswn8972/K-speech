<template>
  <div class="join1">
    <div class="join_content">
      <div class="join_leftBox">
         <div class="join_input">
          <h1 class="join_header join2_head">회원가입</h1>
          <p class="text">본인인증을 해주세요.</p>

          <form class="join_form">
            <p class="mid_join_header" for="user-phone">핸드폰번호</p>
            <v-text-field 
              id="user-phone"
              v-model="user.userPhone"
              style="padding-top:0px" 
              class="join_input join_input_phone" 
              placeholder="핸드폰번호를 숫자만 입력해주세요. " 
              single-line
            ></v-text-field>

            <v-btn class="join_id_check_btn" outlined color="#04338C" @click="send">전송</v-btn>

            <p class="mid_join_header" for="auth-number">인증번호</p>
            <v-text-field 
              id="auth-number"
              v-model="authNumber"
              style="padding-top:0px" 
              class="join_input" 
              placeholder="발송된 인증번호 6자리를 입력해주세요" 
              single-line
            ></v-text-field>

            <div class="text-center">
              <v-btn
                class="join_btn"
                rounded
                :color="classObject"
                dark
                @click="onSubmit"
              >JOIN</v-btn>
            </div>
          </form>
        </div>
      </div>
      <div class="join_rightBox">
        <img class="join_img" src="../../../public/Image/join_img.png">
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import Swal from 'sweetalert2'

export default {
  name: "Join2",
  data : () => {
    return{
      user:{
        userId: "",
        userNickname: "",
        userPhone:"",
        userPw: "",
      },
      checkPhone : false,
      authNumber:"",
      component: this,
    }
  },
  created(){
    this.user.userId = this.$route.params.userId;
    this.user.userNickname = this.$route.params.userNickname;
    this.user.userPw = this.$route.params.userPw;
  },
  computed: {
    classObject: function(){
      if(this.checkPhone && this.authNumber.length == 6)
        return "#04338C";
      return  "#6482B9";
    },
  },
  methods: {
    // 인증번호 발송
    send(){
      if(this.user.userPhone.length != 11){
        Swal.fire({
          icon: "error",
          text: "핸드폰번호를 11자리 입력해주세요(숫자만) ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      // 가입된 정보가 있다면 막기
      http
      .get(`v1/user/phone/${this.user.userPhone}`)
      .then((res) => {
        if(res.data.status === "success"){
          Swal.fire({
            icon: "error",
            html: "이미 가입된 유저입니다. <br/> 기존 유저로 로그인을 진행해주세요.",
            showConfirmButton: false,
            timer: 2000,
          });
          this.$router.push("/");
        }else{
          const phone = {
              phoneNumber: this.user.userPhone,
              valid : true
            }

          http
          .post("v1/auth", JSON.stringify(phone))
          .then((res) => {
            if(res.data.status === "success"){
              this.checkPhone = true;
              Swal.fire({
                icon: "success",
                html: "인증번호가 발송되었습니다. <br/>잠시만 기다려주세요.",
                showConfirmButton: false,
                timer: 2000,
              });
            }else{
              Swal.fire({
                icon: "error",
                text: "인증번호 발송을 실패했습니다",
                showConfirmButton: false,
                timer: 1000,
              });
            };
          })
          .catch((err) => {
              console.error(err);
          });
        }
      })
      .catch((err) => {
          console.error(err);
      });
    },
    
    // 회원가입
    onSubmit(){
      if(!this.checkPhone){
        Swal.fire({
          icon: "error",
          text: "본인인증을 진행해주세요. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      if(this.authNumber.length != 6){
        Swal.fire({
          icon: "error",
          text: "인증번호 6자리를 입력해주세요. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      // 인증번호 확인
      http
      .get(`v1/auth/${this.user.userPhone}/${this.authNumber}`)
      .then((res) => {
        if(res.data.status === "success"){
          if(res.data.data == "true"){
            // 회원가입 완료하기
            http
            .post("v1/user/join", JSON.stringify(this.user))
            .then((res) => {
              if(res.data.status === "success"){
                Swal.fire({
                  icon: "success",
                  text: "회원가입이 완료되었습니다.",
                  showConfirmButton: false,
                  timer: 1000,
                });
                this.$router.push("/");
              }else{
                Swal.fire({
                  icon: "error",
                  text: "회원가입에 실패하였습니다. 다시 진행해주세요.",
                  showConfirmButton: false,
                  timer: 1000,
                });
                this.$router.push("/join");
              };
            })
            .catch((err) => {
                console.error(err);
            });
          }else {
            Swal.fire({
              icon: "error",
              text: "인증번호가 일치하지 않습니다. ",
              showConfirmButton: false,
              timer: 1000,
            });
          }
        }else{
          Swal.fire({
            icon: "error",
            text: "인증번호 확인에 실패했습니다. ",
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

<style scoped src="../../css/Join.css">
</style>
