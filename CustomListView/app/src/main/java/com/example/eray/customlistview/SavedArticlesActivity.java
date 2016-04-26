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
public class SavedArticlesActivity extends BaseActivity {

    ListView newsListView;
    ListView listView;
    MainActivity m;
    Database db;
    public CustomListAdapter adapter;
    List<ListItemElement> listItemElementList = new ArrayList<ListItemElement>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.newspapers_list);
        getLayoutInflater().inflate(R.layout.saved_articles, frameLayout);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        // setTitle(listArray[position]);
        db = new Database(this);
        listView = (ListView) findViewById(R.id.listSaved);
        adapter = new CustomListAdapter(this, listItemElementList,this);
        listView.setAdapter(adapter);
        getSupportActionBar().setTitle(osArray[position]);
        listView.setEmptyView(findViewById(R.id.empty));
        mDrawerList.setItemChecked(position, true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  ReadBtn();
        listFromDB();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent splash = new Intent(SavedArticlesActivity.this, SplashActivity.class);
                splash.putExtra("content", listItemElementList.get(position).getContent());

                ListItemElement le = (ListItemElement)(listView.getItemAtPosition(position));
                splash.putExtra("title",le.getWorth());
                startActivityForResult(splash, 1);
            }
        });
    }

    protected void removeAnim(View rowView, final int positon) {
        final Animation animation = AnimationUtils.loadAnimation(
                SavedArticlesActivity.this, R.anim.slide_out);
        rowView.startAnimation(animation);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {

            public void run() {
                listItemElementList.remove(positon);
                adapter.notifyDataSetChanged();
            }
        }, 700);}

    public void deleteArticleFromDB(String tag) {
        db.deleteArticleWithId(Integer.parseInt(tag));
        db.getTableAsString("articles");
    }

    public void listFromDB () {

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Article> contacts = db.getAllArticles();

        for (Article cn : contacts) {
            String log = "Id: " + cn.getId() + cn.getHeader() + cn.getDate();
            // Writing Contacts to log
            Log.d("Name: ", log);
            ListItemElement listItemElement = new ListItemElement();
            listItemElement.setName(cn.getAuthor().getName());
            listItemElement.setThumbnailUrl(cn.getAuthor().getImageUrl());
            listItemElement.setWorth(cn.getHeader());
            listItemElement.setYear(cn.getDate());
            listItemElement.setSource(cn.getAuthor().getNewsPaper().name);
            listItemElement.setContent(cn.getContent());
            listItemElement.setTag(String.valueOf(cn.getId()));
            Log.d("ID", String.valueOf(cn.getId()));
            listItemElementList.add(listItemElement);
        }
        adapter.notifyDataSetChanged();
    }

    public void listFreshArticles(String file,final String newpaper) throws JSONException {

        String tmp = file;
        Log.d("tmp",tmp);
        file = "{ 'articles': [" + tmp + "]}";

       // Log.d("SIZE", file.length() + " " +  file.charAt(5234));
        JSONObject obj = new JSONObject(file);
        Log.w("URYİ",obj.toString());
        JSONArray jsonArray = obj.getJSONArray("articles");

        Log.w("Arrsize", String.valueOf(jsonArray.length()));
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject explrObject = jsonArray.getJSONObject(i);

            ListItemElement listItemElement = new ListItemElement();
            listItemElement.setName(explrObject.getString("authorName"));
            listItemElement.setThumbnailUrl(explrObject.getString("authorImage"));
            listItemElement.setWorth(explrObject.getString("articleName"));
            listItemElement.setYear(explrObject.getString("date"));
            listItemElement.setSource(newpaper);
            listItemElement.setContent(explrObject.getString("content"));
            listItemElement.setTag(String.valueOf(i));

            // adding Billionaire to listItemElement array
            listItemElementList.add(listItemElement);
          //  Log.e("Name",explrObject.getString("authorName"));

        }
        adapter.notifyDataSetChanged();
      //  listView.invalidateViews();

    }

    public Object getSelectedItem() {
       return listView.getSelectedItem();
    }


    public void ReadBtn() {
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("savedArticles.txt");
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


            Log.d("TEXTFILE", s);
            listFreshArticles(s.substring(0,s.length()-1), "Downloaded");


            //Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String read (String filename,Context c) throws IOException, FileNotFoundException {

        StringBuffer buffer = new StringBuffer();
        String line;
        FileInputStream fis = c.openFileInput(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        if (fis!=null) {
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n" );
            }
        }
        fis.close();
        Log.d("TEXT", buffer.toString());
        return buffer.toString();
    }

    public void setOnClickforListViewItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent splash = new Intent(SavedArticlesActivity.this,SplashActivity.class);
                splash.putExtra("content", listItemElementList.get(position).getContent());
                ListItemElement le = (ListItemElement)(listView.getItemAtPosition(position));
                splash.putExtra("title",le.getWorth());
                startActivityForResult(splash, 1);
            }
        });
    }

    public void updateAdapter() {
       // this.recreate();
        adapter.notifyDataSetChanged();
        // totalClassmates.setText("(" + friendsList.size() + ")"); //update total friends in list

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
