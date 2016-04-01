package com.example.eray.customlistview;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListNewsPapersActivity extends BaseActivity {
    ListView newsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.newspapers_list);
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        createsNewPaperListMenu();
    }

    public void createsNewPaperListMenu(){
        List<String> images = new ArrayList<String>();
        images.add("http://www.mathandchessturkey.com/images/cumhuriyet.jpg");
        images.add("http://jani.com.tr/wp-content/uploads/2015/03/hurriyet_gazetesi_logo.jpg");
        images.add("https://upload.wikimedia.org/wikipedia/tr/archive/9/90/20130126180709!Milliyet_logosu.png");
        images.add("http://images.all-free-download.com/images/graphicthumb/posta_140341.jpg");
     //   images.add("http://uxturkiye.co/wp-content/uploads/2014/01/radikal-logo.jpg");
     //   images.add("http://megaholdings.co/wp-content/uploads/2014/11/sabah-logo.jpg");
        images.add("https://upload.wikimedia.org/wikipedia/tr/1/14/Sozculogo248x90.png");
       // images.add("http://s.hurriyet.com.tr/static/images/hurriyet/hurriyet-logo.png");
        newsListView = (ListView)findViewById(R.id.newsPaperList);
        newsListView.setAdapter(new ImageAdapter(this, R.layout.list_item, images));
    }

}
