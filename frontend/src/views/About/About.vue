<template>
  <div id="home">
    <div class="sections-menu">
      <span
        class="menu-point"
        v-bind:class="{active: activeSection == index}"
        v-on:click="scrollToSection(index)"
        v-for="(offset, index) in offsets"
        v-bind:key="index">
      </span>
    </div>
    <section class="fullpage home1">
      <Nav/>
    </section>
    <section class="fullpage home2">
      <div class="home2__box">
        <div class="home2__title">ABOUT US</div>
        <div class="home2__content">
          <p>힘든 코로나 상황을 겪고 있는 Z세대를 위한 <span class="blue">"학습 메타버스 플랫폼"</span>입니다.</p>
          <p>단순한 공부 플랫폼을 넘어 친구들과 같이 참여하고 즐길 수 있는 환경을 제공할 뿐만 아니라,</p>
          <p>실제 학교 같은 컨셉으로 가벼운 연대의 네트워크를 통해 사용자 간의 유대를 형성하고</p>
          <p>그에 맞는 다양한 기능들을 제공하여 코로나 상황을 보다 슬기롭게 헤쳐나갈 수 있습니다.</p>
        </div>
      </div>
    </section>
    <section class="fullpage home3">
      <div class="home3__box">
        <div class="home3__title">01. 가상환경 제공</div>
        <div class="home3__content">
          <div class="home3__content__left"></div>
          <div class="home3__content__right">
            <p>메타버스(Metaverse)란</p>
            <p>가상·초월(meta)과 세계·우주(universe)의 합성어로</p>
            <p>3차원 가상 세계를 뜻합니다.</p>
            <p><span class="blue">백투스쿨</span>은 Unity를 통해</p>
            <p>가상환경으로 서비스를 제공합니다.</p>
          </div>
        </div>
      </div>
    </section>
    <section class="fullpage home4">
      <div class="home4__box">
        <div class="home4__title">
          02. 컴퓨터 교실
          <div class="home4__subtitle">코딩에 특화된 교실에서 수업도 하고 다양한 자료를 공유하며 함께 학습해봅시다.</div>
        </div>
        <div class="home4__content">
          <div class="home4__card">
            <div class="home4__card__img"></div>
            <div class="home4__card__title blue">웹캡</div>
            <div class="home4__card__content">웹캠 서비스를 통해 선생님과 수업을 듣고 친구들과 공유하는 시간을 가져봅시다.</div>
          </div>
          <div class="home4__card">
            <div class="home4__card__img2"></div>
            <div class="home4__card__title blue">정보공유 게시판</div>
            <div class="home4__card__content">친구들에게 공유하고 싶은 다양한 정보를 나눌 수 있습니다.</div>
          </div>
          <div class="home4__card">
            <div class="home4__card__img3"></div>
            <div class="home4__card__title blue">QNA 게시판</div>
            <div class="home4__card__content">궁금한 것이 생겼다면 질문을 올리고, 친구의 질문에 아는 것이 있다면 답변해주세요.</div>
          </div>
          <div class="home4__card">
            <div class="home4__card__img4"></div>
            <div class="home4__card__title blue">코드공유 게시판</div>
            <div class="home4__card__content">문제를 올리고 친구들과 나의 코드를 공유하며, 학습해보세요.</div>
          </div>
        </div>
      </div>
    </section>
    <section class="fullpage home5">
      <div class="home5__box">
        <div class="home5__title">
          03. 휴게실
          <div class="home5__subtitle">친구들과 식사와 휴식을 즐겨보세요.</div>
        </div>
        <div class="home5__content">
          <img src="../../assets/main_friends.png" alt="">
          <div class="home5__card">
            <div class="home5__card__img"></div>
            <div class="home5__card__title blue">웹캡</div>
          </div>
          <div class="home5__content__plus"></div>
          <div class="home5__card">
            <div class="home5__card__img2"></div>
            <div class="home5__card__img3"></div>
            <div class="home5__card__title blue">인기 영상</div>
          </div>
        </div>
      </div>
    </section>
    <section class="fullpage home6">
      <div class="home6__box">
        <div class="home6__title">
          04. 도서관
          <div class="home6__subtitle">개인학습 서비스가 제공됩니다.</div>
        </div>
        <div class="home6__content">
          <div class="home6__card">
            <div class="home6__card__img"></div>
            <div class="home6__card__title blue">플래너</div>
          </div>
          <img src="../../assets/main_students.png" alt="">
          <div class="home6__content__plus"></div>
          <div class="home6__card">
            <div class="home6__card__img2"></div>
            <div class="home6__card__title blue">공부 자극영상</div>
          </div>
        </div>        
      </div>
    </section>
  </div>
</template>

<script>
import Nav from '../../common/Nav/Nav.vue'

export default {
  components: {
    Nav 
  },
  data() {
    return{
      inMove: false,
      activeSection: 0,
      offsets: [],
      touchStartY: 0
    }
  },
  methods: {
    calculateSectionOffsets() {
      let sections = document.getElementsByTagName('section');
      let length = sections.length;
      console.log(length);
      console.log(this.offsets);
      for(let i = 0; i < length; i++) {
        let sectionOffset = sections[i].offsetTop;
        this.offsets.push(sectionOffset);
      }
      console.log(this.offsets);
    },
    handleMouseWheel: function(e) {
      
      if (e.wheelDelta < 30 && !this.inMove) {
        this.moveUp();
      } else if (e.wheelDelta > 30 && !this.inMove) {
        this.moveDown();
      }
        
      e.preventDefault();
      return false;
    },
    handleMouseWheelDOM: function(e) {
      
      if (e.detail > 0 && !this.inMove) {
        this.moveUp();
      } else if (e.detail < 0 && !this.inMove) {
        this.moveDown();
      }
      
      return false;
    },
    moveDown() {
      this.inMove = true;
      this.activeSection--;
        
      if(this.activeSection < 0) this.activeSection = this.offsets.length - 1;
        
      this.scrollToSection(this.activeSection, true);
    },
    moveUp() {
      this.inMove = true;
      this.activeSection++;
        
      if(this.activeSection > this.offsets.length - 1) this.activeSection = 0;
        
      this.scrollToSection(this.activeSection, true);
    },
    scrollToSection(id, force = false) {
      if(this.inMove && !force) return false;
      
      this.activeSection = id;
      this.inMove = true;
      
      document.getElementsByTagName('section')[id].scrollIntoView({behavior: 'smooth'});
      
      setTimeout(() => {
        this.inMove = false;
      }, 400);
      
    },
    touchStart(e) {
      e.preventDefault();
      
      this.touchStartY = e.touches[0].clientY;
    },
    touchMove(e) {
      if(this.inMove) return false;
      e.preventDefault();
      
      const currentY = e.touches[0].clientY;
      
      if(this.touchStartY < currentY) {
        this.moveDown();
      } else {
        this.moveUp();
      }
      
      this.touchStartY = 0;
      return false;
    }
  },
  mounted() {
    this.calculateSectionOffsets();
    
    window.addEventListener('DOMMouseScroll', this.handleMouseWheelDOM);
    window.addEventListener('mousewheel', this.handleMouseWheel, { passive: false });
    
    window.addEventListener('touchstart', this.touchStart, { passive: false });
    window.addEventListener('touchmove', this.touchMove, { passive: false });
  },
  destroyed() {
    window.removeEventListener('mousewheel', this.handleMouseWheel, { passive: false });
    window.removeEventListener('DOMMouseScroll', this.handleMouseWheelDOM);
    
    window.removeEventListener('touchstart', this.touchStart);
    window.removeEventListener('touchmove', this.touchMove);
  }
}
</script>

<style lang="scss">
.fullpage {
  height: 100vh;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.home1 {
  background: center no-repeat url("../../assets/main_page.png");
  background-size:100vw 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  #nav{
    border: none;
  }
}
.blue{
  color: var(--color-mainBlue);
}
.home2 {
  background: center no-repeat url("../../assets/main_2bg.png");
  background-size:100vw 100vh;
  font-family: "AppleSDGothicNeoM";
  font-weight: bold;
  &__box{
    width: 720px;
    margin-top: 4rem;
    margin-left: 20rem;
  }
  &__title{
    font-size: var(--font-size-60);
  }
  p{
    margin: 0.7rem 0;
  }
  &__content{
    font-size: var(--font-size-20);
  }
}
.home3{
  background: center no-repeat url("../../assets/main_3bg.png");
  background-size:100vw 100vh;
  font-family: "AppleSDGothicNeoM";
  font-weight: bold;
  &__box{
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
  }
  &__title{
    font-size: var(--font-size-35);
  }
  &__content{
    display: flex;
    position: relative;
    &__left{
      width: 700px;
      height: 600px;
      background: center no-repeat url("../../assets/main_unity.gif");
      background-size: 100% auto;
      margin-right: 11rem;
    }
    &__right{
      font-size: var(--font-size-24);
      display: flex;
      flex-direction: column;
      justify-content: center;
      p{
        margin: 0.7rem 0;
        text-align: end;
      }    
    }
  }
}
.home4{
  background-color: #D4E2FC;
  background-size:100vw 100vh;
  font-family: "AppleSDGothicNeoM";
  font-weight: bold;
  &__box{
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
  }  
  &__title{
    text-align: center;
    font-size: var(--font-size-35);
  }
  &__subtitle{
    padding: 0.5rem 0;
    text-align: center;
    color: #585858;
    font-size: var(--font-size-18);
  }
  &__content{
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  &__card{
    width: 330px;
    height: 450px;
    padding: 1rem;
    margin: 0 1rem;
    border-radius: 20px;
    box-shadow: 0px 4px 8px #0000001A;
    background-color: var(--color-white);
    &__img{
      border-radius: 20px;
      height: 310px;
      background: center no-repeat url("../../assets/webcam.png");
      background-size: 100% 100%;
    }
    &__img2{
      border-radius: 20px;
      height: 310px;
      background: center no-repeat url("../../assets/share_info.png");
      background-size: 100% 100%;
    }
    &__img3{
      border-radius: 20px;
      height: 310px;
      background: center no-repeat url("../../assets/QNA.png");
      background-size: 100% 100%;
    }
    &__img4{
      border-radius: 20px;
      height: 310px;
      background: center no-repeat url("../../assets/code.png");
      background-size: 100% 100%;
    }
    &__title{
      padding: 1em 0;
      font-size: var(--font-size-20);
    }
    &__content{
      font-size: var(--font-size-14);
      color: #585858;
    }
  }
  &__card:hover{
    transform: scale(1.1);
  }
}
.home5 {
  background-color: #90B6FF;
  background-size:100vw 100vh;
  font-family: "AppleSDGothicNeoM";
  font-weight: bold;
  &__box{
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
  }
  &__title{
    text-align: center;
    font-size: var(--font-size-35);
  }
  &__subtitle{
    padding: 0.5rem 0;
    text-align: center;
    color: #585858;
    font-size: var(--font-size-18);
  }
  &__content{
    display: flex;
    justify-content: center;
    align-items: center;    
    width: 80%;
    height: 450px;
    background-color: var(--color-white);
    position: relative;
    border-radius: 20px;
    img{
      position: absolute;
      width: 350px;
      top: -80px;
    }
    &__plus{
      width: 100px;
      height: 100px;
      background: center no-repeat url("../../assets/plus.png");
      background-size: 100% 100%;
    }
  }
  &__card{
    width: 330px;
    margin: 2rem;
    &__img{
      height: 330px;
      background: center no-repeat url("../../assets/main_webcam.png");
      background-size: 100% 100%;
    }
    &__img2{
      height: 35px;
      background: center / contain no-repeat url("../../assets/youtube.png");
    }
    &__img3{
      margin: 10px 0;
      height: 275px;
      background: center no-repeat url("../../assets/youtube_start.png");
      background-size: 100% 100%;
    }
    &__title{
      padding: 1rem 0;
      text-align: center;
      font-size: var(--font-size-20);
    }
  }
}
.home6 {
  background-color: #FCEF8E;
  background-size:100vw 100vh;
  font-family: "AppleSDGothicNeoM";
  font-weight: bold;
  &__box{
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
  }
  &__title{
    text-align: center;
    font-size: var(--font-size-35);
  }
  &__subtitle{
    padding: 0.5rem 0;
    text-align: center;
    color: #585858;
    font-size: var(--font-size-18);
  }
  &__content{
    display: flex;
    justify-content: center;
    align-items: center;    
    width: 80%;
    background-color: transparent;
    position: relative;
    img{
      position: absolute;
      width: 300px;
      bottom: -80px;
    }
  }
  &__card{
    width: 400px;
    margin: 0 8rem;
    &__img{
      border-radius: 50%;
      border: 4px solid var(--color-white);
      width: 396px;
      height: 396px;
      background: center no-repeat url("../../assets/calender_check.png");
      background-size: 100% 100%;
    }
    &__img2{
      border-radius: 50%;
      border: 4px solid var(--color-white);      
      width: 396px;
      height: 396px;
      background: center / contain no-repeat url("../../assets/study.png");
      background-size: 100% 100%;
    }
    &__title{
      padding: 1rem 0;
      text-align: center;
      font-size: var(--font-size-20);
    }
  }
}
.sections-menu {
  position: fixed;
  right: 1rem;
  top: 50%;
  transform: translateY(-50%);
}

.sections-menu .menu-point {
  width: 10px;
  height: 10px;
  background-color: #FFF;
  display: block;
  margin: 1rem 0;
  opacity: .6;
  transition: .4s ease all;
  cursor: pointer;
}

.sections-menu .menu-point.active {
  opacity: 1;
  transform: scale(1.5);
}
@media screen and(max-width: 1750px) {
  .home3{
    &__content{
      &__left{
        width: 500px;
        height: 600px;
        margin-right: 8rem;
      }
      &__right{
        font-size: var(--font-size-22); 
      }
    }
  }
}
@media screen and (max-width: 1500px) {
  .home3{
    &__content{
      display: flex;
      position: relative;
      &__left{
        margin-right: 5rem;
      }
    }
  }
}
@media screen and (max-width: 1300px) {
  .home3{
    &__title{
      font-size: var(--font-size-30);
    }
    &__content{
      &__left{
        width: 400px;
        margin-right: 5rem;
      }
      &__right{
        font-size: var(--font-size-16);
      }
    }
  }
  .home4{
    &__title{
      font-size: var(--font-size-30);
    }
    &__card{
      width: 250px;
      &__img{
        height: 320px;
      }
      &__img2{
        height: 320px;
      }
      &__img3{
        height: 320px;
      }
      &__img4{
        height: 320px;
      }
      &__title{
        font-size: var(--font-size-18);
      }
      &__content{
        font-size: var(--font-size-12);
      }              
    }
  }
  .home5 {
    &__title{
      font-size: var(--font-size-30);
    }
    &__content{
      height: auto;
      img{
        width: 300px;
      }
      &__plus{
        width: 70px;
        height: 70px;
      }
    }
    &__card{
      width: 250px;
      &__img{
        height: 250px;
      }
      &__img2{
        height: 30px;
      }
      &__img3{
        height: 200px;
      }
      &__title{
        font-size: var(--font-size-18);
      }
    }
  }
  .home6 {
    &__title{
      font-size: var(--font-size-30);
    }
    &__content{
      img{
        width: 260px;
        bottom: -90px;
      }
    }
    &__card{
      width: 300px;
      &__img{
        width: 296px;
        height: 296px;
      }
      &__img2{
        width: 296px;
        height: 296px;
      }
      &__title{
        font-size: var(--font-size-18);
      }
    }
  }
}
@media screen and (max-width: 1200px) {
  .home2 {
    &__box{
      width: 580px;
      margin-top: 5rem;
      margin-left: 20rem;
    }
    &__title{
      font-size: var(--font-size-40);
    }
    p{
      margin: 0.7rem 0;
    }
    &__content{
      font-size: var(--font-size-16);
    }
  }
}
@media screen and (max-width: 1100px) {
  .home4{
    &__content{
      width: 60%;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      align-items: center;
    }
    &__card{
      width: 230px;
      height: 240px;
      margin-bottom: 3rem;
      &__img{
        height: 170px;
      }
      &__img2{
        height: 170px;
      }
      &__img3{
        height: 170px;
      }
      &__img4{
        height: 170px;
      }
      &__content{
        display: none;
      }     
    }
  }
  .home6 {
    &__title{
      font-size: var(--font-size-30);
    }
    &__content{
      flex-direction: column;
      img{
        display: none;
      }
    }
    &__card{
      width: 240px;
      margin: 0 5rem;
      &__img{
        width: 236px;
        height: 236px;
      }
      &__img2{
        width: 236px;
        height: 236px;
      }
      &__title{
        font-size: var(--font-size-18);
      }
    }
  }  
}
@media screen and (max-width: 1000px) {
  .home3{
    &__title{
      font-size: var(--font-size-26);
    }
    &__content{
      &__left{
        width: 400px;
        margin-right: 3rem;
      }
      &__right{
        font-size: var(--font-size-14); 
      }
    }
  }  
}
@media screen and (max-width: 900px) {
  .home2 {
    &__box{
      width: 430px;
      margin-top: 7rem;
      margin-left: 13rem;
    }
    &__title{
      font-size: var(--font-size-28);
    }
    p{
      margin: 0.7rem 0;
    }
    &__content{
      font-size: var(--font-size-12);
    }
  }
  .home4{
    &__card{
      width: 40%;
      height: 60%;
      &__img{
        height: 70%;
      }
      &__img2{
        height: 70%;
      }
      &__img3{
        height: 70%;
      }
      &__img4{
        height: 70%;
      }  
    }
  }
  .home5 {
    &__content{
      img{
        width: 270px;
      }
      &__plus{
        width: 50px;
        height: 50px;
      }
    }
    &__card{
      width: 200px;
      &__img{
        height: 200px;
      }
      &__img2{
        height: 20px;
      }
      &__img3{
        height: 160px;
      }
      &__title{
        font-size: var(--font-size-16);
      }
    }
  }
  .home6 {
    &__title{
      font-size: var(--font-size-30);
    }
    &__content{
      img{
        width: 200px;
        bottom: -90px;
      }
    }
    &__card{
      width: 220px;
      margin: 0 3rem;
      &__img{
        height: 216px;
      }
      &__img2{
        height: 216px;
      }
      &__title{
        font-size: var(--font-size-18);
      }
    }
  }    
}

</style>