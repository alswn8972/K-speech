<template>
  <div class="join1">
    <div class="join_content">
      <div class="join_leftBox">
          <div class="join_input">
            <h1 class="join_header">회원가입</h1>
            <p class="text">회원정보를 입력하세요.</p>

          <form class="join_form">
            <p class="mid_join_header" for="user-id">아이디</p>
            <v-text-field 
              id="user-id" 
              v-model="id" 
              :disabled="this.iskakao"
              style="padding-top:0px" 
              class="join_input join_input_id" 
              placeholder="이메일 형식으로 로그인해주세요 " 
              single-line
              autocomplete="off"
            ></v-text-field>

            <v-btn v-if="idCheck == false" class="join_id_check_btn" outlined color="#04338C" @click="CheckId">중복확인</v-btn>
            <v-btn v-if="idCheck == true" class="join_id_check_btn" style="color:white;" depressed color="#04338C">확인완료</v-btn> 

            <p class="mid_join_header" for="user-nick">닉네임</p>
            <v-text-field 
              id="user-nick" 
              v-model="nickName" 
              :disabled="this.iskakao"
              style="padding-top:0px" 
              class="join_input join_input_id"  
              placeholder="최대 20자입니다. " 
              single-line
              autocomplete="off"
            ></v-text-field>

            <v-btn v-if="nickNameCheck == false" class="join_id_check_btn" outlined color="#04338C" @click="CheckNickName">중복확인</v-btn>
            <v-btn v-if="nickNameCheck == true" class="join_id_check_btn" style="color:white;" depressed color="#04338C">확인완료</v-btn> 

            <p class="mid_join_header" for="user-pw">비밀번호</p>
            <v-text-field 
              id="user-pw"
              v-model="password"
              style="padding-top:0px" 
              class="join_input_c" 
              placeholder="8자~11자입니다. " 
              single-line
              type="password"
              autocomplete="off"
            ></v-text-field>

            <p class="mid_join_header" for="userpw_check">비밀번호 확인</p>
            <v-text-field 
              id="userpw_check"
              v-model="passwordCheck"
              style="padding-top:0px" 
              class="join_input_c" 
              placeholder="비밀번호를 다시 입력해주세요. " 
              single-line
              type="password"
              autocomplete="off"
            ></v-text-field>

            <p class="move join_next" style="color:#04338C" @click="SignUp" >Sign Up >></p>
          </form>
        </div>
      </div>
      <div class="join_rightBox">
        <img class="join_img" src="../../../public/Image/join_img.png">
        <p>K-speech에서</p>
        <p>친구들과 함께</p>
        <p>한국어 발음 연습도하고, </p>
        <p>게임도 즐겨보세요!</p>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import Swal from 'sweetalert2'
import PV from "password-validator";

export default {
  name: "Join1",
  data: () => {
    return {
      code:"",
      id: "",
      nickName: "",
      password: "",
      passwordCheck: "",
      passwordSchema: new PV(),
      idCheck:false,
      nickNameCheck:false,
      component : this,
      iskakao:false,
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
    this.code=this.$route.query.code;
    this.getToken();
  },
  methods:{
    // 토큰 받아오기(카카오 로그인 시 회원정보가 없으면 카카오 정보로 회원가입 진행 : 카카오 이메일=아이디, 카카오닉네임=닉네임)
    getToken(){
      http.get("/api/auth/kakaologin?authorize_code="+this.code)
      .then((res)=>{
        this.$router.push({
            name:'About'
        });
        this.$store.commit('login',res.data.user);
        Swal.fire({
          icon: "success",
          text: res.data['message'],
          showConfirmButton: false,
          timer: 1000,
        });
      })
      .catch((err)=>{
        console.log(err.response);
        
        this.id=err.response.data.user.userId;
        this.nickName=err.response.data.user.userNickName;
        this.iskakao=true;

        Swal.fire({
              icon: "error",
              text: "일치하는 회원이 없습니다. 회원가입을 진행해주세요.",
              showConfirmButton: false,
              timer: 3000,
        });
      })
    },
    // 아이디 중복체크
    CheckId(){
      let exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
      if(exptext.test(this.id)==false){
        Swal.fire({
          icon: "error",
          text: "아이디가 이메일 형식이 아닙니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      http
      .get(`api/users/${this.id}`)
      .then(() => {
          this.idCheck = true;
          Swal.fire({
              icon: "success",
              text: "사용 가능한 아이디 입니다.",
              showConfirmButton: false,
              timer: 1000,
            });
      })
      .catch((err) => {
        if(err.response.status==409){
          Swal.fire({
              icon: "error",
              text: "중복된 아이디 입니다.",
              showConfirmButton: false,
              timer: 1000,
            });
        }
      });
    }, 
    CheckNickName(){
      if(this.nickName.length<=0 || this.nickName.length>20){
        Swal.fire({
          icon: "error",
          text: "닉네임 최대 20자입니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      http
      .get(`api/users/nickName/${this.nickName}`)
      .then(() => {
          this.nickNameCheck = true;
          Swal.fire({
              icon: "success",
              text: "사용 가능한 닉네임 입니다.",
              showConfirmButton: false,
              timer: 1000,
            });
      })
      .catch((err) => {
        if(err.response.status==409){
          Swal.fire({
              icon: "error",
              text: "중복된 닉네임 입니다.",
              showConfirmButton: false,
              timer: 1000,
            });
        }
      });
    }, 
    // 회원가입
    SignUp(){
      if(this.idCheck == false){
        Swal.fire({
          icon: "error",
          text: "아이디 중복여부를 확인해주세요. ",
          showConfirmButton: false,
          timer: 1000,
        });  
        return;
      }

      if(this.nickNameCheck == false){
        Swal.fire({
          icon: "error",
          text: "닉네임 중복여부를 확인해주세요. ",
          showConfirmButton: false,
          timer: 1000,
        });  
        return;
      }

      if(this.nickName.length<=0 || this.nickName.length>20){
        Swal.fire({
          icon: "error",
          text: "닉네임은 최대 20자입니다. ",
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
        userId: this.id,
        userNickName: this.nickName,
        userPass: this.password
      }

      http
            .post("api/users/", user)
            .then(() => {
                Swal.fire({
                  icon: "success",
                  text: "회원가입이 완료되었습니다.",
                  showConfirmButton: false,
                  timer: 1000,
                });
                this.$router.push("/");
            })
            .catch((err) => {
                console.error(err);
            });

    },
    // 다음페이지 넘기기
    // next(event){
    //   event.preventDefault();

    //   if(this.idCheck == false){
    //     Swal.fire({
    //       icon: "error",
    //       text: "아이디 중복여부를 확인해주세요. ",
    //       showConfirmButton: false,
    //       timer: 1000,
    //     });  
    //     return;
    //   }

    //   if(this.nickNameCheck == false){
    //     Swal.fire({
    //       icon: "error",
    //       text: "닉네임 중복여부를 확인해주세요. ",
    //       showConfirmButton: false,
    //       timer: 1000,
    //     });  
    //     return;
    //   }

    //   if(this.nickName.length<=0 || this.nickName.length>20){
    //     Swal.fire({
    //       icon: "error",
    //       text: "닉네임은 최대 20자입니다. ",
    //       showConfirmButton: false,
    //       timer: 1000,
    //     });
    //     return;
    //   }

    //   if(this.password.length<8 || this.password.length>=12){
    //     Swal.fire({
    //       icon: "error",
    //       text: "비밀번호는 8 ~ 11자입니다. ",
    //       showConfirmButton: false,
    //       timer: 1000,
    //     });
    //     return;
    //   }

    //   if(this.password != this.passwordCheck){
    //     Swal.fire({
    //       icon: "error",
    //       text: "비밀번호가 일치하지 않습니다. ",
    //       showConfirmButton: false,
    //       timer: 1000,
    //     });
    //     return;
    //   }

    //   const user = {
    //     userId: this.id,
    //     userNickname: this.nickName,
    //     userPw: this.password
    //   }
      
    //   this.$router.push({name: 'joinUserCheck', params: {...user}})
    // }
  }
};
</script>

<style scoped src="../../css/Join.css">
</style>
