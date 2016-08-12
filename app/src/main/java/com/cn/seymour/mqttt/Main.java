package com.cn.seymour.mqttt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * ...
 * <br>Created by seyMour on 2016/7/21.</br>
 * <br>☞120465271@qq.com☜</br>
 */
public class Main extends AppCompatActivity {

    Context context = this;

    Button buttonmqtt;
    Button buttontcp;
    Button buttonhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        buttonmqtt = (Button)findViewById(R.id.buttonmqtt);
        buttonmqtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,MainActivity.class);
                startActivity(intent);
            }
        });
        buttontcp = (Button)findViewById(R.id.buttontcp);
        buttontcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,TCPActivity.class);
                startActivity(intent);
            }
        });
        buttonhttp =(Button)findViewById(R.id.buttonhttp);
        buttonhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,HttpActivityBack.class);
                startActivity(intent);
            }
        });
    }
}
