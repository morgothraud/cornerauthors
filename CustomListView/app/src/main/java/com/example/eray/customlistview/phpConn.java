package com.example.eray.customlistview;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by macadmin on 08/03/16.
 */
public class phpConn extends AsyncTask<String,Void,String>{
   // private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 0;
    static String json="";
    static JSONObject jObj = null;
    static JSONObject jArr=null;
    ArrayList<JSONObject> jsonObjects;

    public phpConn(Context context,int flag) {
        this.context = context;
     //   this.statusField = statusField;
      //  this.roleField = roleField;
        byGetOrPost = flag;
        jsonObjects = new ArrayList<>();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String link = "http://koseyazari.hol.es/index.php?requestCode=mainContents";
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line="";

            while ((line = in.readLine()) != null) {
                sb.append(line);
                Log.d("line",line);
                break;
            }
            in.close();
            json=sb.toString();

            return json;


        }catch(Exception e){
            Log.d("ex",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        try {
            jArr = new JSONObject(result);
          //  for(int i=0; i<jArr.length(); i++){
                jsonObjects.add(jArr);
                Log.d("ekle","jsonObjects'e eklendi");
        //    }

           // jObj = jArr.getJSONObject(0);
           // JSONObject json_article_info = jObj.getJSONObject("article");
           // String articleID = json_article_info.getString("articleID");
           // String header = json_article_info.getString("header");
           // String author = json_article_info.getString("author");
           // String date = json_article_info.getString("date");
          //  this.statusField.setText(header);
          //  this.roleField.setText(header);
        } catch (Exception e) {
            Log.d("exception",e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<JSONObject> getJsonObjecstList(){
        return this.jsonObjects;
    }
}
