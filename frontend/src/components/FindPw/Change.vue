<template>
  <div class="confirm">
      <div class="find_content">
      <div class="find_leftBox">
         <div class="find_input">
          <h1 class="find_header">비밀번호 찾기</h1>
          <p class="text">변경할 비밀번호를 설정해주세요.</p>

          <p class="mid_find_header" for="user-pw">비밀번호</p>
          <v-text-field 
            id="user-pw"
            v-model="password"
            style="padding-top:0px" 
            class="find_input_c" 
            placeholder="8자~11자입니다. " 
            single-line
          ></v-text-field>

          <p class="mid_find_header" for="userpw_check">비밀번호 확인</p>
          <v-text-field 
            id="userpw_check"
            v-model="passwordCheck"
            style="padding-top:0px" 
            class="find_input_c" 
            placeholder="비밀번호를 다시 입력해주세요. " 
            single-line
          ></v-text-field>
          
          <div class="text-center">
            <v-btn
              class="find_btn"
              rounded
              :color="classObject"
              dark
              @click="change"
            >변경하기</v-btn>
          </div>
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
import PV from "password-validator";

export default {
  name:"ChangePw",
  data: () => {
    return{
      password: "",
      passwordCheck: "",
      passwordSchema: new PV(),
    }
  },
  created(){
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
  },
  computed: {
    classObject: function(){
      if(this.password == this.passwordCheck && this.password.length>=8 && this.password.length<12)
        return "#04338C";
      return  "#6482B9";
    },
  },
  methods:{
    change(){
      if(this.password.length<8 || this.password.length>=12){
        Swal.fire({
          icon: "error",
          text: "비밀번호는 8 ~ 11자입니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      if(this.password != this.passwordCheck){
        Swal.fire({
          icon: "error",
          text: "비밀번호가 일치하지 않습니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

    
      const user = {
        userId: this.$route.params.id,
        userPw : this.password
      }
      http
      .post("v1/user/pwd" , JSON.stringify(user))
      .then((res) => {
         if(res.data.status === "success"){
            Swal.fire({
              icon: "success",
              text: "비밀번호가 변경되었습니다.",
              showConfirmButton: false,
              timer: 1000,
            });
         }else{
           Swal.fire({
              icon: "error",
              text: "비밀번호 변경에 실패하였습니다.",
              showConfirmButton: false,
              timer: 1000,
            });
         }
      })
      .catch((err) => {
        console.error(err);
      });
      this.$router.push("/");
    }
    
  }

}
</script>

<style scoped src="../../css/FindPw.css">
</style>