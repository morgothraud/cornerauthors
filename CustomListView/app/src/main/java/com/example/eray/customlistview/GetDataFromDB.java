package com.example.eray.customlistview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class GetDataFromDB extends AppCompatActivity {
    String author="nuri";
    String date="erdogan";
    TextView gorsel1;
    TextView gorsel2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // gorsel1 = (TextView) findViewById(R.id.icerik1);
      //  gorsel2 = (TextView) findViewById(R.id.icerik2);




    }



}
