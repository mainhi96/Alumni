package com.example.sony.api;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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

public class FindUser extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView searchView;
    private ListView listview;
    // tạo string array Name cho listview
   public static String[] namelist={};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finduser);

        new FindList().execute("http://192.168.1.127/alumniserver/api/alumni");

        // khởi tạo adapter
        listview = (ListView) findViewById(R.id.lvData);

        searchView= (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new FindInfo().execute("http://192.168.1.127/alumniserver/api/alumni?nameinfo="+listview.getItemAtPosition(position).toString());
            }
        });







    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    //phương thúc lọc khi search
    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)){
            adapter.getFilter().filter("");
            listview.clearTextFilter();
        }else {
            adapter.getFilter().filter(newText.toString());
        }
        return true;
    }
    public class FindList extends AsyncTask<String,String,ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
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

                ArrayList<String> data= new ArrayList<>();
                data.add("0");

                try {

                    JSONArray jsonArray = new JSONArray(finalJson);
                    //3. Duyệt từng đối tượng trong Array và lấy giá trị ra
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                     String name1 = jsonObject.optString("user").toString();
                     data.add(name1);
                       // System.out.println(name1);

                    }
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
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            FindUser.namelist = result.toArray(namelist);
            adapter = new ArrayAdapter<String>(FindUser.this,android.R.layout.simple_list_item_1, namelist);

            listview.setAdapter(adapter);

            }

        }

        //Giống hàm Get Info bên Home
        public class FindInfo extends AsyncTask<String, String, ArrayList<String>> {
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
                        data.add(username.trim());
                        String job = jsonObject.optString("job").toString();
                        data.add(job.trim());
                        String year = jsonObject.optString("startyear").toString();
                        year += " - ";
                        year += jsonObject.optString("endyear").toString();
                        data.add(year.trim());
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

                    Toast.makeText(FindUser.this,strings.get(0)+" - " +strings.get(1)+" - "+strings.get(2),Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(FindUser.this,"Chưa có thông tin",Toast.LENGTH_LONG).show();
            }
        }
    }




