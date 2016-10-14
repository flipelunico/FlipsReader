package com.flipsoft.flipreader.app.Parser;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
		private String ENTRIES_URL = "http://cloud.feedly.com/v3/streams/contents?streamId=user/45572cdc-c7de-425f-bc9a-11e08b224fab/category/Android";
        private String Token = "OAuth AzbvRrDhWdB0seGrkhh3g2dz5W941-2XMNPRO7vVhlR9mDrcHSgdiK7Z8zuoNY71IndDUEejb221HhNd4qooDfx4It1dooI4_8vqcjaOZE5JIsYoFtu-jhnBVW7ii3b-KYK2wmG8aCNwsiMeQXpEBTcxLN67f9DIRmubfGKvaRZ4rGty1XmFszpBr8oFc7g1287IOOmS1peMxk5iaPi4V7JpDosQ:feedlydev";
        private Context context;

        public FeedlyParser(Context context){
                this.context = context;
        }

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

                    FeedlyDB.getInstance(context).syncCATEGORIES(categories);

                    //hidepDialog();
                }
              }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Flipelunico", "Error: " + error.getMessage());
                    Toast.makeText(context,
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


            RequestQueue requestQueue = Volley.newRequestQueue(context);
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

                FeedlyDB.getInstance(context).syncSUBSCRIPTIONS(subscriptions);

                //hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Flipelunico", "Error: " + error.getMessage());
                Toast.makeText(context,
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


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //requestQueue.add(stringRequest);
        requestQueue.add(jsonArrReq);
    }
         
		public void get_entries(){
            Log.d("Flipelunico", "Obteniendo entradas");
        final List<Entry> entries = new ArrayList<>();

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

                    //TODO: fatan gets...flojera

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

                    entries.add(e);
                }


                FeedlyDB.getInstance(context).syncENTRIES(entries);
                //hidepDialog();

                Log.d("Flipelunico", "Obteniendo entradas fin");
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Flipelunico", "Error: " + error.getMessage());
                Toast.makeText(context,
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


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //requestQueue.add(stringRequest);
        requestQueue.add(jsonArrReq);
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
