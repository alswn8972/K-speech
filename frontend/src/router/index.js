import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../views/Login/Login.vue";
import Character from "../components/Login/Character.vue";
import Camera from "../components/Login/Camere.vue";
import Join from "../views/Join/Join.vue";
import FindPw from "../views/FindPw/FindPw.vue";
import MyPage from "../views/MyPage/MyPage.vue";
import ChangePw from "../components/FindPw/Change.vue";
import Confirm from "../components/FindPw/Confirm.vue";
import Computer from "../views/Computer/Computer.vue";
import Board from "../views/Board/Board.vue";
import InfoBoard from "../components/Board/InfoBoard.vue";
import InfoDetail from "../components/Board/InfoDetail.vue";
import ProblemBoard from "../components/Board/ProblemBoard.vue";
import ProblemDetail from "../components/Board/ProblemDetail.vue";
import CreateCode from '../components/Board/CreateCode.vue';
import UpdateCode from '../components/Board/UpdateCode.vue';
import CodeDetail from '../components/Board/CodeDetail.vue';
import QnaBoard from '../components/Board/QnaBoard.vue';
import QnaDetail from '../components/Board/QnaDetail.vue';
import Cook from "../views/Cook/Cook.vue";
import Library from "../views/Library/Library.vue";
import Calendar from "../components/Library/Calendar.vue";
import Youtube from "../components/Library/Youtube.vue";
import Rest from "../views/Rest/Rest.vue";
import Play from "../views/Play/Play.vue";
import Office from "../views/Office/Office.vue";
import joinUserInfo from "../components/Join/Join_1.vue";
import joinUserCheck from "../components/Join/Join_2.vue";
import CamSetting from "../views/MyPage/CamSetting.vue";
import Award from "../views/Award/Award.vue";
import Unity from "../views/Unity/Unity.vue";
import GuestBook from "../views/GuestBook/GuestBook.vue";
import About from '../views/About/About.vue';

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
    path: "/findpw",
    name: "FindPw",
    component: FindPw,
    children: [
      {
        path: "",
        name: "Confirm",
        component: Confirm,
      },
      {
        path: "changePw",
        name: "ChangePw",
        component: ChangePw,
      },
    ],
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
    path: "/computer",
    name: "Computer",
    component: Computer,
  },
  {
    path: "/board",
    name: "Board",
    component: Board,
    children: [
      {
        path: "information",
        name: "InfoBoard",
        component: InfoBoard,
      },
      {
        path: "information/:id",
        name: "InfoDetail",
        component: InfoDetail,
      },
      {
        path: "problem",
        name: "ProblemBoard",
        component: ProblemBoard,
      },
      {
        path: "problem/:id",
        name: "ProblemDetail",
        component: ProblemDetail,
      },
      {
        path: "problem/:id/code",
        name: "CreateCode",
        component: CreateCode,
      },
      {
        path: "problem/:id/code/:codeId/:userId",
        name: "UpdateCode",
        component: UpdateCode,
      },
      {
        path: "problem/:id/code/:codeId",
        name: "CodeDetail",
        component: CodeDetail,
      },
      {
        path: "qna",
        name: "QnaBoard",
        component: QnaBoard,
      },
      {
        path: "qna/:id",
        name: "QnaDetail",
        component: QnaDetail,
      },
    ],
  },
  {
    path: "/cook",
    name: "Cook",
    component: Cook,
  },
  {
    path: "/library",
    name: "Library",
    component: Library,
    children: [
      {
        path: "youtube",
        name: "Youtube",
        component: Youtube,
      },
      {
        path: "calendar",
        name: "Calendar",
        component: Calendar,
      },
    ],
  },
  {
    path: "/rest",
    name: "Rest",
    component: Rest,
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
  {
    path : "/gb",
    name : "GuestBook",
    component : GuestBook,
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
