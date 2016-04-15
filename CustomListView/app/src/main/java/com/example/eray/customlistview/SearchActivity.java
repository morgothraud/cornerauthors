package com.example.eray.customlistview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macadmin on 8.4.2016.
 */
public class SearchActivity extends AppCompatActivity {

    public ListView listView;
    private CustomListAdapter adapter;
    private List<ListItemElement> listItemElementList = new ArrayList<ListItemElement>();
    private ArrayList<ListItemElement> listItemElementsforSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.list);
        Bundle bundleObject = getIntent().getExtras();
        listItemElementsforSearch = (ArrayList<ListItemElement>) bundleObject.getSerializable("items");
        adapter = new CustomListAdapter(this, listItemElementsforSearch);
        listView.setAdapter(adapter);

        setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        listView.setEnabled(true);
        setOnClickforListViewItem();
    }

    public void setOnClickforListViewItem( ){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent splash = new Intent(SearchActivity.this,SplashActivity.class);
                splash.putExtra("content", listItemElementsforSearch.get(Integer.parseInt((String)view.getTag())).getContent());
                ListItemElement le = (ListItemElement)(listView.getItemAtPosition(position));
                splash.putExtra("title",le.getWorth());
                startActivityForResult(splash, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
