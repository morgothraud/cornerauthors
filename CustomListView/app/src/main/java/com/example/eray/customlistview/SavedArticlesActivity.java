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
public class SavedArticlesActivity extends BaseActivity {

    ListView newsListView;
    ListView listView;
    MainActivity m;
    private CustomListAdapter adapter;
    List<ListItemElement> listItemElementList = new ArrayList<ListItemElement>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.newspapers_list);
        getLayoutInflater().inflate(R.layout.saved_articles, frameLayout);
        // setTitle(listArray[position]);


        listView = (ListView) findViewById(R.id.listSaved);
        adapter = new CustomListAdapter(this, listItemElementList);
        listView.setAdapter(adapter);
        mDrawerList.setItemChecked(position, true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ReadBtn();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent splash = new Intent(SavedArticlesActivity.this, SplashActivity.class);
                splash.putExtra("content", listItemElementList.get(Integer.parseInt((String) view.getTag())).getContent());
                startActivityForResult(splash, 1);
            }
        });
    }

    public void listFreshArticles(String file,final String newpaper) throws JSONException {

        String tmp = file;
        file = "{ 'articles': [" + tmp + "]}";

        JSONObject obj = new JSONObject(file);
      //  JSONArray arr = new JSONArray();
       // obj.put("articles",arr);
//        Log.w("FILEEE", file.substring(file.length()-100,+file.length()));

     //   JSONObject elementOne = new JSONObject(file);

    //    arr.put(elementOne);

        //JSONObject jo = new JSONObject(file);
      //  ja.put(jo);
        Log.w("URYİ",obj.toString());
       // Log.w("OBJE", elementOne.toString().substring(elementOne.toString().length() - 200, +elementOne.toString().length()));
       // Log.w("FILEEE", arr.toString().substring(arr.length()-100,+arr.length()));
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
                splash.putExtra("content", listItemElementList.get(Integer.parseInt((String)view.getTag())).getContent());
                startActivityForResult(splash, 1);
            }
        });
    }
}
