import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../views/Login/Login.vue";
import Camera from "../components/Login/Camere.vue";
import Join from "../views/Join/Join.vue";
import MyPage from "../views/MyPage/MyPage.vue";
import joinUserInfo from "../components/Join/Join_1.vue";
import joinUserCheck from "../components/Join/Join_2.vue";
import Award from "../views/Award/Award.vue";
import About from '../views/About/About.vue';
import Practice from '../views/Practice/Practice.vue';
import PracticeScore from "../components/Practice/Score.vue";
import AcidRain from "../components/AcidRain/AcidRainGame.vue";
import SentencePractice from "../components/Practice/SentencePractice.vue";
import Unity from "../views/Unity/Unity.vue";
import CamSetting from "../views/MyPage/CamSetting.vue";
import meeting from  "../views/meeting/meeting.vue"
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "About",
    component: About,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    children: [
      {
        path: "camera",
        name: "Camera",
        component: Camera,
      },
    ],
  },
  {
    path: "/join",
    name: "Join",
    component: Join,
    children: [
      {
        path: "",
        name: "joinUserInfo",
        component: joinUserInfo
      },
      {
        path: "check",
        name: "joinUserCheck",
        component: joinUserCheck
      }
    ]
  },
  {
    path: "/practice",
    name: "practice",
    component: Practice
  },
  {
    path: "/word",
    name: "word",
    component: SentencePractice
  },
  {
    path: "/sentence",
    name: "sentence",
    component: SentencePractice
  },
  {
    path: "/acidRain",
    name: "acidRain",
    component: AcidRain
  },
  {
    path: "/score",
    name: "score",
    component: PracticeScore
  },
  {
    path: "/mypage",
    name: "MyPage",
    component: MyPage,
  },
  {
    path: "/camSetting",
    name: "CamSetting",
    component: CamSetting,
  },
  {
    path: "/meeting",
    name: "Meeting",
    component: meeting,
  },
  {
    path: "/award",
    name: "Award",
    component: Award
  },
  {
    path : "/unityGame",
    name : "Unity",
    component : Unity,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
