package com.example.eray.customlistview;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;


/**
 * Created by Eray on 8.3.2016.
 */
public class ReadArticleActivity extends BaseActivity {

    TextView articleContent_TextView;
    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.read_article_layout);
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        articleContent_TextView = (TextView)findViewById(R.id.articleContent_TextView);
        articleContent_TextView.setMovementMethod(new ScrollingMovementMethod());
        printHtmlContent();
    }


    public void printHtmlContent(){
        try {
            String link = getUrlFromExtra();
            Log.d("cekilen_link", link);

            //HTMLParser parser = new SabahHTMLParser(getUrlFromExtra());
           // articleContent_TextView.setText(Html.fromHtml(parser.getArticleWithHTML()));
            articleContent_TextView.append(link);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public String getUrlFromExtra(){
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("link");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("link");
        }
        return newString;
    }

}
