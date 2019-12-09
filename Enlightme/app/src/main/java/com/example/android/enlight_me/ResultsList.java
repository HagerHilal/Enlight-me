package com.example.android.enlight_me;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsList extends AppCompatActivity {

    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_list);
        new booklist().execute();
    }



    public class booklist extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            Intent intent = getIntent();
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(message);
                JSONArray items = jsonObj.getJSONArray("items");
                //ArrayList<JSONObject> list = new ArrayList<JSONObject>();
                JSONObject [] availableitems = new JSONObject[items.length()];
                int j = 0 ;
                if (items != null) {
                    int len = items.length();
                    for (int i=0;i<len;i++){
                        JSONObject  item1 = items.getJSONObject(i);
                        JSONObject accessInfo = item1.getJSONObject("accessInfo");
                        JSONObject pdf = accessInfo.getJSONObject("pdf");
                        boolean isavailabe = pdf.getBoolean("isAvailable");
                        if ( isavailabe == true) {
                            availableitems[j] = item1;
                            j++;
                        }
                    }
                }
                JSONObject [] visibledata = new JSONObject[j];
                String [] visible = new String[j];
                for ( int i = 0 ; i < j ; i++){
                    JSONObject item = items.getJSONObject(i);
                    JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                    String title = volumeInfo.getString("title");
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    String previewLink = volumeInfo.getString("previewLink");
                    JSONObject visibleitem = new JSONObject() ;
                    visibleitem.accumulate(  "title", title );
                    visibleitem.accumulate("authers" , authors);
                    visibleitem.accumulate("preview" , previewLink);
                    visibledata[i] = visibleitem ;

                    // String s = String.format("title : %s\nAuthers : %s\npreviewLink : %s", title, authors, Html.fromHtml("<a href = "+ previewLink+ ">" +previewLink +"</a>" )) ;
                    String s =  "title : " + title + "\n" + "Authers : " +  authors + "\n" + Html.fromHtml("<a href = "+ previewLink+ ">" +previewLink +"</a>") ;

                    visible[i] = s ;
                }







                adapter = new ArrayAdapter<String>(ResultsList.this, R.layout.activity_listview, visible);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";
        }

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String result) {
            ListView listView = (ListView) findViewById(R.id.book_list);
            listView.setAdapter(adapter);

        }
    }


}