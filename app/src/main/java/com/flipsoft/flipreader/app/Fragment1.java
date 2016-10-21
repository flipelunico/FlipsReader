package com.flipsoft.flipreader.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView feed_author =  (TextView) view.findViewById(R.id.feed_author);

        final Bundle args = getArguments();

        if (args != null) {
            String title = args.getString("title");
            String content = args.getString("content");
            String author = args.getString("author");
            String origin_title = args.getString("origin_title");
            //mImage.setImageResource(getImageDrawable(getActivity(), position));
            feed_title.setText(title);
            String textHTML = content.replaceAll("\\\\n","<br>");
            feed_content.setText(Html.fromHtml(textHTML));
            feed_author.setText(origin_title + " by " + author);
        }

        return view;
    }
}
