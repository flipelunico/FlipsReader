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
		private String ENTRIES_URL = "http://cloud.feedly.com/v3/subscriptions";
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
                    Log.d("", response.toString());
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
                Log.d("", response.toString());
                try {
                    // Parsing json array response
                    // loop through each json object

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject person = (JSONObject) response.get(i);

                        String id = person.getString("id");
                        String title = person.getString("title");
                        String website = person.getString("website");
                        String updated = person.getString("updated");
                        JSONObject categories = person.getJSONObject("categories");

                        String category_id = categories.getString("id");
                        String category_label = categories.getString("label");

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

        final List<Entry> entries = new ArrayList<>();

        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                ENTRIES_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("", response.toString());
                try {
                    // Parsing json array response
                    // loop through each json object

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject JSONEntry = (JSONObject) response.get(i);

                        String id = JSONEntry.getString("id");
                        String title = JSONEntry.getString("title");
                        JSONObject content = JSONEntry.getJSONObject("content");
						String content_content = JSONEntry.getString("content");
						
						JSONObject summary = JSONEntry.getJSONObject("summary");
						String summary_content = JSONEntry.getString("content");
						
						String author = JSONEntry.getString("author");
						String crawled = JSONEntry.getString("crawled");
						String recrawled = JSONEntry.getString("crawled");
						String published = JSONEntry.getString("published");
						String updated = JSONEntry.getString("updated");
						
						//FIX: array object
						JSONObject alternate = JSONEntry.getJSONObject("alternate");
						String alternate_href = alternate.getString("href");
						
						JSONObject origin = JSONEntry.getJSONObject("origin");
						String origin_title = origin.getString("title");
						String origin_htmurl = origin.getString("htmlurl");
						
						JSONObject visual = JSONEntry.getJSONObject("visual");
						String visual_url = visual.getString("url");
						String visual_height = visual.getString("height");
						String visual_width = visual.getString("width");
						
						String unread = JSONEntry.getString("unread");
						
                        Entry e = new Entry();
                        e.set_id(id);
                        e.set_title(title);
                        //TODO: falta

                        entries.add(s);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();

                }

                FeedlyDB.getInstance(context).syncENTRIES(entries);

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
}
