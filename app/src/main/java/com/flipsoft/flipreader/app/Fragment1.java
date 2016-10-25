package com.flipsoft.flipreader.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.flipsoft.flipreader.app.Parser.MyTagHandler;
import com.flipsoft.flipreader.app.Parser.RssTagHandler;
import com.flipsoft.flipreader.app.Parser.URLImageParser;

/**
 * Created by Flipelunico on 18-10-16.
 */

public class Fragment1 extends Fragment {

    private static final String ARG_ID = "AnimalFragment:id";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1, container, false);
        TextView feed_title = (TextView) view.findViewById(R.id.feed_title);
        TextView feed_content = (TextView) view.findViewById(R.id.feed_content);

        String mime = "text/html";
        String encoding = "utf-8";

        //WebView myWebView = (WebView) view.findViewById(R.id.myWebView);
        //myWebView.getSettings().setJavaScriptEnabled(true);


        TextView feed_author =  (TextView) view.findViewById(R.id.feed_author);

        final Bundle args = getArguments();

        if (args != null) {
            String title = args.getString("title");
            String content = args.getString("content");
            String author = args.getString("author");
            String origin_title = args.getString("origin_title");
            //mImage.setImageResource(getImageDrawable(getActivity(), position));
            feed_title.setText(title);
            //myWebView.loadDataWithBaseURL(null, content, mime, encoding, null);

            String textHTML = content.replaceAll("\\\\n","<br>");
            String textHTML2 = textHTML.replaceAll("\\\\","");
            //feed_content.setText(Html.fromHtml(textHTML));
            Log.i("Flipelunico","HTML : " + textHTML2);
            feed_content.setText(Html.fromHtml(textHTML2, new URLImageParser(feed_content, getContext()), new MyTagHandler()));
            //feed_content.setText();
            feed_author.setText(origin_title + " by " + author);
        }

        return view;
    }
}
