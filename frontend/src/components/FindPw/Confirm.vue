<template>
  <div class="confirm">
      <div class="find_content">
      <div class="find_leftBox">
         <div class="find_input">
          <h1 class="find_header">비밀번호 찾기</h1>
          <p class="text">본인인증을 해주세요.</p>

          <p  class="mid_find_header" for="user-id">아이디</p>
          <v-text-field 
            id="user-id" 
            v-model="id" 
            style="padding-top:0px" 
            class="find_input" 
            placeholder="최대 10자입니다. " 
            single-line
          ></v-text-field>

          <p class="mid_find_header" for="user-phone">핸드폰번호</p>
            <v-text-field 
              id="user-phone"
              v-model="userPhone"
              style="padding-top:0px" 
              class="find_input find_input_phone" 
              placeholder="핸드폰번호를 숫자만 입력해주세요. " 
              single-line
            ></v-text-field>

            <v-btn class="find_id_check_btn" outlined color="#04338C" @click="send">전송</v-btn>

            <p class="mid_find_header" for="auth-number">인증번호</p>
            <v-text-field 
              id="auth-number"
              v-model="authNumber"
              style="padding-top:0px" 
              class="find_input" 
              placeholder="발송된 인증번호 6자리를 입력해주세요" 
              single-line
            ></v-text-field>

            <p class="move find_next" style="color:#04338C" @click="next" >NEXT >></p>
          
        </div>
      </div>
      <div class="find_rightBox">
        <img class="find_img" src="../../../public/Image/pw_img.png">
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import Swal from 'sweetalert2'

export default {
  name: "Confirm",
  data: () => {
    return {
      id: "",
      userPhone:"",
      authNumber:"",
      checkPhone : false,
      component : this
    }
  },
  methods:{
    // 인증번호 발송
    send(){
      if(this.id.length<=0 || this.id.length>10){
        Swal.fire({
          icon: "error",
          text: "아이디는 최대 10자입니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      if(this.userPhone.length != 11){
        Swal.fire({
          icon: "error",
          text: "핸드폰번호를 11자리 입력해주세요(숫자만) ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      // 가입된 정보 찾기
      http
      .get(`v1/user/${this.id}/${this.userPhone}`)
      .then((res) => {
        if(res.data.status === "success"){ // 가입된 유저가 있으면
          const phone = { // 인증번호 전송
              phoneNumber: this.userPhone,
              valid : true
            }
          // 인증번호 전송
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

        }else{
          Swal.fire({
            icon: "error",
            text: "가입된 정보가 없습니다.",
            showConfirmButton: false,
            timer: 1000,
          });
        }
      })
      .catch((err) => {
          console.error(err);
      });

    },
    next(){
       // 인증번호 확인
      http
      .get(`v1/auth/${this.userPhone}/${this.authNumber}`)
      .then((res) => {
        if(res.data.status === "success"){
          if(res.data.data == "true"){
            this.$router.push({name: 'ChangePw', params: {id: this.id}});
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
}
</script>

<style scoped src="../../css/FindPw.css">
</style>