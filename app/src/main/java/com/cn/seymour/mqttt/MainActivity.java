package com.cn.seymour.mqttt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    MainActivity mainActivity  = this;
    private TextView resultTv;
    String host = "tcp://192.168.1.222:61613";
    String userName = "admin";
    String passWord = "password";

    MyHandler myHandler = new MyHandler(this);
    static MqttClient client;
    static String myTopic = "test/topic";
    MqttConnectOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = (TextView) findViewById(R.id.result);

        init();

        myHandler =  new MyHandler(mainActivity);
    }

    static class MyHandler extends Handler{
        private WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity mainActivity = weakReference.get();
            if(mainActivity != null){
                if (msg.what == 1) {
                    Toast.makeText(mainActivity, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    System.out.println("-------------------");
                } else if (msg.what == 2) {
                    Toast.makeText(mainActivity, "连接成功", Toast.LENGTH_SHORT).show();
                    try {
                        client.subscribe(myTopic, 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (msg.what == 3) {
                    Toast.makeText(mainActivity, "连接失败，系统正在重连", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void init() {
        try {

            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保
            client = new MqttClient(host, "test",
                    new MemoryPersistence());

            //MQTT的连接设置
            options = new MqttConnectOptions();

            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);

            //设置连接的用户名
            options.setUserName(userName);

            //设置连接的密码
            options.setPassword(passWord.toCharArray());

            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);

            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);

            //设置回调
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    System.out.println("connectionLost----------");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                    System.out.println("deliveryComplete---------"
                            + token.isComplete());
                }

                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {

                    //subscribe后得到的消息会执行到这里面
                    System.out.println("messageArrived----------" + message.toString());
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = topicName + "---" + message.toString();
                    myHandler.sendMessage(msg);
                }
            });
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.connect(options);
                    Message msg = new Message();
                    msg.what = 2;
                    myHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 3;
                    myHandler.sendMessage(msg);
                }
            }
        }).start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (client != null && keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                client.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
