<template>
  <div class="editor" v-if="editor">
    <div class="editor__title">
      <input class="editor__title__input" type="text" v-model="title" placeholder="제목을 입력해주세요." autofocus>
    </div>
    <menu-bar class="editor__header" :editor="editor" />
    <editor-content class="editor__content" :editor="editor"/>
  </div>
</template>

<script>
import { Editor, EditorContent,VueNodeViewRenderer } from '@tiptap/vue-2'
import { defaultExtensions } from '@tiptap/starter-kit'
import TaskList from '@tiptap/extension-task-list'
import TaskItem from '@tiptap/extension-task-item'
import Highlight from '@tiptap/extension-highlight'
import Document from '@tiptap/extension-document'
import Paragraph from '@tiptap/extension-paragraph'
import Table from '@tiptap/extension-table'
import TableRow from '@tiptap/extension-table-row'
import TableCell from '@tiptap/extension-table-cell'
import TableHeader from '@tiptap/extension-table-header'
import Text from '@tiptap/extension-text'
import Image from '@tiptap/extension-image'
import Dropcursor from '@tiptap/extension-dropcursor'
import MenuBar from './MenuBar.vue';
import CodeBlockLowlight from '@tiptap/extension-code-block-lowlight';
import CodeBlockComponent from './CodeBlockComponent';
import lowlight from 'lowlight';

export default {
  components: {
    EditorContent,
    MenuBar,
  },

  data() {
    return {
      editor: null,
      title:'',
    }
  },

  mounted() {
    this.editor = new Editor({
      extensions: [
        ...defaultExtensions().filter(extension => extension.name !== 'history'),
        Document,
        Paragraph,
        Text,
        Highlight,
        TaskList,
        TaskItem,
        Table.configure({
          resizable: true,
        }),
        TableRow,
        TableCell,
        TableHeader,
        Image,
        Dropcursor,
        CodeBlockLowlight
          .extend({
            addNodeView() {
              return VueNodeViewRenderer(CodeBlockComponent)
            },
          })
          .configure({ lowlight }),
      ],
    })
  },

  methods: {
  },

  beforeDestroy() {
    this.editor.destroy()
  },
}
</script>

<style lang="scss" scoped>
.editor {
  margin: 1rem;
  font-family: "AppleSDGothicNeoB";
  display: flex;
  flex-direction: column;
  color: #0D0D0D;
  background-color: white;
  border: 3px solid #0D0D0D;
  // border-radius: 0.75rem;
  &__title{
    width: 100%;
    &__input{
      width: 100%;
      font-size: var(--font-size-30);
      padding: 0.5em;
    }
    &__input:focus{
      outline: none;
    }
  }
  &__header {
    display: flex;
    align-items: center;
    flex: 0 0 auto;
    flex-wrap: wrap;
    padding: 0.25rem;
    border-top: 1px solid #cccccc;
    border-bottom: 1px solid #cccccc;
  }

  &__content {
    padding: 1.25rem 1rem;
    flex: 1 1 auto;
    overflow-x: hidden;
    overflow-y: auto;
    -webkit-overflow-scrolling: touch;
  }
}
</style>