import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueModal from '@kouts/vue-modal';
import '@kouts/vue-modal/dist/vue-modal.css';
import VueMoment from 'vue-moment'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import MMonacoEditor from 'vue-m-monaco-editor';
import VueLoading from 'vue-loading-template';
Vue.config.productionTip = false;
Vue.use(VueMoment);
Vue.use(MMonacoEditor);
Vue.use(VueLoading);
Vue.use(Vuetify);

var VueCookie = require('vue-cookie');
//쿠키를 사용한다.
Vue.use(VueCookie);

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faTrashAlt, faCalendarAlt, faCommentDots, faWindowMinimize, faWindowMaximize} from '@fortawesome/free-regular-svg-icons'
import { faPencilAlt, faChevronLeft,faChevronRight, faLink,faBold,faItalic
  ,faStrikethrough,faUnderline,faCode,faFileCode,faParagraph,faListOl,faListUl,
  faQuoteLeft,faUndo,faRedo,faImage,faChevronDown
}from '@fortawesome/free-solid-svg-icons';
library.add(faPencilAlt,faChevronLeft,faChevronRight,faLink,faBold,faItalic,
  faStrikethrough,faUnderline,faCode,faFileCode,faParagraph,faListOl,
  faListUl,faQuoteLeft,faWindowMinimize,faWindowMaximize,faUndo,faRedo,faImage,
  faTrashAlt,faCalendarAlt,faCommentDots,faChevronDown);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('Modal',VueModal);
new Vue({
  router,
  store,
  vuetify : new Vuetify(),
  render: (h) => h(App),
}).$mount("#app");
