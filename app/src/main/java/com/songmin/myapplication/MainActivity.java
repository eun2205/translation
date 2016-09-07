package com.songmin.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    public static final String TARGET_URL = "https://openapi.naver.com/v1/language/translate";
    String[] params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        params = new String[3];
        editText = (EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.textView);
        Button btn =(Button)findViewById(R.id.btn_ko_to_en);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params[0] = editText.getText().toString();
                params[1] = "ko";
                params[2] = "en";
                new AsyncTrans().execute(params);
            }
        });

        btn =(Button)findViewById(R.id.btn_en_to_ko);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params[0] = editText.getText().toString();
                params[1] = "en";
                params[2] = "ko";
                new AsyncTrans().execute(params);
            }
        });

        btn =(Button)findViewById(R.id.btn_ko_cn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params[0] = editText.getText().toString();
                params[1] = "ko";
                params[2] = "zh-CN";
                new AsyncTrans().execute(params);
            }
        });

        btn =(Button)findViewById(R.id.btn_ko_to_ja);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params[0] = editText.getText().toString();
                params[1] = "ko";
                params[2] = "ja";
                new AsyncTrans().execute(params);
            }
        });
    }

    public class AsyncTrans extends AsyncTask<String, Integer, String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result ="";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();
            try{
                queryBuf.append("text="+strings[0])
                        .append("&source="+strings[1])
                        .append("&target="+strings[2]);

                URL target = new URL(TARGET_URL);
                conn = (HttpURLConnection)target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("X-Naver-Client-Id", " EoqHhA2zE3io6u505lUo");
                conn.setRequestProperty("X-Naver-Client-Secret", "4FcfMQQfRv");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(new String(queryBuf.toString()).getBytes("UTF-8"));
                toServer.close();

                int resCode = conn.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_OK){
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String onLine="";

                    StringBuilder jsonBuf = new StringBuilder();
                    while((onLine = fromServer.readLine())!=null){
                        jsonBuf.append(onLine);
                    }
                    Log.e("ssongmin", jsonBuf.toString());
                    result =MainActivity.getJSon(jsonBuf);

                }

            }catch(Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }

    public static String getJSon(StringBuilder buf){
        JSONObject jo = null;
        JSONObject jo2 = null;
        JSONObject jo3 = null;
        JSONArray jsonArray = null;
        String result = "";
        try{
            jo = new JSONObject(buf.toString());
            jo2 = jo.getJSONObject("message");
            jo3 = jo2.getJSONObject("result");
            result = jo3.getString("translatedText");

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
