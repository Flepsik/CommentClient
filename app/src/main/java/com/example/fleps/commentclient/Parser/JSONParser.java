package com.example.fleps.commentclient.Parser;

import android.util.Log;

import com.example.fleps.commentclient.Activity.AllCommentsActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

public class JSONParser {

    InputStream is = null;
    JSONObject jObj = null;
    JSONArray jObj2 = null;
    String json = "";

    // constructor
    public JSONParser() {

    }

    // метод получение json объекта по url
    // используя HTTP запрос и методы POST или GET
    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {

        // Создаем HTTP запрос
        try {

            // проверяем метод HTTP запроса
            if (method == "POST") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else if (method == "GET") {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "/" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // пытаемся распарсить строку в JSON объект
        try {
            jObj = new JSONObject(json);

        } catch (JSONException e) {
            try {
                jObj2 = new JSONArray(json);
                jObj = new JSONObject();
                jObj.put(AllCommentsActivity.TAG_COMMENTS, jObj2);
            } catch (Exception e1) {
                return null;
            }
        }

        // возвращаем JSON строку
        return jObj;

    }

}