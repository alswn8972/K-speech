
<template>
  <!--배경-->
  <div class="mypage">
    <!--가운데 내용-->
    <div class="mypage_content">
      <div class="content_top">
        <div class="top_leftBox">
          <p class="left_h">마이페이지</p>
          <p class="left_p">정보를 변경해주세요</p>
          <p class="left_name">{{user.userId}}님</p>
          <div class="left_img">
            <img
              class="user_img"
              v-if="imageUrl==''||imageUrl==null"
              src="../../../public/Image/user_profile.png"
            />
            <img v-else :src="imageUrl"/>
          </div>
            <div class="input_file">
              <input type="file" id="file" ref="file" accept=".jpg, .png, .jpeg" v-on:change="handleFileUpload()" @change="onChangeImages"/>
            </div>
            <ul class="left_ul">
              <li class="delete" @click="delPicture()">삭제</li>
          </ul>
          <p class="mid_header">닉네임</p>
          <v-text-field class="input" v-model="user.userNickname" single-line></v-text-field>
        </div>
        <div class="top_rightBox">
          <router-link class="goCamera" to="/camSetting">
            카메라 설정 변경 >>
          </router-link>

          <p class="mid_header">비밀번호</p>
          <v-text-field class="input" v-model="user.userPw" type="password" single-line></v-text-field>

          <p class="mid_header">비밀번호 확인</p>
          <v-text-field class="input" v-model="passwordCheck" type="password" single-line></v-text-field>

          <p class="mid_header">핸드폰번호</p>
          <v-row>
            <v-col cols="12" sm="8">
              <v-text-field
              v-model="user.userPhone"
                class="input input_certify"
                single-line
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4">
              <v-btn class="certifyBtn" color="#04338C" @click="sendSns()" dark>전송</v-btn>
            </v-col>
          </v-row>

          <p class="mid_header">인증번호</p>
          <v-row>
            <v-col cols="12" sm="8">
              <v-text-field
                class="input input_certify"
                placeholder="인증번호 6자리 입력"
                v-model="authNumber"
                single-line
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="4">
              <v-btn class="certifyBtn" color="#04338C" @click="certify()" dark>본인인증</v-btn>
            </v-col>
          </v-row>
        </div>
      </div>
      <!--완료-->
      <div class="content_btm">
        <v-btn class="modify_btn" @click="update()" rounded color="#04338C" dark>{{isChanged ? '변경하기' : '게임으로'}}</v-btn>
        <a href="#" class="goDelete" @click="deleteUser()">탈퇴하기</a>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import Swal from 'sweetalert2'

export default {
  name: "MyPage",
  data() {
    return {
      newAgency: ["SKT", "KT", "LG U+"],
      user: {},
      passwordCheck:'',
      originalPhone:'', //수정전 원래 폰번호
      checkPhone:false, //폰중복검사
      checkAuth: false, //인증번호 검사
      authNumber:'', //인증번호
      imageUrl:'', //이미지 미리보기
      file:'',
    };
  },
  created(){
    //vuex에서 가져오는 부분 추가예정
    if(this.$store.getters.getUser === null){
      this.$router.push('/login');
    }
    this.user = this.$store.getters.getUser;
    this.originalPhone = this.user.userPhone;
    this.passwordCheck=this.user.userPw;
    this.imageUrl=this.user.userImg;
  },
  computed:{
    isChanged(){
      const origin = this.$store.state.user;
      let flag=false;
      Object.keys(origin).forEach(key=>{
        if(this.user[key]!==origin[key])
          flag=true;
      })
      return flag
    }
  },
  methods:{
    update:function(){
      this.user.userImg = this.imageUrl;
      if(this.user.userNickname.length<=0 || this.user.userNickname.length>6){
        Swal.fire({
          icon: "error",
          text: "닉네임은 최대 6자입니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      if(this.user.userPw.length<8 || this.user.userPw.length>=12){
        Swal.fire({
          icon: "error",
          text: "비밀번호는 8 ~ 11자입니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      if(this.user.userPw != this.passwordCheck){
        Swal.fire({
          icon: "error",
          text: "비밀번호가 일치하지 않습니다. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

      //핸드폰 수정 안했으면 바로 true
      if(this.user.userPhone == this.originalPhone){
        this.checkPhone = true;
        this.checkAuth = true;
      }

      
      if(!this.checkPhone){
        Swal.fire({
          icon: "error",
          text: "본인인증을 진행해주세요. ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }


      if(!this.checkAuth){
          Swal.fire({
            icon: "error",
            text: "인증번호가 올바르지 않습니다. ",
            showConfirmButton: false,
            timer: 1000,
          });
          return;
      }
      if(!this.isChanged){
        this.$router.push({
          name: 'Unity'
        })
        return
      }
      const formData = new FormData();
      formData.append("file", this.file);
      formData.append("request", new Blob([JSON.stringify(this.user)], { type: "application/json" }));

      http
      .put(`v1/user/${this.user.userId}`, formData, {
        headers:{
          "Content-Type":`multipart/form-data`,
        }
      })
      .then((res) => {
        if(res.data.status === "success"){
          Swal.fire({
            icon: "success",
            html: "회원수정이 완료되었습니다",
            showConfirmButton: false,
            timer: 2000,
          });

          this.user.userImg = res.data.data;
          this.imageUrl = res.data.data;
          this.$store.commit('setUser',this.user);
          this.$router.push({
            name: 'Unity'
          })
        } else{
          Swal.fire({
            icon: "error",
            text: "회원정보 수정에 실패하였습니다.",
            showConfirmButton: false,
            timer: 1000,
          });
          return;
        }
      })
      .catch((err) => {
        console.error(err);
      });

    },
    deleteUser:function(){
      Swal.fire({
		  title: '탈퇴하시겠습니까?',
		  text: "탈퇴하시면 다시 복구시킬 수 없습니다.",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: '삭제',
		  cancelButtonText: '취소'
		}).then((result) => {
		  if (result.value) {
        http
        .delete(`v1/user/${this.user.userId}`)
        .then(() => {
          this.$router.push("/");
        })
        .catch((err) => {
          console.error(err);
        });
		  }
		})
    },
    sendSns:function(){ //핸드폰 중복확인 후 인증번호 발송
      if(this.user.userPhone.length != 11){
        Swal.fire({
          icon: "error",
          text: "핸드폰번호를 11자리 입력해주세요(숫자만) ",
          showConfirmButton: false,
          timer: 1000,
        });
        return;
      }

     
      //핸드폰번호 바꿀때만 인증번호 전송
      if(this.user.userPhone != this.originalPhone){
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
              this.checkPhone = false;
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
          text: "현재 등록된 휴대전화 정보와 동일합니다.",
          showConfirmButton: false,
          timer: 1000,
        });
      }
    },
    certify:function(){ //인증번호 확인
      http
      .get(`v1/auth/${this.user.userPhone}/${this.authNumber}`)
      .then((res) => {
        if(res.data.status === "success"){
          if(res.data.data == "true"){
            this.checkAuth = true;
            Swal.fire({
                icon: "success",
                html: "본인인증이 완료되었습니다.",
                showConfirmButton: false,
                timer: 1000,
              });
          }else {
            this.checkAuth = false;
            Swal.fire({
              icon: "error",
              text: "인증번호가 일치하지 않습니다. ",
              showConfirmButton: false,
              timer: 1000,
            });
          }
        }else{
          checkAuth = false;
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
    },
     handleFileUpload(){
        this.file = this.$refs.file.files[0];
    },
    onChangeImages(e) {
        const file = e.target.files[0];
        this.imageUrl = URL.createObjectURL(file);
    },
    delPicture(){
      http
        .post(`v1/user/defaultImg/${this.user.userId}`)
        .then((res) => {
          if (res.data.status === "success") {
             Swal.fire({
                icon: "success",
                html: "프로필 사진이 삭제되었습니다.",
                showConfirmButton: false,
                timer: 2000,
              });
            this.user.userImg = ""; 
            this.imageUrl = ""; //미리보기 삭제 
            //this.$store.commit('setUserInfo',this.user);
          }else{
             Swal.fire({
            icon: "error",
            text: "프로필 사진 삭제에 실패했습니다. ",
            showConfirmButton: false,
            timer: 1000,
          });
          }
        })
        .catch((err) => {
          console.error(err);
        });
    }
  },
  components: {},
};
</script>


<style scoped src="../../css/MyPage.css">

</style>

