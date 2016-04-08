package com.example.eray.customlistview;

import android.app.Activity;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.swipe.SwipeLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
    private MaterialSearchView searchView;
    // Billionaires json url
   // private static final String url = "https://raw.githubusercontent.com/mobilesiri/Android-Custom-Listview-Using-Volley/master/richman.json";
    private static final String SabahUrl = "https://gist.githubusercontent.com/anonymous/7dae51e5c07f078a023b/raw/822a479bbacda6e72347aec4f286f039bc001ad2/blob.json";
    private static final String SabahRadikalUrl = "https://gist.githubusercontent.com/anonymous/3dd0f3d5898527d16020/raw/4373a6aa52596924b5184302b3676e5b5c21fd3e/blob.json";
    private static final String RadikalUrl = "https://gist.githubusercontent.com/anonymous/8b92250d390e693a6d7d/raw/3f830f62b6309ba865e0f80fd328a838dc69f142/blob.json";
    private static final String MilliyetUrl = "https://gist.githubusercontent.com/anonymous/98e7ef90369ed7973949/raw/d108a066f826b76d13177f109e65877d41d5950f/json_milliyet";
   private static final String CumhuriyetUrl = "https://gist.githubusercontent.com/anonymous/8a40bfde6c0f5ea0e58970cfb22b59f5/raw/3e022aad5119015622874b548b70d88f6e1ea1bd/cumh";
    private ProgressDialog pDialog;
    private List<ListItemElement> listItemElementList = new ArrayList<ListItemElement>();
    private ArrayList<ListItemElement> array_sort = new ArrayList<>();
    public ListView listView;
    private CustomListAdapter adapter;
    int textlength;
    TextView search_EditText;
    private SwipeLayout swipeLayout;

    private boolean isSearchOpened = false;
    private EditText edtSeach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    //    setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, listItemElementList);
        listView.setAdapter(adapter);
        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
       // setTitle(listArray[position]);

        pDialog = new ProgressDialog(this);
        pDialog.setCanceledOnTouchOutside(false);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
       // hidePDialog();

        // Creating volley request obj
        listFreshArticles(MilliyetUrl, "Milliyet");
        listFreshArticles(RadikalUrl,"Radikal");
        listFreshArticles(SabahUrl,"Sabah");
        listFreshArticles(CumhuriyetUrl,"Cumhuriyet");


        setOnClickforListViewItem();

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
      //  searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setSuggestions(getResources().getStringArray(R.array.suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(findViewById(R.id.drawer_layout), "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic


                search_EditText = (TextView) findViewById(R.id.search_src_text);
                search_EditText.setText(newText);
                search_EditText.addTextChangedListener(textWatcher);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
                getSupportActionBar().hide();
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                getSupportActionBar().show();
            }
        });

    }

    public void setOnClickforListViewItem( ){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent splash = new Intent(MainActivity.this,SplashActivity.class);
                splash.putExtra("content", listItemElementList.get(Integer.parseInt((String)view.getTag())).getContent());

                ListItemElement le = (ListItemElement)(listView.getItemAtPosition(position));
                splash.putExtra("title",le.getWorth());

                startActivityForResult(splash, 1);
            }
        });
    }

    public String editAuthorName(String s) throws UnsupportedEncodingException {
        String exception = "̇";
        String fin;
        s = s.toLowerCase();
        char[]c = s.toCharArray();

        for (int i = 0; i <c.length ; i++) {
            int j = c[i];
             Log.d("TYU",String.valueOf(c[i])+  j );
         //   Log.d("TYU2",String.valueOf(c[i]));
            if(j == 775) break;
          //  if(c[i]=='i') c[i]=((char) 105);
            if(i==0) c[i]=Character.toUpperCase(c[i]);
            if (c[i] == ' ') {c[i+1]=Character.toUpperCase(c[i+1]); continue;}

        }
        Log.d("EFD",String.valueOf('i'));
        return fin =  String.valueOf(c);

    }

    public void listFreshArticles(String jsonUrl,final String newpaper){
        JsonArrayRequest billionaireReq = new JsonArrayRequest(jsonUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                ListItemElement listItemElement = new ListItemElement();
                                listItemElement.setName(editAuthorName(obj.getString("authorName")));
                                listItemElement.setThumbnailUrl(obj.getString("authorImage"));
                                listItemElement.setWorth(obj.getString("articleName"));
                                listItemElement.setYear(obj.getString("date"));
                                listItemElement.setSource(newpaper);
                                listItemElement.setContent(obj.getString("content"));
                                listItemElement.setTag(String.valueOf(i));

                                // adding Billionaire to listItemElement array
                                listItemElementList.add(listItemElement);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(billionaireReq);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     //   super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
   /*     MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type something...");
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.DKGRAY);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            search_EditText = (TextView) searchPlate.findViewById(searchTextId);
            search_EditText.addTextChangedListener(textWatcher);
            if (search_EditText!=null) {
                search_EditText.setTextColor(Color.WHITE);
                search_EditText.setHintTextColor(Color.WHITE);
            }
        }*/

    }



    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            textlength = search_EditText.getText().length();
            array_sort.clear();
            for (int i = 0; i < adapter.getCount(); i++) {
                if (textlength <= ((ListItemElement)(adapter.getItem(i))).getName().length()) {
                    if (search_EditText.getText().toString().equalsIgnoreCase(
                            (String)
                                    ((ListItemElement)(adapter.getItem(i))).getName().subSequence(0,
                                            textlength))) {
                        array_sort.add(((ListItemElement)(adapter.getItem(i))));
                    }
                }
            }
            listView.setAdapter(new CustomListAdapter(MainActivity.this, array_sort));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();

        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    private void setSwipeViewFeatures() {
        //set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                Log.i(TAG, "onClose");
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                Log.i(TAG, "on swiping");
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {
                Log.i(TAG, "on start open");
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                Log.i(TAG, "the BottomView totally show");
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                Log.i(TAG, "the BottomView totally close");
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });
    }

    private void setListViewAdapter() {
        adapter = new CustomListAdapter(this,listItemElementList);
        listView.setAdapter(adapter);

       // totalClassmates.setText("(" + friendsList.size() + ")");
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged(); //update adapter
       // totalClassmates.setText("(" + friendsList.size() + ")"); //update total friends in list

    }


}
