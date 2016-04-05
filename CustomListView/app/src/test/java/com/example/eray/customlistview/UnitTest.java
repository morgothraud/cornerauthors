package com.example.eray.customlistview;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowLog;

import java.util.List;

import XMLParser.XMLParser;
import com.example.macadmin.cornerauthors.MainActivity;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class UnitTest {

    // <editor-fold desc="Variables">
    private MainActivity activity;
    private Activity context;
    ListView listview;
    EditText search_EditText;
    ImageView author_icon,newspaper_icon;
    TextView author_name,article_title;
    ImageButton imageButton;
    Adapter adapter;
    XMLParser xmlParser;
    // </editor-fold">

    // <editor-fold desc="Setup Test Environment">
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        context = Robolectric.buildActivity(MainActivity.class).create().get();
        activity = Robolectric.setupActivity(MainActivity.class);
        listview = (ListView) activity.findViewById(R.id.listview); //getting the list layout xml
        search_EditText = (EditText) activity.findViewById(R.id.search_EditText);
        author_icon = (ImageView)activity.findViewById(R.id.icon);
        author_name = (TextView)activity.findViewById(R.id.secondLine);
        article_title = (TextView) activity.findViewById(R.id.firstLine);
        newspaper_icon = (ImageView)activity.findViewById(R.id.imageView);
        imageButton = (ImageButton) activity.findViewById(R.id.imageButton);
        xmlParser = new XMLParser(context);
    }

    // </editor-fold">

    // <editor-fold desc="Main Tests">

    @Test
    public void mainActivityIsNotNull() throws Exception {
        assertTrue(activity != null);
    }

    @Test
    public void mainActivityFinishingOnEmptyBundle() throws Exception{
        assertTrue(listview!=null);
    }

    @Test
    public void shouldFindListView()throws Exception{
       // View recycleView = View.inflate(new Activity(), R.layout.listitem, null);
       // ListAdapter viewHolder =  // Static method in adapter
        //recycleView.setTag(viewHolder);
       /// assertNotNull("ListView not found ", listview);
       // ShadowListView shadowListView = Shadows.shadowOf(listview); //we need to shadow the list view

       // shadowListView.populateItems();// will populate the adapter
       // ShadowLog.d("Checking the first country name in adapter " ,
         //       ((RowModel)listView.getAdapter().getItem(0)).getCountry());
        //assertTrue(listview.getAdapter().getCount() != 0);
        //assertTrue("Country Japan doesnt exist", "Japan".equals((listview.getAdapter().getItem(0))));
        //assertTrue(3==lstView.getChildCount());
    }

    @Test
    public void shouldListviewIsFilled(){
        //execute asynctask
        executeListview();
        // can run asserts on result now
        assertTrue(xmlParser.getSabah() != null);
    }

    // </editor-fold>

    // <editor-fold desc = "Parsing Tests">
    // <editor-fold desc="Author Name Tests">

    @Test
    public void shouldListviewHaveAuthorNameForEachItem(){
        executeListview();
        int counter = 0;
        //checking for each item has an author name.
        for (int i =0 ;i < xmlParser.getSabah().size();i++){
            if(xmlParser.getSabah().get(i).getAuthor() != null){
                counter++;
            }
        }
        assertTrue( counter == xmlParser.getSabah().size());
    }

    @Test
    public void shouldListviewIsAddingAuthorName(){
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getAuthor() != null);
    }

    @Test
    public void shouldListviewIsAuthorNameCorrect() {
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getAuthor().equalsIgnoreCase("AHMET ÇAKAR"));
    }

    // // </editor-fold>

    // <editor-fold desc="Category Tests">

    @Test
    public void shouldListviewHaveCategoryForEachItem(){
        executeListview();
        int counter = 0;
        //checking for each item has a category.
        for (int i =0 ;i < xmlParser.getSabah().size();i++){
            if(xmlParser.getSabah().get(i).getCategory() != null){
                counter++;
            }
        }
        assertTrue( counter == xmlParser.getSabah().size());
    }

    @Test
    public void shouldListviewIsAddingCategory(){
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getCategory() != null);
    }

    @Test
    public void shouldListviewIsCategoryCorrect() {
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getCategory().equalsIgnoreCase("http://www.sabah.com.tr/spor/yazarlar/cakar/2016/03/14/firat-aydinus-buyuk-tehlike"));
    }

    // </editor-fold>

    // <editor-fold desc = "Title Tests">

    @Test
    public void shouldListviewHaveTitleForEachItem(){
        executeListview();
        int counter = 0;
        //checking for each item has a category.
        for (int i =0 ;i < xmlParser.getSabah().size();i++){
            if(xmlParser.getSabah().get(i).getTitle() != null){
                counter++;
            }
        }
        assertTrue( counter == xmlParser.getSabah().size());
    }

    @Test
    public void shouldListviewIsAddingTitle(){
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getTitle() != null);
    }

    @Test
    public void shouldListviewIsTitleCorrect() {
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getTitle().equalsIgnoreCase("Fırat Aydınus büyük tehlike!"));
    }

    // </editor-fold>

    // <editor-fold desc="Description Tests">
    @Test
    public void shouldListviewHaveDescriptionForEachItem(){
        executeListview();
        int counter = 0;
        //checking for each item has a category.
        for (int i =0 ;i < xmlParser.getSabah().size();i++){
            if(xmlParser.getSabah().get(i).getDescription() != null){
                counter++;
            }
        }
        assertTrue(counter == xmlParser.getSabah().size());
    }

    @Test
    public void shouldListviewIsAddingDescription(){
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getDescription() != null);
    }
    //</editor-fold>

    // <editor-fold desc = "Date Tests">
    @Test
    public void shouldListviewHaveDateForEachItem(){
        executeListview();
        int counter = 0;
        //checking for each item has a category.
        for (int i =0 ;i < xmlParser.getSabah().size();i++){
            if(xmlParser.getSabah().get(i).getDate() != null){
                counter++;
            }
        }
        assertTrue( counter == xmlParser.getSabah().size());
    }

    @Test
    public void shouldListviewIsAddingDate(){
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getDate() != null);
    }

    @Test
    public void shouldListviewIsDateCorrect() {
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getDate().equalsIgnoreCase("Sun, 13 Mar 2016 23:09:52 GMT"));
    }
    // </editor-fold>

    // <editor-fold desc="Link Test">
    @Test
    public void shouldListviewHaveLinkForEachItem(){
        executeListview();
        int counter = 0;
        //checking for each item has a link.
        for (int i =0 ;i < xmlParser.getSabah().size();i++){
            if(xmlParser.getSabah().get(i).getLink() != null){
                counter++;
            }
        }
        assertTrue( counter == xmlParser.getSabah().size());
    }

    @Test
    public void shouldListviewIsAddingLink(){
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getLink() != null);
    }

    @Test
    public void shouldListviewIsLinkCorrect() {
        executeListview();
        assertTrue(xmlParser.getSabah().get(0).getLink().equalsIgnoreCase("http://www.sabah.com.tr/spor/yazarlar/cakar/2016/03/14/firat-aydinus-buyuk-tehlike"));
    }

    // </editor-fold>
    // </editor-fold>

    // <editor-fold desc="Methods">

    public void executeListview(){
        //start task
        xmlParser.execute();
        //wait for task code
        // Robolectric.runBackgroundTasks(); (pre 3.0)
        Robolectric.flushBackgroundThreadScheduler(); //from 3.0
    }

    // </editor-fold>


}
