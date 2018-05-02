package com.example.sony.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private ListView lvChat;
    private EditText edtChat;
    private Button btnSend;
    private ArrayList<String> content;
    private InputStream in;
    private OutputStream out;
    private Socket socket;
    public static String mess;
    public static  String messSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        lvChat = (ListView) findViewById(R.id.lvChat);
        edtChat = (EditText) findViewById(R.id.edtChat);
        btnSend = (Button) findViewById(R.id.btnSend);
        content = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, content);
        lvChat.setAdapter(adapter);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    socket = new Socket("192.168.1.127", 1234);
                    in = socket.getInputStream();
                    out = socket.getOutputStream();
                    send(Home.name);
                    while (true) {
                        ChatActivity.mess = received();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                content.add(mess);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }

                } catch (UnknownHostException e) {
                    System.err.println("Don't know about host ");
                    return;
                } catch (IOException e) {
                    System.err.println("Couldn't get I/O for the connection to " + "\n" + e.getMessage());
                    return;
                }
            }

        }).start();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatActivity.messSend= edtChat.getText().toString();
                send(ChatActivity.messSend);
                content.add(Home.name+": "+ChatActivity.messSend);
                edtChat.setText("");

            }
        });


    }






public void send(String mess){
        try {
        out.write(mess.getBytes());
        } catch (IOException e) {
        System.out.println("Can't send");
        }
        }

public String received(){
        byte[] buff = new byte[2048];
        String mess;

        try {

        int receivedBytes = in.read(buff);
            if(receivedBytes<1) return "Ket thuc";
        mess = new String(buff,0,receivedBytes);
        System.out.println("Nhan duoc" +mess);

        //   Toast.makeText(MainActivity.this,mess+"",Toast.LENGTH_LONG).show();
        return mess;


        } catch (Exception e) {
        System.out.println("Can't read "+e);
        }

        return "";
        }






    }



