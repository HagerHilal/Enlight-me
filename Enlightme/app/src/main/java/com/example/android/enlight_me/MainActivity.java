package com.example.android.enlight_me;
import com.example.android.enlight_me.NetworkUtils;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;


/*

327098358281-u6f2gb56f3i38uhvrnnvdbk1nik800gm.apps.googleusercontent.com
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    EditText bookname ;
    TextView results;
    TextView url ;
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO (29) Use findViewById to get a reference to mSearchBoxEditText

        bookname =  findViewById(R.id.searchE);
        results =  findViewById(R.id.results);
        url = findViewById(R.id.url);
        button = findViewById(R.id.searchb);
        button.setOnClickListener(this);
        getBook();
    }

    private void getBook(){
        String book = bookname.getText().toString();
        URL urlcreated = NetworkUtils.buildUrl(book);
        url.setText(urlcreated.toString());
        new booksearch().execute(urlcreated);
    }

    public class booksearch extends AsyncTask<URL, Void, String> {

        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String result = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.equals("")) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(result);
                    JSONArray items = jsonObj.getJSONArray("items");
                    JSONObject  item1 = items.getJSONObject(0);
                    JSONObject accessInfo = item1.getJSONObject("accessInfo");
                    JSONObject pdf = accessInfo.getJSONObject("pdf");
                    String webReaderLink = accessInfo.getString("webReaderLink");
                    String s = "pdf info : " + pdf + "\n" + "read online :" + "\n" + webReaderLink ;
                    results.setText(s);
                    url.setCursorVisible(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    results.setText("NOT FOUND");
                    url.setCursorVisible(false);
                }
            }
        }
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.searchb:
                getBook();
        }
    }
}
