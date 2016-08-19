package com.cn.seymour.mqttt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * ...
 * <br>Created by seyMour on 2016/8/12.</br>
 * <br>☞120465271@qq.com☜</br>
 */
public class TestLogin extends AppCompatActivity {

    Button buttonLogin;
    Button buttonSent;
    HttpURLConnection conn;
    BufferedReader bufferedReader;
    String str;
    String JSESSIONID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testlogin);
        buttonLogin = (Button)findViewById(R.id.button_login);
        buttonSent = (Button)findViewById(R.id.button_cent);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://192.168.1.222:8080/QiwuOffice/ajaxlogin.json?j_username=123&j_password=1");
                            conn = (HttpURLConnection)url.openConnection();
                            conn.setRequestMethod("POST");
                            Map map = conn.getHeaderFields();
                            JSESSIONID = map.entrySet().toArray()[4].toString().split(";")[0].substring(23, 55);
                            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while(bufferedReader.read() != -1){
                                System.out.println(bufferedReader.readLine());
                            }
                            bufferedReader.close();
                            conn.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        buttonSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://192.168.1.222:8080/QiwuOffice/base/feedback/savefeedback.json?username=13688888888&feedback=xxxx");
                            conn = (HttpURLConnection)url.openConnection();
                            conn.setRequestProperty("Cookie", "JSESSIONID=" + JSESSIONID);
                            conn.setRequestMethod("POST");
                            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while(bufferedReader.read() != -1){
                                System.out.println(bufferedReader.readLine());
                            }
                            bufferedReader.close();
                            conn.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
