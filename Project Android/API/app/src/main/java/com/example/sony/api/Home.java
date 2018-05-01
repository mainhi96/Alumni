package com.example.sony.api;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class Home extends AppCompatActivity {
    private TextView tvName;
    private TextView tvJob;
    private TextView tvYear;
    private Button btnUp;
   public static String kt=""; //kiem tra xem co info chua

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvName = (TextView) findViewById(R.id.tvName);
        tvJob = (TextView) findViewById(R.id.tvJob);
        tvYear = (TextView) findViewById(R.id.tvYear);
        btnUp = (Button) findViewById(R.id.btnEdt);


        Intent intent = getIntent();
        final String stringValue = intent.getStringExtra("username");


        new Home.GetInfo().execute("http://192.168.1.127/alumniserver/api/alumni?nameinfo=" + stringValue);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, UpdateInfo.class);
                intent.putExtra("username", stringValue);
                intent.putExtra("kt",kt);
                startActivity(intent);
                onRestart();

            }
        });

    }

    public class GetInfo extends AsyncTask<String, String, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                //       connection.setRequestMethod("GET");

                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                if (finalJson.length() < 10) return null;
                ArrayList<String> data = new ArrayList<>();

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
                    JSONObject jsonObject = new JSONObject(finalJson);
                    String username = jsonObject.optString("username").toString();
                    data.add(username);
                    String job = jsonObject.optString("job").toString();
                    data.add(job);
                    String year = jsonObject.optString("startyear").toString();
                    year += " - ";
                    year += jsonObject.optString("endyear").toString();
                    data.add(year);
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
                if (connection != null) {
                    connection.disconnect();
                }

                if (reader != null) {
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
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            if (strings != null) {
                tvName.setText(strings.get(0));
                tvJob.setText(strings.get(1));
                tvYear.setText(strings.get(2));
            }
            else kt="null";
        }
    }
}
