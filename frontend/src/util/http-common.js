import axios from "axios";

// axios 객체 생성
export default axios.create({
  //개발
  //baseURL: "https://localhost:8080",
  //배포
  baseURL: "https://k5d104.p.ssafy.io:8080",

  headers: {
    "Content-type": "application/json",
  },
});
