package com.cn.seymour.mqttt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.seymour.mqttt.component.LLinearLayoutView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Random;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ...
 * <br>Created by seyMour on 2016/8/8.</br>
 * <br>☞120465271@qq.com☜</br>
 */
public class HttpActivity extends AppCompatActivity implements View.OnClickListener {

    HttpActivity httpActivity = this;
    Button button_connect;
    Button button_disconnect;
    TextView textview_connect;
    InputStream inputStream;
    BufferedReader in;
    PrintWriter ou;

    private ConnectivityManager manager;

    HttpActivityHandler handler;

    Socket socket;

    Dialog dialog;

    String buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        button_connect = (Button) findViewById(R.id.button_connect);
        button_disconnect = (Button) findViewById(R.id.button_disconnect);
        textview_connect = (TextView) findViewById(R.id.textview_connect);
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        handler = new HttpActivityHandler(httpActivity);

        initListener();
        initView();
    }

    static class HttpActivityHandler extends Handler {
        private WeakReference<HttpActivity> weakReference;

        public HttpActivityHandler(HttpActivity httpActivity) {
            weakReference = new WeakReference<>(httpActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HttpActivity httpActivity = weakReference.get();
            if (httpActivity != null) {
                switch (msg.what) {
                    case 0:
                        httpActivity.textview_connect.setText("连接中。。。");
                        break;
                    case 1:
                        httpActivity.textview_connect.setText("断开连接!");
                        break;
                    case 4:
                        Toast.makeText(httpActivity.getApplicationContext(), "请打开网络连接", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        httpActivity.dialog.dismiss();
                        Toast.makeText(httpActivity.getApplicationContext(), "连接失败！", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(httpActivity.getApplicationContext(), "连接成功! "+(msg.obj==null), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    Runnable runnableConnect = new Runnable() {
        @Override
        public void run() {
            try {
                if (manager.getActiveNetworkInfo() == null || !manager.getActiveNetworkInfo().isAvailable()) {
                    handler.sendEmptyMessage(4);
                } else {
                    socket = new Socket(Constant.ip, 9997);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    ou = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

                    if (socket.isConnected()) {
                        dialog.dismiss();
                        handler.sendEmptyMessage(6);
                    }

                    ou.println("begin");



                    try {
                        buffer = in.readLine();
                        Message msg = Message.obtain();
                        msg.obj = buffer;
                        msg.what = 6;
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    new Thread(){
//                        @Override
//                        public void run() {
//
//                        }
//                    }.start();


                }
            } catch (Exception e) {
                handler.sendEmptyMessage(5);
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_connect:
                dialog.show();
                new Thread(runnableConnect).start();
                break;
            case R.id.button_disconnect:
                try {
                    socket.close();
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
    }

    private void initView() {
        // ======================登录对话框初始化========================
        if (null == this.dialog) {
            LLinearLayoutView dialogView = new LLinearLayoutView(this);
            dialogView.setBackgroundColor(Color.parseColor("#55000000"), -10,
                    -10, -10, -10, 5, 0,
                    Color.parseColor("#55000000"));
            dialogView.setGravity(Gravity.CENTER);
            dialogView.setOrientation(LinearLayout.VERTICAL);

            // 进度条
            ProgressBar bar = new ProgressBar(this);
            bar.setIndeterminate(true);
            bar.setIndeterminateDrawable(this.getResources().getDrawable(
                    R.drawable.lcontainerview_anim_loading));
            int w = 90;
            dialogView.addView(bar, w, w);

            TextView textView = new TextView(this);
            textView.setText("正在连接...");
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            textView.setPadding(0, 10, 0, 0);
            dialogView.addView(textView);

            this.dialog = new Dialog(this, R.style.dialog_style);
            this.dialog.setCanceledOnTouchOutside(false);
            LinearLayout.LayoutParams dialogLP = new LinearLayout.LayoutParams(900, 900);
            this.dialog.setContentView(dialogView, dialogLP);
        }
    }

}
