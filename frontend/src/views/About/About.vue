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
      <div class="home1__content__img">
          <div class="home1__card">
            <div class="home1__card__img"></div>
          </div>  
          <div class="home1__card">
            <div class="home1__card__img2"></div>
          </div>
          <div class="home1_card">
            <div class="home1__card__img3"></div>
          </div>
          <div class="home1__card">
            <div class="home1__card__img4"></div>
          </div>
        </div>    
      <div class="home1__box">
        <div class="home1__title">
          About K-speech?
        </div>
        <div class="home1__content">
          <div class="home1__card">
            <p style="font-size:50px; font-color:#">한국어 발음</p>
            <p style="font-size:30px;">너무 어려우셨죠?</p>
            <p style="font-size:30px;">이제는 K-speech에서 한번에 끝내보세요!</p>
            
          </div>
          
        </div>
      </div>
    </section>

    <section class="fullpage home2">
      <div class="home2__box">
        <div class="home2__title">
          01. 다양한 게임 제공
          <div class="home2__subtitle">발음연습에 특화된 게임을 통해 한국어 발음을 연습할 수 있습니다.</div>
        </div>
        <div class="home2__content">
          <div class="home2__card">
            <div class="home2__card__img"></div>
            <div class="home2__card__title blue">맑은 비 게임</div>
            <div class="home2__card__content">비구름에서 내려오는 단어들을 음성으로 발음 연습을 통해 점수를 얻어봅세요!</div>
          </div>
          <div class="home2__card">
            <div class="home2__card__img2"></div>
            <div class="home2__card__title blue">광석 캐기 게임</div>
            <div class="home2__card__content">원하는 단어를 발음하고 내가 얻을 수 있는 최고점을 얻어보세요!</div>
          </div>
        </div>
      </div>
    </section>


    <section class="fullpage home3">
      <div class="home3__box">
        <div class="home3__title">01. 다양한 게임 제공</div>
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
    <section class="fullpage home6">
      <div class="home6__box">
        <div class="home6__title">
          02. 가상환경에서의 상호작용
          <div class="home6__subtitle">개인학습 서비스가 제공됩니다.</div>
        </div>
        <div class="home6__content">
          <div class="home6__card">
            <div class="home6__card__img"></div>
            <div class="home6__card__title blue">산성비 게임</div>
          </div>
          <img src="../../assets/main_students.png" alt="">
          <div class="home6__content__plus"></div>
          <div class="home6__card">
            <div class="home6__card__img2"></div>
            <div class="home6__card__title blue">자원캐기 게임</div>
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

.blue{
  color: #000000;
}

.home3{
    //background: center no-repeat url("../../assets/bg2.gif");
  background-color: #eeda6c;
  background-size:100vw 100vh;
  font-family: "neodgm_pro";
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
.home2{
  background-color: #eeb66c;
  background-size:100vw 100vh;
  font-family: "neodgm_pro";
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
    border-radius: 5px;
    box-shadow: 0px 4px 8px #0000001A;
    background-color: var(--color-white);
    &__img{
      border-radius: 5px;
      height: 310px;
      background: center no-repeat url("../../assets/game.png");
      background-size: 100% 100%;
    }
    &__img2{
      border-radius: 5px;
      height: 310px;
      background: center no-repeat url("../../assets/game2.png");
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
.home1 {
  #nav{
    border: none;
  }
  background-color:#f09196;
  background-size:100vw 100vh;
  font-family: "neodgm_pro";
  font-weight: bold;
  &__box{
    width: 100vw;
    height: 100vh;
    display: flex;
    margin-top:-150px;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
  }
  &__title{
    text-align: center;
    font-size: var(--font-size-50);
    background-color: #ebd9d9;
    border-radius: 5px;
    padding:20px 20px 20px 20px;
    z-index: 10;
;
  }
  &__subtitle{
    padding: 0.5rem 0;
    text-align: center;
    color: #585858;
    font-size: var(--font-size-18);
  }
  &__content__img{
    display: flex;
    justify-content: center;
    //margin-top:-150px;
    align-items: center;    
    width: 60%;
    height: 450px;
    position: relative;
  }
  &__content{
    display: flex;
    justify-content: center;
    margin-top:-150px;
    align-items: center;    
    width: 60%;
    height: 450px;
    background-color: var(--color-white);
    position: relative;
    border-radius: 20px;
  }
  &__card{
   
    justify-content: center;
    &__img{
      border-radius: 50%;
      border: 4px solid var(--color-white);
      width: 196px;
      height: 196px;
      background: center no-repeat url("../../assets/picture1.png");
      background-size: 100% 100%;
    }
    &__img2{
      border-radius: 50%;
      border: 4px solid var(--color-white);      
      width: 196px;
      height: 196px;
      background: center / contain no-repeat url("../../assets/picture3.png");
      background-color: #FFF;
      background-size: 100% 100%;
    }
    &__img3{
      border-radius: 50%;
      border: 4px solid var(--color-white);      
      width: 196px;
      height: 196px;
      background: center / contain no-repeat url("../../assets/picture4.png");
      background-size: 100% 100%;
    }
    &__img4{
      border-radius: 50%;
      border: 4px solid var(--color-white);      
      width: 196px;
      height: 196px;
      background: center / contain no-repeat url("../../assets/picture5.png");
      background-size: 100% 100%;
    }
  }
}
.home6 {
  background-color: #b2fa9c;
  background-size:100vw 100vh;
  font-family: "neodgm_pro";
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