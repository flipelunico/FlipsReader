package com.flipsoft.flipreader.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flipsoft.flipreader.app.Parser.FeedlyParser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FeedlyParser fp = new FeedlyParser(this);
        fp.get_categories();




    }



}
