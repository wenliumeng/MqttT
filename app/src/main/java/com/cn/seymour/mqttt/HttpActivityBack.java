package com.cn.seymour.mqttt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import okhttp3.HttpUrl;

/**
 * ...
 * <br>Created by seyMour on 2016/8/8.</br>
 * <br>☞120465271@qq.com☜</br>
 */
public class HttpActivityBack extends AppCompatActivity implements View.OnClickListener {

    HttpActivityBack httpActivity = this;
    Button button_connect;
    Button button_disconnect;
    Button button_sent;
    TextView textview_connect;
    InputStream inputStream;
    BufferedReader in;
    PrintWriter ou;
    HttpURLConnection conn;
    String JSESSION = "";

    Button button_login;
    Button button_logout;

    HttpActivityHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        button_connect = (Button) findViewById(R.id.button_connect);
        button_disconnect = (Button) findViewById(R.id.button_disconnect);
        textview_connect = (TextView) findViewById(R.id.textview_connect);
        button_sent = (Button) findViewById(R.id.button_sent);
        button_login = (Button) findViewById(R.id.button_login);
        button_logout = (Button) findViewById(R.id.button_logout);


        handler = new HttpActivityHandler(httpActivity);

        initListener();
    }

    static class HttpActivityHandler extends Handler {
        private WeakReference<HttpActivityBack> weakReference;

        public HttpActivityHandler(HttpActivityBack httpActivity) {
            weakReference = new WeakReference<>(httpActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            HttpActivityBack httpActivity = weakReference.get();
            if (httpActivity != null) {
                switch (msg.what) {
                    case 0:
                        httpActivity.textview_connect.setText("连接中。。。");
                        break;
                    case 1:
                        httpActivity.textview_connect.setText("断开连接!");
                        break;
                    case 100:
                        Toast.makeText(httpActivity.getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        httpActivity.button_login.setText("登陆"+msg.obj);
                        break;
                    case 101:
                        Toast.makeText(httpActivity.getApplicationContext(), "已经退出", Toast.LENGTH_SHORT).show();
                        httpActivity.button_login.setText("登陆");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void initDefaultHttp() throws IOException, InterruptedException {
        URL url = new URL("http://192.168.1.222:8080/QiwuOffice/devicesetting.json");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(1000 * 1000);
        conn.setReadTimeout(1000 * 1000);
        conn.setRequestProperty("Content-Type", "text/xml");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", "33");
        conn.setRequestProperty("Charset", "utf-8");

        //客户端发送
        ou = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.getOutputStream())), true);
        ou.println("begin");
        ou.flush();
        ou.close();

        //客户端接收
        inputStream = conn.getInputStream();
        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while (in.read() != -1) {
            System.out.println(in.readLine());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            URL url = new URL("http://192.168.1.222:8080/QiwuOffice/ajaxlogin.json?j_username=13688888888&j_password=4");
                            URL url = new URL("http://192.168.1.222:8080/QiwuOffice/ajaxlogin.json?j_username=123&j_password=1");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            InputStream inputStream = conn.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String line;
                            String buff = "";
                            while ((line = bufferedReader.readLine()) != null) {
                                buff = line + buff;
                            }
                            conn.getHeaderFields();
                            Set set = conn.getHeaderFields().keySet();
                            Iterator iterator = set.iterator();
                            while (iterator.hasNext()) {
                                Object o = iterator.next();
                                if (o != null && o.toString().equals("Set-Cookie")) {
                                    JSESSION = conn.getHeaderField("Set-Cookie").substring(11,43);
                                }
                            }
                            inputStream.close();
//                            conn.disconnect();
                            Message msg = Message.obtain();
                            msg.obj = buff + JSESSION;
                            msg.what = 100;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.button_logout:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL url = null;
                        try {
                            url = new URL("http://192.168.1.222:8080/QiwuOffice/j_spring_security_logout");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            System.out.println(JSESSION);
                            conn.setRequestProperty("Cookie", "JSESSIONID=" + JSESSION);
                            conn.connect();
                            System.out.println(conn.getResponseCode());
                            handler.sendEmptyMessage(101);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.button_connect:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                initDefaultHttp();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.button_disconnect:
                conn.disconnect();
                break;
            case R.id.button_sent:
                try {
                    in.close();
                    System.out.println(ou == null);
                    ou = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.getOutputStream())), true);
                    ou.println("!!!!!test data  : 再次发送");
                    ou.flush();
                    ou.close();

                    inputStream = conn.getInputStream();
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while (in.read() != -1) {
                        System.out.println(in.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            default:
                break;
        }
    }

    private void initListener() {
        button_connect.setOnClickListener(this);
        button_disconnect.setOnClickListener(this);
        button_sent.setOnClickListener(this);
        button_login.setOnClickListener(this);
        button_logout.setOnClickListener(this);
    }
}
