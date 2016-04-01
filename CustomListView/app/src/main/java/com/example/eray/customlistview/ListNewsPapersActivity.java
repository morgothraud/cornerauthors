package com.example.eray.customlistview;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ListNewsPapersActivity extends BaseActivity {
    ListView newsListView;
    List<String> images = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.newspapers_list);
         getLayoutInflater().inflate(R.layout.newspapers_list, frameLayout);

        Context context = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.newspapers_list, frameLayout);
        newsListView = (ListView) view.findViewById(R.id.newsPaperList);
       // setTitle(listArray[position]);

        mDrawerList.setItemChecked(position, true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        createsNewPaperListMenu();
    }

    public void createsNewPaperListMenu(){

        images.add("http://www.mathandchessturkey.com/images/cumhuriyet.jpg");
        images.add("http://jani.com.tr/wp-content/uploads/2015/03/hurriyet_gazetesi_logo.jpg");
        images.add("https://upload.wikimedia.org/wikipedia/tr/archive/9/90/20130126180709!Milliyet_logosu.png");
        images.add("http://images.all-free-download.com/images/graphicthumb/posta_140341.jpg");
     //   images.add("http://uxturkiye.co/wp-content/uploads/2014/01/radikal-logo.jpg");
     //   images.add("http://megaholdings.co/wp-content/uploads/2014/11/sabah-logo.jpg");
        images.add("https://upload.wikimedia.org/wikipedia/tr/1/14/Sozculogo248x90.png");
       // images.add("http://s.hurriyet.com.tr/static/images/hurriyet/hurriyet-logo.png");
      //  newsListView = (ListView)findViewById(R.id.newsPaperList);
         newsListView.setAdapter(new ImageAdapter(this, R.layout.list_item, images));
    }

}
