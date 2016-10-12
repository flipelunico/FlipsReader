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


            /*
            StringRequest stringRequest = new StringRequest(CATEGORIES_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String salida = response;
                            Toast.makeText(context,"Respuesta OK",Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", Token);
                    return headers;
                }
            };*/

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            //requestQueue.add(stringRequest);
            requestQueue.add(jsonArrReq);
        }

        private void ParseCategories(String JSONString){

        try{
            //JSONArray elements = new JSONArray (response);
            JSONObject json=new JSONObject(JSONString);
            JSONArray elements = json.getJSONArray("items");

            Log.d("Flipelunico", "Elemenenti numero" +elements.length());

            for(int i = 0; i < elements.length(); i++){
                JSONObject c = elements.getJSONObject(i);
                // Storing each json item in variable

                String identifier = c.getString("id");
                String title = c.getString("title");
                String link = c.getString("originId");
                String data = c.getString("published");


                JSONObject summaryObj= c.getJSONObject("summary");
                String summary = summaryObj.getString("content");

                /*JSONObject contentObj= c.getJSONObject("content");
                String content = contentObj.getString("content");

                JSONObject sourceObj= c.getJSONObject("origin");
                String source = contentObj.getString("title");


                if (summary.length()==0 && content.length()!=0) summary=content;
                if (content.length()==0 && summary.length()!=0) content=summary;
                */
                String content = summary;

                //String image=this.getFirstImage(content);
                String image = "";

                /*RssItem newItem = new RssItem();

                newItem.setTitle(title);
                newItem.parserSetContent("",content);
                newItem.parserSetContent("",content);
                newItem.parserSetPubDate(fecha);


                feedList.add(newItem);*/

            }


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
