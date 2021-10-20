<template>
  <div class="board__wrap">
    <Nav/>
    <stop-watch v-if="showLink3"/>
    <div class="board__link__box" v-if="showLink">
      <router-link id="information" class="board__link" :to="{name:'InfoBoard'}">정보공유</router-link>
      <span class="board__link__divider">|</span>
      <router-link id="code" class="board__link" :to="{name:'ProblemBoard'}">코드공유</router-link>
    </div>
    <div class="board__title" v-if="showLink2===1">정보공유 게시판</div>
    <div class="board__title" v-else-if="showLink2===2">코드공유 게시판</div>
    <div class="board__title" v-else-if="showLink2===3">QnA 게시판</div>
    <router-view/>
  </div>
</template>

<script>
import Nav from '../../common/Nav/Nav.vue'
import StopWatch from '../../components/Library/StopWatch.vue';
export default {
  components: { Nav,StopWatch },
  name:'Board',
  data(){
    return {
      showLink: true,
      showLink2:0,
      showLink3: false,
    }
  },
  created(){
    if(this.$store.getters.getUser === null){
      this.$router.push('/login');
    } else if(this.$store.getters.getSchoolId === null) {
      this.$router.push({
        name: 'Unity',
      })
    }
    if(this.$route.name==="Board"){
      this.$router.push({
        name:'InfoBoard'
      })
    }
    if(this.$route.name==='InfoBoard' || this.$route.name==='ProblemBoard'){
      this.showLink=true;
    } else{
      this.showLink=false;
    }
    if(this.$route.name==='InfoDetail'){
      this.showLink2=1;
    } else if(this.$route.name==='ProblemDetail' || this.$route.name==='CreateCode' || 
    this.$route.name==='UpdateCode' || this.$route.name==='CodeDetail'){
      this.showLink2=2;
    } else if(this.$route.name==='QnaBoard' || this.$route.name==='QnaDetail'){
      this.showLink2=3;
    } else{
      this.showLink2=0;
    }
    if(this.$route.name==='CreateCode' || this.$route.name==='UpdateCode'){
      this.showLink3=true
    } else{
      this.showLink3=false
    }
  },
  beforeUpdate(){
    if(this.$route.name==='InfoBoard' || this.$route.name==='ProblemBoard'){
      this.showLink=true;
    } else{
      this.showLink=false;
    }
    if(this.$route.name==='InfoDetail'){
      this.showLink2=1;
    } else if(this.$route.name==='ProblemDetail' || this.$route.name==='CreateCode' || 
    this.$route.name==='UpdateCode' || this.$route.name==='CodeDetail'){
      this.showLink2=2;
    } else if(this.$route.name==='QnaBoard' || this.$route.name==='QnaDetail'){
      this.showLink2=3;
    } else{
      this.showLink2=0;
    }
    if(this.$route.name==='CreateCode' || this.$route.name==='UpdateCode'){
      this.showLink3=true
    } else{
      this.showLink3=false
    }    
  }
}
</script>

<style lang="scss">
.board__wrap{
  position: relative;
}
.board__link__box{
  text-align: center;
  padding-top: 2rem;
}
.board__link{
  font-size: var(--font-size-22);
  font-family: "AppleSDGothicNeoEB";
  text-align: center;
  padding: 0.5em;
  color: var(--color-grey-3);
  text-decoration: none;
}
.board__link__divider{
  font-size: var(--font-size-22);
  font-family: "AppleSDGothicNeoEB";
  color: var(--color-black);
}
.board__title{
  font-size: var(--font-size-22);
  font-family: "AppleSDGothicNeoEB";
  color: var(--color-black);
  text-align: center;
  padding: 2rem 0;
}
.router-link-active{
  color: var(--color-mainBlue);
}
.scrollable-modal {
  display: flex;
  max-width: 80% !important;
  flex-direction: column;
  height: calc(100% - 50px);
}
.scrollable-modal .vm-titlebar {
  color: var(--color-grey-2);
  font-family: "AppleSDGothicNeoB";
  text-align: center;
  flex-shrink: 0;
  .vm-title{
    font-size: var(--font-size-22);
  }
}
.scrollable-modal .vm-content {
  padding: 0;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  min-height: 0; 
}
.scrollable-modal .vm-content .scrollable-content {
  position: relative;
  overflow-y: auto;
  overflow-x: hidden;
  max-height: 700px;
  flex-grow: 1;
}
.scrollable-modal .scrollable-modal-footer {
  border-top: 1px solid #e5e5e5;
  width: 100%;
  margin: auto;
  display: flex;
  justify-content: center;
}
.editor__content, .qna__question__problem, .answer__problem{
  > * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }
  .code-block {
    select {
      display: none;
    }
  }
  pre {
    background: #0D0D0D;
    color: #FFF;
    font-family: 'JetBrainsMono', monospace;
    padding: 0.75rem 1rem;
    margin: 0.2rem 0;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
    .hljs-comment,
    .hljs-quote {
      color: #616161;
    }

    .hljs-variable,
    .hljs-template-variable,
    .hljs-attribute,
    .hljs-tag,
    .hljs-name,
    .hljs-regexp,
    .hljs-link,
    .hljs-name,
    .hljs-selector-id,
    .hljs-selector-class {
      color: #F98181;
    }

    .hljs-number,
    .hljs-meta,
    .hljs-built_in,
    .hljs-builtin-name,
    .hljs-literal,
    .hljs-type,
    .hljs-params {
      color: #FBBC88;
    }

    .hljs-string,
    .hljs-symbol,
    .hljs-bullet {
      color: #B9F18D;
    }

    .hljs-title,
    .hljs-section {
      color: #FAF594;
    }

    .hljs-keyword,
    .hljs-selector-tag {
      color: #70CFF8;
    }

    .hljs-emphasis {
      font-style: italic;
    }

    .hljs-strong {
      font-weight: 700;
    }
  }

  mark {
    background-color: #FAF594;
  }

  img {
    max-width: 100%;
    height: auto;
  }

  hr {
    margin: 1rem 0;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0D0D0D, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0D0D0D, 0.1);
    margin: 2rem 0;
  }

  ul[data-type="taskList"] {
    list-style: none;
    padding: 0;

    li {
      display: flex;
      align-items: center;

      > label {
        flex: 0 0 auto;
        margin-right: 0.5rem;
      }
    }
  }
  table {
    border-collapse: collapse;
    table-layout: fixed;
    width: 100%;
    margin: 0;
    overflow: hidden;

    td,
    th {
      min-width: 1em;
      border: 2px solid #ced4da;
      padding: 3px 5px;
      vertical-align: top;
      box-sizing: border-box;
      position: relative;

      > * {
        margin-bottom: 0;
      }
    }

    th {
      font-weight: bold;
      text-align: left;
      background-color: #f1f3f5;
    }

    .selectedCell:after {
      z-index: 2;
      position: absolute;
      content: "";
      left: 0; right: 0; top: 0; bottom: 0;
      background: rgba(200, 200, 255, 0.4);
      pointer-events: none;
    }

    .column-resize-handle {
      position: absolute;
      right: -2px;
      top: 0;
      bottom: -2px;
      width: 4px;
      background-color: #adf;
      pointer-events: none;
    }
  }  
  .tableWrapper {
    overflow-x: auto;
  }

  .resize-cursor {
    cursor: ew-resize;
    cursor: col-resize;
  }  
}
@media screen and (max-height: 800px) {
  .scrollable-modal .vm-content .scrollable-content{
    max-height: 500px;
  }
}
@media screen and (max-height: 670px) {
  .scrollable-modal .vm-content .scrollable-content{
    max-height: 400px;
  }
}
@media screen and (max-height: 570px) {
  .scrollable-modal .vm-content .scrollable-content{
    max-height: 300px;
  }
}
@media screen and (max-height: 470px) {
  .scrollable-modal .vm-content .scrollable-content{
    max-height: 250px;
  }
}
</style>

<style lang="scss">
/* Basic editor styles */
.ProseMirror:focus{
  outline: none;
}
.qna__answer__wrap{
  .ProseMirror{
    height: 150px;
  }
}
.ProseMirror {
  height:360px;
  cursor:text;
  > * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0D0D0D;
    color: #FFF;
    font-family: 'JetBrainsMono', monospace;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
    .hljs-comment,
    .hljs-quote {
      color: #616161;
    }

    .hljs-variable,
    .hljs-template-variable,
    .hljs-attribute,
    .hljs-tag,
    .hljs-name,
    .hljs-regexp,
    .hljs-link,
    .hljs-name,
    .hljs-selector-id,
    .hljs-selector-class {
      color: #F98181;
    }

    .hljs-number,
    .hljs-meta,
    .hljs-built_in,
    .hljs-builtin-name,
    .hljs-literal,
    .hljs-type,
    .hljs-params {
      color: #FBBC88;
    }

    .hljs-string,
    .hljs-symbol,
    .hljs-bullet {
      color: #B9F18D;
    }

    .hljs-title,
    .hljs-section {
      color: #FAF594;
    }

    .hljs-keyword,
    .hljs-selector-tag {
      color: #70CFF8;
    }

    .hljs-emphasis {
      font-style: italic;
    }

    .hljs-strong {
      font-weight: 700;
    }
  }

  mark {
    background-color: #FAF594;
  }

  img {
    max-width: 100%;
    height: auto;
  }

  hr {
    margin: 1rem 0;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0D0D0D, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0D0D0D, 0.1);
    margin: 2rem 0;
  }

  ul[data-type="taskList"] {
    list-style: none;
    padding: 0;

    li {
      display: flex;
      align-items: center;

      > label {
        flex: 0 0 auto;
        margin-right: 0.5rem;
      }
    }
  }
}
.ProseMirror {
  table {
    border-collapse: collapse;
    table-layout: fixed;
    width: 100%;
    margin: 0;
    overflow: hidden;

    td,
    th {
      min-width: 1em;
      border: 2px solid #ced4da;
      padding: 3px 5px;
      vertical-align: top;
      box-sizing: border-box;
      position: relative;

      > * {
        margin-bottom: 0;
      }
    }

    th {
      font-weight: bold;
      text-align: left;
      background-color: #f1f3f5;
    }

    .selectedCell:after {
      z-index: 2;
      position: absolute;
      content: "";
      left: 0; right: 0; top: 0; bottom: 0;
      background: rgba(200, 200, 255, 0.4);
      pointer-events: none;
    }

    .column-resize-handle {
      position: absolute;
      right: -2px;
      top: 0;
      bottom: -2px;
      width: 4px;
      background-color: #adf;
      pointer-events: none;
    }
  }
}
.qna__answer__wrap{
  .editor{
    border: 1px solid var(--color-grey-6) !important;
    margin: 0 !important;
    &__header{
      border-top: none !important;
      border-bottom: 1px solid var(--color-grey-6) !important;
    }
  }
}
.tableWrapper {
  overflow-x: auto;
}

.resize-cursor {
  cursor: ew-resize;
  cursor: col-resize;
}
</style>