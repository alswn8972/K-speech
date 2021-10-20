<template>
  <node-view-wrapper class="code-block">
    <!-- <select contenteditable="false" v-model="selectedLanguage">
      <option :value="null">
        <p>auto</p>
      </option>
      <option disabled>
      <p> — </p>
      </option>
      <option v-for="(language, index) in languages" :value="language" :key="index">
        <p>{{ language }}</p>
      </option>
    </select> -->
    <pre><node-view-content as="code" /></pre>
  </node-view-wrapper>
</template>

<script>
import { NodeViewWrapper, NodeViewContent, nodeViewProps } from '@tiptap/vue-2'

export default {
  components: {
    NodeViewWrapper,
    NodeViewContent,
  },

  props: nodeViewProps,

  // data() {
  //   return {
  //     languages: this.extension.options.lowlight.listLanguages().filter((language)=>{
  //       const a=['c','cpp','java','javascript','python','sql'];
  //       if(a.includes(language)) return true
  //       return false
  //     }),
  //   }
  // },

  // computed: {
  //   selectedLanguage: {
  //     get() {
  //       return this.node.attrs.language
  //     },
  //     set(language) {
  //       this.updateAttributes({ language })
  //     },
  //   },
  // },
}
</script>

<style lang="scss" scoped>
.code-block {
  position: relative;

  select {
    background-color: white;
    padding: 0.1rem;
    position: absolute;
    top: 0.5rem;
    right: 0.5rem;
  }
}
</style>