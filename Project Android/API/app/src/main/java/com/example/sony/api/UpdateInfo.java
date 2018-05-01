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

public class UpdateInfo extends AppCompatActivity {

    private TextView username;
    private EditText edtJob;
    private EditText edtStart;
    private EditText edtEnd;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        username= (TextView) findViewById(R.id.tvName);
        edtJob = (EditText) findViewById(R.id.edtJob);
        edtStart= (EditText) findViewById(R.id.edtStartYear);
        edtEnd= (EditText) findViewById(R.id.edtEndYear);
        btnUpdate= (Button) findViewById(R.id.btnUp);



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String stringValue = intent.getStringExtra("username");
                String kt= intent.getStringExtra("kt");
                String job= edtJob.getText().toString();
                String start= edtStart.getText().toString();
                String end= edtEnd.getText().toString();
                if (kt.equals("null"))
                new UpdateInfo.PostInfo().execute("http://192.168.1.127/alumniserver/api/alumni?name="+stringValue+"&job="+job+"&start="+start+"&end="+end);
                else new UpdateInfo.PostInfo().execute("http://192.168.1.127/alumniserver/api/alumni?user="+stringValue+"&job="+job+"&start="+start+"&end="+end);
            }
        });

    }
    public class PostInfo extends AsyncTask<String,String,String> {

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
                Toast.makeText(UpdateInfo.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();

                Intent intent = new Intent( getApplicationContext(),Home.class);
                startActivity(intent);
            }



        }
    }
}
