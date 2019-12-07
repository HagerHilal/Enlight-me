package com.example.android.enlight_me;
import com.example.android.enlight_me.NetworkUtils;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        bookname = (EditText) (findViewById(R.id.search));
        results = (TextView) (findViewById(R.id.results));
        //url = findViewById(R.id.url);
        button = findViewById(R.id.searchb);
        button.setOnClickListener(this);

        // getWeather();
    }

    private void getBook(){
        String cityname = bookname.getText().toString();
        URL urlcreated = NetworkUtils.buildUrl(cityname);
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

                results.setText(result);
                url.setCursorVisible(false);
            }
        }

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.button:
                getBook();
        }
    }
}
