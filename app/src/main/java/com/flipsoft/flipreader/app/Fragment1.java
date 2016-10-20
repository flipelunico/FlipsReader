package com.flipsoft.flipreader.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public static Fragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);

        Fragment fragment = new Fragment1();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1, container, false);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        final Bundle args = getArguments();

        if (args != null) {
            int position = args.getInt(ARG_ID);
            //mImage.setImageResource(getImageDrawable(getActivity(), position));
            tv.setText("Prueba de contenido item: " + position);
        }

        return view;
    }
}
