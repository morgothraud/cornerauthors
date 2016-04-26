package com.example.eray.customlistview;


/*
   mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
 */

        import android.app.Activity;
        import android.app.ActivityOptions;
        import android.content.Context;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.content.res.Resources;
        import android.content.res.TypedArray;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.v4.app.ActionBarDrawerToggle;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarActivity;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.Toast;

        import java.io.FileOutputStream;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

/**
 * @author dipenp
 *
 * This activity will add Navigation Drawer for our application and all the code related to navigation drawer.
 * We are going to extend all our other activites from this BaseActivity so that every activity will have Navigation Drawer in it.
 * This activity layout contain one frame layout in which we will add our child activity layout.
 */
public class BaseActivity extends ActionBarActivity {

    protected ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private List<HashMap<String,String>> mList ;
    private SimpleAdapter mAdapter;
    protected static int position;
    protected FrameLayout frameLayout;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private static boolean isLaunch = true;
    Context context;
    final private String TEXT = "text";
    final private String ICON = "icon";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        context= this;
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }
    String[] osArray = { "Favori Yazarlar", "Tüm Yazarlar", "Gazeteler", "Kaydedilen Yazılar" };
    private void addDrawerItems() {

        mList = new ArrayList<HashMap<String,String>>();

        int[] mIcons = new int[]{
                R.drawable.ic_person_white_24dp,
                R.drawable.ic_format_list_bulleted_white_24dp,
                R.drawable.ic_newspaper_white_24dp,
                R.drawable.ic_folder_download_white_24dp,
        };

        for(int i=0;i<mIcons.length;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(TEXT, osArray[i]);
            hm.put(ICON, Integer.toString(mIcons[i]) );
            mList.add(hm);
        }

        String[] from = { TEXT,ICON };
        int[] to = { R.id.tt , R.id.drawer_item_icons };
        mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_list_item, from, to);
       // mAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item,R.id.tt, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openActivity(position);
            }
        });



    }



    protected void openActivity(int position) {
        /**
         * We can set title & itemChecked here but as this BaseActivity is parent for other activity,
         * So whenever any activity is going to launch this BaseActivity is also going to be called and
         * it will reset this value because of initialization in onCreate method.
         * So that we are setting this in child activity.
         */
		mDrawerList.setItemChecked(position, true);
        getSupportActionBar().setTitle(osArray[position]);

        //   mDrawerLayout.closeDrawer(mDrawerList);
        BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.
        //  mDrawerLayout.closeDrawer(mDrawerList);
        switch (position) {
            case 0:
                Log.d("test", "entered");
                Intent i = new Intent(this,FavoriteAuthorsActivity.class);
                startActivity(i);
                this.finish();
                break;
            case 1:
                Log.d("test", "entered");
                 i = new Intent(this,MainActivity.class);
                startActivity(i);
                this.finish();
                break;
            case 2:
                Log.d("test", "entered");
                i = new Intent(this,ListNewsPapersActivity.class);
                startActivity(i);
                this.finish();
                break;
            case 3:
                Log.d("test","entered");
                i = new Intent(this,SavedArticlesActivity.class);
                startActivity(i);
                this.finish();
                break;
            default:
                break;
        }

        //   Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
    }

    private void setupDrawer() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerList.bringToFront();
                mDrawerLayout.requestLayout();
                getSupportActionBar().setTitle("Corner Authors");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(isLaunch){
            isLaunch = false;
            openActivity(0);
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}


