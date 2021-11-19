# API

## KaKao Map API

1. 해당 주소에서 로그인 후 API 키 발급

[Kakao 지도 API](https://apis.map.kakao.com/)

1. API 키 입력
    - fronted/src/pages/Map.vue
    
    ```jsx
    
    addKakaoMapScript() {
          const script = document.createElement("script");
          /* global kakao */
          script.onload = () => kakao.maps.load(this.initMap);
          script.src =
            "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=[APPKEY]";
          document.head.appendChild(script);
     },
    ```