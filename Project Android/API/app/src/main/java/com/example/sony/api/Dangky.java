package com.example.sony.api;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Dangky extends AppCompatActivity {
    EditText username;
    EditText passwords;
    private Button btnRes;
    private TextView tvKq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        btnRes = (Button) findViewById(R.id.btnRes);
        tvKq = (TextView) findViewById(R.id.tvJson);


        passwords = findViewById(R.id.edtpass1);
        username = findViewById(R.id.edtname1);

        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=username.getText().toString();
              String pass=passwords.getText().toString();
                new Dangky.DangKy().execute("http://192.168.1.127/alumniserver/api/alumni?name="+name+"&pass="+pass);
            }
        });
    }
    public class DangKy extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection=null;
            BufferedReader reader=null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line="";

                while ((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                return finalJson;





            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if(connection!=null){
                    connection.disconnect();
                }

                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("true")){
                Toast.makeText(Dangky.this,"Đăng ký thành công, mời đăng nhập",Toast.LENGTH_LONG).show();
                Intent intent = new Intent( Dangky.this,MainActivity.class);
                startActivity(intent);
            }

            else tvKq.setText("Username đã được đăng ký. Mời nhập lại");

        }
    }

}
