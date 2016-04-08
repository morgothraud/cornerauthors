package com.example.eray.customlistview;


/*
   mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
 */

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.os.Bundle;
        import android.support.v4.app.ActionBarDrawerToggle;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarActivity;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.FrameLayout;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.io.FileOutputStream;
        import java.util.ArrayList;
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
    private ArrayAdapter<String> mAdapter;
    protected static int position;
    protected FrameLayout frameLayout;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private static boolean isLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    private void addDrawerItems() {
        String[] osArray = { "Favori Yazarlar", "Tüm Yazarlar", "Gazeteler", "Kaydedilen Yazılar" };
        mAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, osArray);
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
//		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);

        //   mDrawerLayout.closeDrawer(mDrawerList);
        BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.
        //  mDrawerLayout.closeDrawer(mDrawerList);
        switch (position) {
            case 0:
                Log.d("test", "entered");
                startActivity(new Intent(this, FavoriteAuthorsActivity.class));
                break;
            case 1:
                Log.d("test", "entered");
                startActivity(new Intent(this, MainActivity.class));

                break;
            case 2:
                Log.d("test", "entered");
                // startActivity(new Intent(this, Item3Activity.class));
                startActivity(new Intent(this, ListNewsPapersActivity.class));
                break;
            case 3:
                Log.d("test","entered");
                startActivity(new Intent(this, SavedArticlesActivity.class));
                //  startActivity(new Intent(this, Item4Activity.class));
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
              //  getSupportActionBar().setTitle("Navigation!");
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


