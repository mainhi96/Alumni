package com.example.sony.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class FindUser extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView searchView;
    private ListView listview;
    // tạo string array Name cho listview
    String[] NAME = {"Nam","Hoa","Huong","Lan","Minh","Duong"};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finduser);

        // khởi tạo adapter
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, NAME);
        listview = (ListView) findViewById(R.id.lvData);
        //set adapter cho listview
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        searchView= (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);




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
}


