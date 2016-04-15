package com.example.eray.customlistview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Melkor on 29.3.2016.
 */
public class FavoriteAuthorsActivity extends BaseActivity {

    ListView newsListView;
    ListView listView;
    MainActivity m;
    private CustomListAdapter adapter;
    List<ListItemElement> listItemElementList = new ArrayList<ListItemElement>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.favorited_authors, frameLayout);
        listView = (ListView) findViewById(R.id.listSaved);
        adapter = new CustomListAdapter(this, listItemElementList);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.empty));
        mDrawerList.setItemChecked(position, true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ReadBtn();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent splash = new Intent(FavoriteAuthorsActivity.this, SplashActivity.class);
                splash.putExtra("content", listItemElementList.get(Integer.parseInt((String) view.getTag())).getContent());
                startActivityForResult(splash, 1);
            }
        });
    }

    public void listFreshArticles(String file,final String newpaper) throws JSONException {

        String tmp = file;
        file = "{ 'favorites': [" + tmp + "]}";
        JSONObject obj = new JSONObject(file);

        JSONArray jsonArray = obj.getJSONArray("favorites");

        Log.d("JSONOBJE",jsonArray.getJSONObject(0).toString());
        Log.w("Arrsize", String.valueOf(jsonArray.length()));
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explrObject = jsonArray.getJSONObject(i);
            ListItemElement listItemElement = new ListItemElement();;
            listItemElement.setName(explrObject.getString("authorName"));
            listItemElement.setThumbnailUrl(explrObject.getString("authorImage"));
            listItemElement.setSource(newpaper);
            listItemElement.setTag(String.valueOf(i));

            // adding Billionaire to listItemElement array
            listItemElementList.add(listItemElement);
            //  Log.e("Name",explrObject.getString("authorName"));
        }

     /*   JSONObject jobi = new JSONObject(file);
        Iterator<String> iter = jobi.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                Object value = finalOb.get(key);
                Log.w("VALUEEE", value.toString());
            } catch (JSONException e) {
                // Something went wrong!
            }
        }*/

      /*  JSONObject obj = new JSONObject(file);

        Iterator<String> iter = obj.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                ListItemElement listItemElement = new ListItemElement();
                listItemElement.setName(obj.getString("authorName"));
                listItemElement.setThumbnailUrl(obj.getString("authorImage"));
                listItemElement.setWorth(obj.getString("articleName"));
                listItemElement.setYear(obj.getString("date"));
                listItemElement.setSource(newpaper);
                listItemElement.setContent(obj.getString("content"));

                // adding Billionaire to listItemElement array
                listItemElementList.add(listItemElement);
            } catch (JSONException e) {
                // Something went wrong!
            }
        }*/
        adapter.notifyDataSetChanged();
        //  listView.invalidateViews();

    }

    public void ReadBtn() {
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("favoriteAuthors.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();

            listFreshArticles(s.substring(0,s.length()-1), "Favorites");
            //Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
