package com.example.eray.customlistview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Melkor on 29.3.2016.
 */
public class FavoriteAuthorsActivity extends BaseActivity {
    private static final String allURL = "https://gist.githubusercontent.com/anonymous/7dae51e5c07f078a023b/raw/822a479bbacda6e72347aec4f286f039bc001ad2/blob.json";
    ListView newsListView;
    ListView listView;
    MainActivity m;
    private CustomListAdapter adapter;
    Database db;
    List<ListItemElement> listItemElementList = new ArrayList<ListItemElement>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getLayoutInflater().inflate(R.layout.favorited_authors, frameLayout);
        db = new Database(this);
        listView = (ListView) findViewById(R.id.listSaved);
        adapter = new CustomListAdapter(this, listItemElementList,this);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.empty));
        mDrawerList.setItemChecked(position, true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(osArray[position]);
        listFromDB();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent splash = new Intent(FavoriteAuthorsActivity.this, SplashActivity.class);
                splash.putExtra("content", listItemElementList.get(Integer.parseInt((String) view.getTag())).getContent());
                startActivityForResult(splash, 1);
            }
        });
    }

    public void updateAdapter() {
        // this.recreate();
        adapter.notifyDataSetChanged();
        // totalClassmates.setText("(" + friendsList.size() + ")"); //update total friends in list

    }

    String[] favoriteAuthorsContent = new String[3];
    public void getFavoriteAuthorsContent(String jsonUrl, final String authorName){
        final JsonArrayRequest billionaireReq = new JsonArrayRequest(jsonUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Log.d("authorName",obj.getString("authorName"));
                                if(obj.getString("authorName").equals("AHMET ÇAKAR")) {
                                    Log.d("INSIDE","INSIDE");
                                    Log.d("INSIDEArticleName",obj.getString("articleName"));
                                    favoriteAuthorsContent[0] = obj.getString("articleName");
                                    favoriteAuthorsContent[1] = obj.getString("date");
                                    favoriteAuthorsContent[2] = obj.getString("content");
                                    Log.d("ITEM",favoriteAuthorsContent[0]);
                                    //listItemElement.setSource(cn.getAuthor().getNewsPaper().name);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(billionaireReq);
    }


    public void listFromDB () {

        // Reading all contacts
        Log.d("Reading: ", "Reading all favorites..");
        List<Article> contacts = db.getAllFavourites();
        for (Article cn : contacts) {
            getFavoriteAuthorsContent(allURL,cn.getAuthor().getName());
            String log = "Id: " + cn.getId() + cn.getHeader() + cn.getDate();
            // Writing Contacts to log
            Log.d("Name: ", log);
            ListItemElement listItemElement = new ListItemElement();
            listItemElement.setName(cn.getAuthor().getName());
            listItemElement.setThumbnailUrl(cn.getAuthor().getImageUrl());

//            Log.d("TEST",favoriteAuthorsContent[0]);
            listItemElement.setWorth(favoriteAuthorsContent[0]);
            listItemElement.setYear(favoriteAuthorsContent[1]);
            listItemElement.setContent(favoriteAuthorsContent[2]);
         //   Log.d("ITEMS",favoriteAuthorsContent[0]);
//          listItemElement.setSource(cn.getAuthor().getNewsPaper().name);
            listItemElement.setTag(String.valueOf(cn.getId()-1));
            Log.d("ID", String.valueOf(cn.getId()));
            listItemElementList.add(listItemElement);
        }
        adapter.notifyDataSetChanged();
    }


    protected void removeAnim(View rowView, final int positon) {
        final Animation animation = AnimationUtils.loadAnimation(
                FavoriteAuthorsActivity.this, R.anim.slide_out);
        rowView.startAnimation(animation);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {

            public void run() {
                listItemElementList.remove(positon);
                adapter.notifyDataSetChanged();
            }
        }, 700);}

    public void deleteArticleFromDB(String tag) {
        db.deleteFavouriteWithId(Integer.parseInt(tag));
        db.getTableAsString("favourites");
    }

    public void setOnClickforListViewItem( ){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent splash = new Intent(FavoriteAuthorsActivity.this,SplashActivity.class);
                splash.putExtra("content", listItemElementList.get(Integer.parseInt((String)view.getTag())).getContent());
                startActivityForResult(splash, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);
        return true;
    }


}
