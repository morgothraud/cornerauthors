package com.example.eray.customlistview;


import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.swipe.SwipeLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    boolean saved = false;
    boolean faved = false;
    boolean searched = false;
    boolean mained = false;
    boolean deleted = false;
    private MainActivity activity;
    private SearchActivity searchActivity;
    private SavedArticlesActivity savedActivity;
    private FavoriteAuthorsActivity favoritesActivity;
    private LayoutInflater inflater;
    private List<ListItemElement> billionairesItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Context savedActivityContext;
    private Context favedActivityContext;

    public CustomListAdapter(MainActivity activity, List<ListItemElement> billionairesItems) {
        this.activity = activity;
        this.billionairesItems = billionairesItems;
        mained=true;
    }

    public CustomListAdapter(SearchActivity activity, List<ListItemElement> billionairesItems) {
        this.searchActivity = activity;
        this.billionairesItems = billionairesItems;
        searched=true;

    }

    public CustomListAdapter(SavedArticlesActivity savedArticlesActivity, List<ListItemElement> listItemElementList,Context context) {
        this.savedActivity = savedArticlesActivity;
        this.billionairesItems = listItemElementList;
        saved=true;
        this.savedActivityContext =context;
    }

    public CustomListAdapter(FavoriteAuthorsActivity fav, List<ListItemElement> listItemElementList,Context context) {
        this.favoritesActivity = fav;
        this.billionairesItems = listItemElementList;
        faved=true;
        this.favedActivityContext = context;
    }

    @Override
    public int getCount() {
        return billionairesItems.size();
    }

    @Override
    public Object getItem(int location) {
        return billionairesItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(saved==true) {
            ViewHolder holder;
            if (inflater == null)
                inflater = (LayoutInflater) savedActivity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null){
                convertView = inflater.inflate(R.layout.saved_articles_list_item, null);
                holder = new ViewHolder(convertView);
                // set tag for holder
                convertView.setTag(holder);}
            else {
                // if holder created, get tag from view
                // holder = (ViewHolder) convertView.getTag();
                holder = new ViewHolder(convertView);
            }

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            NetworkImageView thumbNail = (NetworkImageView) convertView
                    .findViewById(R.id.thumbnail);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView worth = (TextView) convertView.findViewById(R.id.worth);
            TextView source = (TextView) convertView.findViewById(R.id.source);
            TextView year = (TextView) convertView.findViewById(R.id.inYear);

            // getting billionaires data for the row
            ListItemElement m = billionairesItems.get(position);

            // thumbnail image
            thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

            // name
            name.setText(m.getName());

            // Wealth Source
            source.setText(String.valueOf(m.getSource()));

            worth.setText(String.valueOf(m.getWorth()));

            // release year
            year.setText(String.valueOf(m.getYear()));

            convertView.setTag(String.valueOf(m.getTag()));

            //    holder.name.setText(getItem(position));

            //handling buttons event
            holder.btnDown.setOnClickListener(onSaveDelete(position, holder,convertView));

            return convertView;
        }

        if(faved==true) {
            ViewHolder holder;
            if (inflater == null)
                inflater = (LayoutInflater) favoritesActivity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null){
                convertView = inflater.inflate(R.layout.favorited_authors_list_item, null);
                holder = new ViewHolder(convertView);
                // set tag for holder
                convertView.setTag(holder);}
            else {
                // if holder created, get tag from view
                // holder = (ViewHolder) convertView.getTag();
                holder = new ViewHolder(convertView);
            }

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            NetworkImageView thumbNail = (NetworkImageView) convertView
                    .findViewById(R.id.thumbnail);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView worth = (TextView) convertView.findViewById(R.id.worth);
            TextView source = (TextView) convertView.findViewById(R.id.source);
            TextView year = (TextView) convertView.findViewById(R.id.inYear);

            // getting billionaires data for the row
            ListItemElement m = billionairesItems.get(position);

            // thumbnail image
            thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

            // name
            name.setText(m.getName());

            // Wealth Source
            source.setText(String.valueOf(m.getSource()));

            worth.setText(String.valueOf(m.getWorth()));

            // release year
            year.setText(String.valueOf(m.getYear()));

            convertView.setTag(String.valueOf(m.getTag()));

            //    holder.name.setText(getItem(position));

            //handling buttons event
            holder.btnDown.setOnClickListener(onFavDelete(position, holder,convertView));

            return convertView;
        }

        if(searched==true) {
            ViewHolder holder;
            if (inflater == null)
                inflater = (LayoutInflater) searchActivity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null){
                convertView = inflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder(convertView);
                // set tag for holder
                convertView.setTag(holder);}
            else {
                // if holder created, get tag from view
                // holder = (ViewHolder) convertView.getTag();
                holder = new ViewHolder(convertView);
            }

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            NetworkImageView thumbNail = (NetworkImageView) convertView
                    .findViewById(R.id.thumbnail);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView worth = (TextView) convertView.findViewById(R.id.worth);
            TextView source = (TextView) convertView.findViewById(R.id.source);
            TextView year = (TextView) convertView.findViewById(R.id.inYear);

            // getting billionaires data for the row
            ListItemElement m = billionairesItems.get(position);

            // thumbnail image
            thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

            // name
            name.setText(m.getName());

            // Wealth Source
            source.setText(String.valueOf(m.getSource()));


            worth.setText(String.valueOf(m.getWorth()));

            // release year
            year.setText(String.valueOf(m.getYear()));

            convertView.setTag(String.valueOf(m.getTag()));

            //    holder.name.setText(getItem(position));

            //handling buttons event
            holder.btnDown.setOnClickListener(onEditListener(position, holder));
            holder.btnStar.setOnClickListener(onDeleteListener(position, holder));

            return convertView;
        }

        ViewHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_item, null);
        holder = new ViewHolder(convertView);
        // set tag for holder
        convertView.setTag(holder);}
        else {
            // if holder created, get tag from view
           // holder = (ViewHolder) convertView.getTag();
            holder = new ViewHolder(convertView);
        }

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView worth = (TextView) convertView.findViewById(R.id.worth);
        TextView source = (TextView) convertView.findViewById(R.id.source);
        TextView year = (TextView) convertView.findViewById(R.id.inYear);

        // getting billionaires data for the row
        ListItemElement m = billionairesItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // name
        name.setText(m.getName());

        // Wealth Source
        source.setText(String.valueOf(m.getSource()));


        worth.setText(String.valueOf(m.getWorth()));

        // release year
        year.setText(String.valueOf(m.getYear()));

        convertView.setTag(String.valueOf(m.getTag()));

    //    holder.name.setText(getItem(position));

        //handling buttons event
        holder.btnDown.setOnClickListener(onEditListener(position, holder));
        holder.btnStar.setOnClickListener(onDeleteListener(position, holder));

        return convertView;
    }



    public String readJSON() throws JSONException {

        try {
            FileInputStream fileIn=activity.openFileInput("savedArticles.txt");
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

           return s;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private View.OnClickListener onEditListener(final int position, final ViewHolder holder) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     // showEditDialog(position, holder);

                activity.saveArticleToDB(position);

                //date article name authorName authorImage content

                    Toast.makeText(activity.getBaseContext(), "Article saved successfully!",
                            Toast.LENGTH_SHORT).show();
                    holder.swipeLayout.close();
                    activity.updateAdapter();

            }
        };

    }



    private View.OnClickListener onDeleteListener(final int position, final ViewHolder holder) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //friends.remove(position);
                //date article name authorName authorImage content

                activity.favoriteAuthorsToDB(position);
                //date article name authorName authorImage content

                Toast.makeText(activity.getBaseContext(), "Favorited",
                        Toast.LENGTH_SHORT).show();
                holder.swipeLayout.close();
                activity.updateAdapter();
            }
        };
    }


    private View.OnClickListener onSaveDelete(final int position, final ViewHolder holder, final View r) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //friends.remove(position);

                ListItemElement le = (ListItemElement) savedActivity.listView.getItemAtPosition(position);
                Log.d("TAG",le.getTag() + " - " + le.getName());
                if(savedActivityContext instanceof SavedArticlesActivity){
                    Log.d("in,","in");
                    ((SavedArticlesActivity) savedActivityContext).deleteArticleFromDB(le.getTag());
                }
                //date article name authorName authorImage content
                holder.swipeLayout.close();
                ((SavedArticlesActivity) savedActivityContext).updateAdapter();
                ((SavedArticlesActivity) savedActivityContext).removeAnim(r, position);

            }
        };
    }

    private View.OnClickListener onFavDelete(final int position, final ViewHolder holder, final View r) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //friends.remove(position);

                ListItemElement le = (ListItemElement) favoritesActivity.listView.getItemAtPosition(position);
                Log.d("TAG",le.getTag() + " - " + le.getName());
                if(favedActivityContext instanceof FavoriteAuthorsActivity){
                    Log.d("in,","in");
                    ((FavoriteAuthorsActivity) favedActivityContext).deleteArticleFromDB(le.getTag());
                }
                //date article name authorName authorImage content
                holder.swipeLayout.close();
                ((FavoriteAuthorsActivity) favedActivityContext).updateAdapter();
                ((FavoriteAuthorsActivity) favedActivityContext).removeAnim(r, position);

            }
        };
    }

    public static String replaceNull(String input) {
        return input.replace(",null","");
    }

    public static JSONArray asList(JSONArray ja) {
        int len = ja.length();
        JSONArray arr = new JSONArray();
        for (int i = 0; i < len; i++) {
            JSONObject obj = ja.optJSONObject(i);
            if (obj != null) {
                arr.put(obj);
            }
        }
        return arr;
    }

    private class ViewHolder {
        private TextView name;
        private View btnDown;
        private View btnStar;
        private SwipeLayout swipeLayout;

        public ViewHolder(View v) {
            swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe_layout);

            if(mained||searched) {
                btnStar = v.findViewById(R.id.btnStar);
                btnDown = v.findViewById(R.id.btnDown);
            }
            if(saved) btnDown = v.findViewById(R.id.btnDelete);
            if(faved) btnDown = v.findViewById(R.id.btnDeleteFav);

            name = (TextView) v.findViewById(R.id.name);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onClose(SwipeLayout layout) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (saved) {
                                savedActivity.listView.setEnabled(true);
                                savedActivity.setOnClickforListViewItem();
                            }
                            if (faved) {
                                favoritesActivity.listView.setEnabled(true);
                                favoritesActivity.setOnClickforListViewItem();
                            }
                            if (mained) {
                                activity.listView.setEnabled(true);
                                activity.setOnClickforListViewItem();
                            }
                            if (searched) {
                                searchActivity.listView.setEnabled(true);
                                searchActivity.setOnClickforListViewItem();
                            }

                        }
                    }, 300);

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                   // Log.i("TEST", "on swiping");
                    if(saved) {
                        savedActivity.listView.setEnabled(false);
                        savedActivity.listView.setOnItemClickListener(null);
                    }
                    if(faved) {
                        favoritesActivity.listView.setEnabled(false);
                        favoritesActivity.listView.setOnItemClickListener(null);}
                    if(mained){
                        activity.listView.setEnabled(false);
                        activity.listView.setOnItemClickListener(null);}
                    if(searched){
                        searchActivity.listView.setEnabled(false);
                        searchActivity.listView.setOnItemClickListener(null);}

                }

                @Override
                public void onStartOpen(SwipeLayout layout) {
                   // Log.i("TEST", "on start open");
                }

                @Override
                public void onOpen(SwipeLayout layout) {
                 //   Log.i("TEST", "the BottomView totally show");
                }

                @Override
                public void onStartClose(SwipeLayout layout) {
                 //   Log.i("", "the BottomView totally close");

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                    //when user's hand released.
                }
            });
        }
    }
}
