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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btnSign;
    private TextView tvJson;
    EditText username;
    EditText passwords;
    private Button btnRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSign = (Button) findViewById(R.id.btnSign);
        tvJson = (TextView) findViewById(R.id.tvJson);
        btnRes = (Button) findViewById(R.id.btnRegister);

        passwords= findViewById(R.id.edtpass);
        username= findViewById(R.id.edtname);


        btnSign.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {


                                             String   name= username.getText().toString();
                                               String pass= passwords.getText().toString();
                                               if(name.length()>0&&pass.length()>0)
                                               new DangNhap().execute("http://192.168.1.127/alumniserver/api/alumni?nameacc="+name);
                                               else tvJson.setText("Mời nhập đủ thông tin");
                                           }


                                       }
        );

        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Dangky.class);
                startActivity(intent);
            }
        });
    }



    public class DangNhap extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection=null;
            BufferedReader reader=null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                //       connection.setRequestMethod("GET");

                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line="";

                while ((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                String data="";

               /* JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("parentObject");
                JSONObject finalObject = parentArray.getJSONObject(0);

                String description = finalObject.getString("description");*/


                // String titre = finalObject.getString("title");
                try {

                    // JSONArray jsonArray = new JSONArray(finalJson);
                    //
                    //3. Duyệt từng đối tượng trong Array và lấy giá trị ra
                    //  for(int i = 0; i < jsonArray.length(); i++) {
                    //    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // String name = jsonObject.optString("user").toString();
                    JSONObject jsonObject= new JSONObject(finalJson);
                    String pass = jsonObject.optString("passwords").toString();


                    data= pass.trim();
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return data;





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
            String pass= passwords.getText().toString();
            String name= username.getText().toString();
            if (result.equals(pass)){
                Intent intent = new Intent(MainActivity.this, Home.class);
                intent.putExtra("username",name);
                startActivity(intent);

            }
            else {
                tvJson.setText("Mật khẩu không chính xác hoặc tài khoản không tồn tại");
                Toast.makeText(MainActivity.this, "Mật khẩu không chính xác hoặc tài khoản không tồn tại", Toast.LENGTH_LONG);

            }
            }
    }




}