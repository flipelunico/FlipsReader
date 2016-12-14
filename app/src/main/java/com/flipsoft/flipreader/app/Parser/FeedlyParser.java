package com.flipsoft.flipreader.app.Parser;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flipsoft.flipreader.app.DB.FeedlyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Flipelunico on 11-10-16.
 */

public class FeedlyParser {

        private String CATEGORIES_URL = "http://cloud.feedly.com/v3/categories";
        private String SUBSCRIPTIONS_URL = "http://cloud.feedly.com/v3/subscriptions";
		private String ENTRIES_URL = "http://cloud.feedly.com/v3/streams/contents?streamId=user/45572cdc-c7de-425f-bc9a-11e08b224fab/category/Android&count=5";
        private String Token = "OAuth A1dTZrLPu-9ewUD2aA-y8aFN63JkzMmyS5F50EE1Yw7uFD1QZMwOrTabzDV8Td88lKVhpJcJ4fKkC5URvEXOUIkRIfbNu58ATXi5645sUhv2mJ7JZEJTnQB7CWNe1Z-LViL7LDoiy3veVh_J-kdy_wWv_7gSCD1WWMHw6miFt4e0GEC3re6s7s6OxqwtBHq2wh7VBJOiUhwDzG0aV4xfvX9LvNUs:feedlydev";
        private Context mContext;
        private String mContinuation;
        /*
        Instancia singleton
        */
        private static FeedlyParser singleton;

        public FeedlyParser(Context context){
                this.mContext = context;
        }

        public  FeedlyParser(){
            this.listener = null;
        }

        /**
         * Retorna la instancia unica del singleton
         * @param context contexto donde se ejecutarán las peticiones
         * @return Instancia
         */
        public static synchronized FeedlyParser getInstance(Context context) {
            if (singleton == null) {
                singleton = new FeedlyParser(context.getApplicationContext());
            }
            return singleton;
        }

        public interface ParserListener {

             public void onParserReady(String continuation);

        }

        private ParserListener listener;

        public void setCustomParserListener(ParserListener listener) {
            this.listener = listener;
        }

    /**
     * Obtiene categorias desde la cuenta de feedly
      */
    public void get_categories(){

            final List<Category> categories = new ArrayList<>();

            JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                    CATEGORIES_URL, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d("Flipelunico", "Obteniendo Categorias");
                    try {
                        // Parsing json array response
                        // loop through each json object

                        for (int i = 0; i < response.length(); i++) {

                            JSONObject person = (JSONObject) response.get(i);

                            String id = person.getString("id");
                            String label = person.getString("label");

                            Category c = new Category();
                            c.set_id(id);
                            c.set_label(label);

                            categories.add(c);

                        }



                    } catch (JSONException e) {
                        Log.i("Flipelunico","Error!!!: " + e.toString());
                        e.printStackTrace();

                    }

                    FeedlyDB.getInstance(mContext).syncCATEGORIES(categories);

                    //hidepDialog();
                }
              }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Flipelunico", "Error: " + error.getMessage());
                    Toast.makeText(mContext,
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", Token);
                    return headers;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            //requestQueue.add(stringRequest);
            requestQueue.add(jsonArrReq);
        }

        public void get_subscriptions(){



            final List<Subscription> subscriptions = new ArrayList<>();

        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                SUBSCRIPTIONS_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Flipelunico", "Obteniendo subscripciones");
                String category_id = "";
                String category_label = "";

                try {
                    // Parsing json array response
                    // loop through each json object

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject person = (JSONObject) response.get(i);

                        String id = person.getString("id");
                        String title = person.getString("title");
                        String website = person.getString("website");

                        JSONArray JSONcategories = person.getJSONArray("categories");


                        for (int z = 0; z < JSONcategories.length(); z++) {
                            JSONObject categories = JSONcategories.getJSONObject(z);
                            category_id = categories.getString("id");
                            category_label = categories.getString("label");
                        }

                        String updated = person.getString("updated");

                        Subscription s = new Subscription();
                        s.set_id(id);
                        s.set_title(title);
                        s.set_website(website);
                        s.set_category_id(category_id);
                        s.set_category_label(category_label);
                        s.set_updated(updated);

                        subscriptions.add(s);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                }

                FeedlyDB.getInstance(mContext).syncSUBSCRIPTIONS(subscriptions);

                //hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Flipelunico", "Error: " + error.getMessage());
                Toast.makeText(mContext,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Token);
                return headers;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        //requestQueue.add(stringRequest);
        requestQueue.add(jsonArrReq);
    }



    /**
     *  Obtiene feeds desde feedly
      */
    public void get_entries(){
        String result = getEntriesRecall("");
        //while (result != ""){
        //    result = getEntriesRecall(result);
        //}

    }
    private String getEntriesRecall(final String continuation){

        final List<Entry> entries = new ArrayList<>();

        mContinuation = continuation;

        JsonObjectRequest jsonArrReq = new JsonObjectRequest(Request.Method.GET,
                ENTRIES_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Flipelunico", "Obteniendo entradas");

                String id = "";
                String title = "";
                String content = "";
                String summary = "";
                String author = "";
                String crawled = "";
                String recrawled = "";
                String published = "";
                String updated = "";
                String alternate_href = "";
                String origin_title = "";
                String origin_htmlurl = "";
                String visual_url = "";
                String visual_height = "";
                String visual_width = "";
                String unread = "";


                id = getValue(response,"id");
                updated =getValue(response,"updated");
                mContinuation = getValue(response,"continuation");
                //Log.i("Flipelunico","responseCont: " + mContinuation);

                JSONArray items = null;

                try {
                    items = response.getJSONArray("items");
                }catch (JSONException e){
                    //
                }

                for (int z = 0; z < items.length(); z++) {

                    JSONObject item = null;
                    try {
                        item = items.getJSONObject(z);
                    } catch (JSONException e){
                        //
                    }

                    id = getValue(item,"id");
                    title = getValue(item,"title");
                    content = getValue(item,"content");
                    summary = getValue(item,"summary");
                    author = getValue(item,"author");
                    published = getValue(item,"published");
                    unread = getValue(item,"unread");

                    JSONObject origin = null;
                    try {
                        origin = item.getJSONObject("origin");
                    } catch (JSONException e){
                        //
                    }

                    origin_title = getValue(origin,"title");
                    origin_htmlurl = getValue(origin,"htmlUrl");

                    //TODO: faltan gets...flojera

                    Entry e = new Entry();
                    e.set_id(id);
                    e.set_title(title);
                    e.set_content(content);
                    e.set_summary(summary);
                    e.set_author(author);
                    e.set_crawled(crawled);
                    e.set_recrawled(recrawled);
                    e.set_published(published);
                    e.set_updated(updated);
                    e.set_alternate_href(alternate_href);
                    e.set_origin_title(origin_title);
                    e.set_origin_htmlurl(origin_htmlurl);
                    e.set_visual_url(visual_url);
                    e.set_visual_height(visual_height);
                    e.set_visual_width(visual_width);
                    e.set_unread(unread);

                    if (z == items.length() -1){
                        e.set_continuation(mContinuation);
                        //Log.i("Flipelunico","Cont2: " + mContinuation);
                    }
                    entries.add(e);
                }


                FeedlyDB.getInstance(mContext).syncENTRIES(entries);
                //hidepDialog();

                Log.d("Flipelunico", "Obteniendo entradas fin");

                if (listener != null){
                    listener.onParserReady(mContinuation);
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Flipelunico", "Error: " + error.getMessage());

                 if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(mContext,
                            "Error de coneccion a internet",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    //TODO
                } else if (error instanceof NetworkError) {
                    //TODO
                } else if (error instanceof ParseError) {
                    //TODO
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                //TODO: Agregar continuation para realizar rellamada
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Token);
                headers.put("continuation", mContinuation);
                return headers;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        //requestQueue.add(stringRequest);
        requestQueue.add(jsonArrReq);
        Log.i("Flipelunico","Cont: " + mContinuation);
        return mContinuation;
    }

        private String getValue(JSONObject object,String name) {
        String resp;
        try {
            resp = object.getString(name);
        } catch (JSONException e){
            resp = "";
        }
        return resp;

    }
}
