<template>
  <div class="search">
    <select name="search__category" id="search__category" class="search__category" v-model="selectedVal" @change="handleSelect">
      <option value="title">제목</option>
      <option value="name">작성자</option>
    </select>
    <input type="text" v-model="inputVal" class="search__input" placeholder="검색어를 입력하세요" @input="handleSearch">
  </div>
</template>

<script>
export default {
  name: 'Search',
  props:{
    keyword: String,
    category: String,
  },
  data(){
    return{
      selectedVal:"title",
      inputVal: "",
    }
  },
  methods:{
    handleSelect(){
      this.$emit('handleSelect', this.selectedVal)
    },
    handleSearch(e){
      this.inputVal=e.target.value;
      this.$emit('handleSearch',this.inputVal);
    }
  }
}
</script>

<style lang="scss" scoped>

.search{
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin: 1rem 0 ;
  
  &__category{
    font-family: "AppleSDGothicNeoSB";
    color: var(--color-grey-8);
    padding: 0.5rem 1rem;
    ::selection{
      color: var(--color-grey-2);
    }
  }
  &__input{
    width: 400px;
    font-family: "AppleSDGothicNeoSB";
    color: var(--color-grey-2);
    background-color: var(--color-grey-9);
    padding: 0.5rem 0.5rem;
  }
  &__input::placeholder{
    color: var(--color-grey-8);
  }
}
@media screen and (max-width: 800px) {
  .search__input{
    width: 250px;
  }
}
</style>