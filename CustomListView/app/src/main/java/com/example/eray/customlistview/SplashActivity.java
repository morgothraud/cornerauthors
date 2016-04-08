package com.example.eray.customlistview;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;


public class SplashActivity extends BaseActivity {

   // Splash screen timer
   // private static int SPLASH_TIME_OUT = 3000;
    TextView articleContent_TextView;
    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //setContentView(R.layout.activity_splash);
        getLayoutInflater().inflate(R.layout.activity_splash, frameLayout);
        super.onCreate(savedInstanceState);
        mDrawerList.setItemChecked(position, true);
        this.savedInstanceState = savedInstanceState;
//        String message = getIntent().getStringExtra("title").toString();
     //   setTitle(message);
        Context context = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.activity_splash, frameLayout);
        articleContent_TextView = (TextView) view.findViewById(R.id.articleContent_TextView);

     //   articleContent_TextView = (TextView)findViewById(R.id.articleContent_TextView);
      //  articleContent_TextView.setText("Step One: blast egg");

        articleContent_TextView.setMovementMethod(new ScrollingMovementMethod());
        printHtmlContent();
/*        new Handler().postDelayed(new Runnable() {

			*//*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 *//*

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);*/
    }

    public void printHtmlContent(){
        try {
            String content = getContentFromExtra();
            Log.d("cekilen_link", content);

            //HTMLParser parser = new SabahHTMLParser(getUrlFromExtra());
             articleContent_TextView.setText(Html.fromHtml(content));
           // articleContent_TextView.append(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getContentFromExtra(){
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("content");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("content");
        }
        return newString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_search).setVisible(false);

        return true;
    }
}
