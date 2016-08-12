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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.seymour.mqttt.component.LLinearLayoutView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;

/**
 * <br>Created by seyMour on 2016/7/21.</br>
 * <br>☞120465271@qq.com☜</br>
 */
public class TCPActivity extends AppCompatActivity {
    TCPActivity tcpActivity = this;
    EditText editTextIP;
    Button buttonIP;
    EditText editTextMessage;
    EditText editTextMessage1;
    EditText editTextMessage2;
    EditText editTextMessage3;
    EditText editTextMessage4;
    Button buttonMessage;
    Button buttonMessage1;
    Button buttonMessage2;
    Button buttonMessage3;
    Button buttonMessage4;
    Button buttonConnect;
    //    Button buttonConnect1;
    Button buttonDisconnect;
//    Button buttonDisconnect1;

    Button buttonPm25Register;
    Button buttonWaterRegister;
    Button buttonPm25Upload;

    THandler handler;

    Socket socket;

    private BufferedReader in = null;
    private PrintWriter out = null;

    private ConnectivityManager manager;

    static Dialog dialog;

    String buffer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);
        editTextIP = (EditText) findViewById(R.id.edittext_ip);
        buttonIP = (Button) findViewById(R.id.button_ip);
        editTextMessage = (EditText) findViewById(R.id.edittext_message);
        editTextMessage1 = (EditText) findViewById(R.id.edittext_message1);
        editTextMessage2 = (EditText) findViewById(R.id.edittext_message2);
        editTextMessage3 = (EditText) findViewById(R.id.edittext_message3);
        editTextMessage4 = (EditText) findViewById(R.id.edittext_message4);
        buttonMessage = (Button) findViewById(R.id.button_message);
        buttonMessage1 = (Button) findViewById(R.id.button_message1);
        buttonMessage2 = (Button) findViewById(R.id.button_message2);
        buttonMessage3 = (Button) findViewById(R.id.button_message3);
        buttonMessage4 = (Button) findViewById(R.id.button_message4);
        buttonConnect = (Button) findViewById(R.id.button_connect);
        //buttonConnect1 = (Button)findViewById(R.id.button_connect1);
        buttonDisconnect = (Button) findViewById(R.id.button_disconnect);
        //buttonDisconnect1 = (Button)findViewById(R.id.button_disconnect1);
        buttonPm25Register = (Button)findViewById(R.id.button_pm25_register);
        buttonPm25Upload = (Button)findViewById(R.id.button_pm25_dataupload);
        buttonWaterRegister = (Button)findViewById(R.id.button_water_register);
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        initView();

        buttonIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.ip = editTextIP.getText().toString();
            }
        });

        handler = new THandler(tcpActivity);
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket == null) {
                    handler.sendEmptyMessage(1);
                } else {
                    if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                        handler.sendEmptyMessage(2);
                    } else {
                        try {
                            in = new BufferedReader(new InputStreamReader(socket
                                    .getInputStream()));
                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())), true);
                            if (!socket.isOutputShutdown()) {
                                out.println(editTextMessage.getText().toString());
                            }

                            new Thread(){
                                @Override
                                public void run() {
                                    try {
                                        buffer = in.readLine();
                                        Message msg = Message.obtain();
                                        msg.obj = buffer;
                                        msg.what = 9;
                                        handler.sendMessage(msg);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket == null) {
                    handler.sendEmptyMessage(1);
                } else {
                    if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                        handler.sendEmptyMessage(2);
                    } else {
                        try {
                            in = new BufferedReader(new InputStreamReader(socket
                                    .getInputStream()));
                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())), true);
                            if (!socket.isOutputShutdown()) {
                                out.println(editTextMessage1.getText().toString());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonPm25Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket == null) {
                    handler.sendEmptyMessage(1);
                } else {
                    if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                        handler.sendEmptyMessage(2);
                    } else {
                        try {
                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())), true);
                            if (!socket.isOutputShutdown()) {
                                out.println("A55A18020102030405060708404B59440107000010071C010001A355AA");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonWaterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket == null) {
                    handler.sendEmptyMessage(1);
                } else {
                    if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                        handler.sendEmptyMessage(2);
                    } else {
                        try {
                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())), true);
                            if (!socket.isOutputShutdown()) {
                                out.println("A55A18020102030405060708404B59440105000010071C220001BE55AA");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonPm25Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket == null) {
                    handler.sendEmptyMessage(1);
                } else {
                    if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                        handler.sendEmptyMessage(2);
                    } else {
                        try {
                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())), true);
                            if (!socket.isOutputShutdown()) {
                                out.println("A55A1B030102030405060708404B59440107000010071C01050802001ED355AA");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonMessage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket == null) {
                    handler.sendEmptyMessage(1);
                } else {
                    if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                        handler.sendEmptyMessage(2);
                    } else {
                        try {
                            in = new BufferedReader(new InputStreamReader(socket
                                    .getInputStream()));
                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())), true);
                            if (!socket.isOutputShutdown()) {
                                out.println(editTextMessage2.getText().toString());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonMessage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket == null) {
                    handler.sendEmptyMessage(1);
                } else {
                    if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                        handler.sendEmptyMessage(2);
                    } else {
                        try {
                            in = new BufferedReader(new InputStreamReader(socket
                                    .getInputStream()));
                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())), true);
                            if (!socket.isOutputShutdown()) {
                                out.println(editTextMessage3.getText().toString());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonMessage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket == null) {
                    handler.sendEmptyMessage(1);
                } else {
                    if (socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                        handler.sendEmptyMessage(2);
                    } else {
                        try {
                            in = new BufferedReader(new InputStreamReader(socket
                                    .getInputStream()));
                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                    socket.getOutputStream())), true);
                            if (!socket.isOutputShutdown()) {
                                out.println(editTextMessage4.getText().toString());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                new Thread(runnableConnect).start();
                handler.postDelayed(new Thread(runnableListen), 5000);
            }
        });
        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    socket.close();
                    handler.sendEmptyMessage(7);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Runnable runnableConnect = new Runnable() {
        @Override
        public void run() {
            try {
                if (manager.getActiveNetworkInfo() == null || !manager.getActiveNetworkInfo().isAvailable()) {
                    handler.sendEmptyMessage(4);
                } else {
                    socket = new Socket(Constant.ip, Integer.valueOf(Constant.port));
                    if (socket.isConnected()) {
                        dialog.dismiss();
                        handler.sendEmptyMessage(6);
                    }
                }
            } catch (Exception e) {
                handler.sendEmptyMessage(5);
                e.printStackTrace();
            }
        }
    };

    Runnable runnableListen = new Runnable() {
        @Override
        public void run() {
            if (manager.getActiveNetworkInfo().isAvailable()) {
                if (socket == null || socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(3);
                }
            } else {
                handler.sendEmptyMessage(8);
            }
            handler.postDelayed(this, 5000);
        }
    };


    static class THandler extends Handler {
        private WeakReference<TCPActivity> weakReference;

        public THandler(TCPActivity tcpActivity) {
            weakReference = new WeakReference<>(tcpActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TCPActivity tcpActivity = weakReference.get();
            if (tcpActivity != null) {
                switch (msg.what) {
                    case 0:
                        tcpActivity.editTextMessage.setEnabled(false);
                        tcpActivity.buttonDisconnect.setEnabled(false);
                        break;
                    case 3:
                        tcpActivity.editTextMessage.setEnabled(true);
                        tcpActivity.buttonDisconnect.setEnabled(true);
                        break;
                    case 1:
                        Toast.makeText(tcpActivity.getApplicationContext(), "TCP未连接", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(tcpActivity.getApplicationContext(), "TCP已关闭", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(tcpActivity.getApplicationContext(), "请打开网络连接", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        dialog.dismiss();
                        Toast.makeText(tcpActivity.getApplicationContext(), "连接失败！", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        tcpActivity.buttonConnect.setEnabled(false);
                        tcpActivity.buttonMessage.setEnabled(true);
                        tcpActivity.buttonMessage1.setEnabled(true);
                        tcpActivity.buttonMessage2.setEnabled(true);
                        tcpActivity.buttonMessage3.setEnabled(true);
                        tcpActivity.buttonMessage4.setEnabled(true);
                        tcpActivity.buttonDisconnect.setEnabled(true);
                        tcpActivity.buttonPm25Register.setEnabled(true);
                        tcpActivity.buttonPm25Upload.setEnabled(true);
                        tcpActivity.buttonWaterRegister.setEnabled(true);
                        Toast.makeText(tcpActivity.getApplicationContext(), "已连接至服务器！", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        tcpActivity.buttonConnect.setEnabled(true);
                        tcpActivity.buttonMessage.setEnabled(false);
                        tcpActivity.buttonMessage1.setEnabled(false);
                        tcpActivity.buttonMessage2.setEnabled(false);
                        tcpActivity.buttonMessage3.setEnabled(false);
                        tcpActivity.buttonMessage4.setEnabled(false);
                        tcpActivity.buttonDisconnect.setEnabled(false);
                        tcpActivity.buttonPm25Register.setEnabled(false);
                        tcpActivity.buttonPm25Upload.setEnabled(false);
                        tcpActivity.buttonWaterRegister.setEnabled(false);
                        Toast.makeText(tcpActivity.getApplicationContext(), "已断开服务器连接！", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(tcpActivity.getApplicationContext(), "系统异常！", Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(tcpActivity.getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
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
