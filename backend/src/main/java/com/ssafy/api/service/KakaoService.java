package com.ssafy.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;

@Service
public class KakaoService {
    public String getAccessToken(String code){
        String access_token="";
        String refresh_token="";
        String reqUrl="https://kauth.kakao.com/oauth/token";

        try{
            URL url=new URL(reqUrl);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb=new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=176a306530233dd86c855ff4bb75e587");
            sb.append("&redirect_url=http://localhost:8000");
            sb.append("&code="+code);
            bw.write(sb.toString());
            bw.flush();

            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line="";
            String result="";

            while ((line=br.readLine())!=null){
                result+=line;
            }
            JsonParser parser=new JsonParser();
            JsonElement element=parser.parse(result);

            access_token=element.getAsJsonObject().get("access_token").getAsString();
            refresh_token=element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return access_token;
    }
    public HashMap<String, String> getUserInfo(String access_Token){
        HashMap<String, String> userInfo=new HashMap<>();
        String reqURL="https://kapi.kakao.com/v2/user/me";

        try {
            URL url=new URL(reqURL);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer "+access_Token);

            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line="";
            String result="";

            while ((line=br.readLine())!=null){
                result+=line;
            }
            JsonParser parser=new JsonParser();
            JsonElement element=parser.parse(result);

            JsonObject kakao_account=element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            System.out.println(kakao_account.get("email").getAsString());

            String nickname=element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
            String email=kakao_account.get("email").getAsString();

            userInfo.put("nickName", nickname);
            userInfo.put("id", email);

        }catch (IOException e){
            e.printStackTrace();
        }
        return userInfo;
    }
}
