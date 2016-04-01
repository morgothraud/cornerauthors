package com.example.eray.customlistview;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.swipe.SwipeLayout;


import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private MainActivity activity;
    private LayoutInflater inflater;
    private List<ListItemElement> billionairesItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(MainActivity activity, List<ListItemElement> billionairesItems) {
        this.activity = activity;
        this.billionairesItems = billionairesItems;
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

    private View.OnClickListener onEditListener(final int position, final ViewHolder holder) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(position, holder);



            }
        };
    }

    /**
     * Editting confirm dialog
     * @param position
     * @param holder
     */
    private void showEditDialog(final int position, final ViewHolder holder) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setTitle("EDIT ELEMENT");
        final EditText input = new EditText(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
       // input.setText(friends.get(position));
        input.setLayoutParams(lp);
        alertDialogBuilder.setView(input);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result edit text
                               // friends.set(position, input.getText().toString().trim());

                                //notify data set changed
                                activity.updateAdapter();
                                holder.swipeLayout.close();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog and show it
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private View.OnClickListener onDeleteListener(final int position, final ViewHolder holder) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //friends.remove(position);

                holder.swipeLayout.close();
                activity.updateAdapter();
            }
        };
    }

    private class ViewHolder {
        private TextView name;
        private View btnDown;
        private View btnStar;
        private SwipeLayout swipeLayout;

        public ViewHolder(View v) {
            swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe_layout);
            btnStar = v.findViewById(R.id.btnStar);
            btnDown = v.findViewById(R.id.btnDown);
            name = (TextView) v.findViewById(R.id.name);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        }
    }
}
