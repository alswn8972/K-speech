module.exports = {
  lintOnSave: false,

  devServer: {
    // 사용자 정의 환경 변수에서 VUE_APP_PORT가 있으면 사용하고
    // 없으면 8000 포트로 개발서버를 실행합니다.
    port: process.env.VUE_APP_PORT || 8000,
  },

  transpileDependencies: ["vuetify"],
};
