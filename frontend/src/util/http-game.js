import axios from "axios";

// axios 객체 생성
export default axios.create({

  //baseURL: "https://localhost:8081",
  baseURL: "https://k5d104.p.ssafy.io:8081",

  headers: {
    "Content-type": "application/json",
  },
});
