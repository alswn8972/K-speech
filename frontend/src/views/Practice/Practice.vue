<template>
  <div class="practice">
      <div class="practice_right_content" @click="moveSentence"/>
      <div class="practice__content">
          <div class="practice__card" @click="moveWord">
            <div class="practice__card__img"/>
            <div class="practice__card__title blue">단어 연습</div>
          </div>
          <div class="practice__card" @click="moveSentence">
            <div class="practice__card__img2"/>
            <div class="practice__card__title blue">문장 연습</div>
          </div>
        </div>        
    </div>
</template>

<script>

export default {
  name: "practice",
  data: () => {
    return {
      gameType : null
    }
  },
  methods:{    
    moveWord(){
      this.gameType="word";
      this.$router.push({name: 'word', params: {gameType : this.gameType}});
    },
    moveSentence(){
      this.gameType="sentence";
      this.$router.push({name: 'sentence', params: {gameType : this.gameType}});
    }
  }
};
</script>

<style lang="scss" scoped>
.practice {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #FFF56E;
  background-size: cover;
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
      cursor : pointer;
      border-radius: 50%;
      border: 4px solid var(--color-white);
      width: 396px;
      height: 396px;
      background: center no-repeat url("../../assets/word.png");
      background-size: 100% 100%;
    }
    &__img2{
      cursor : pointer;
      border-radius: 50%;
      border: 4px solid var(--color-white);      
      width: 396px;
      height: 396px;
      background: center / contain no-repeat url("../../assets/sentence.png");
      background-size: 100% 100%;
    }
    &__title{
      padding: 1rem 0;
      text-align: center;
      font-size: var(--font-size-20);
    }
  }
}
@media screen and (max-width: 1300px) {
  .practice {
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
@media screen and (max-width: 1100px) {
  .practice {
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
@media screen and (max-width: 900px) {
  .practice {
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
