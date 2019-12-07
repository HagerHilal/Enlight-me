package com.example.android.enlight_me;

//package com.example.android.datafrominternet.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String bookapi =
            "GET https://www.googleapis.com/books/v1/volumes" ;
    final static String PARAM_QUERY = "q";

    public static URL buildUrl(String book) {
        Uri builtUri = Uri.parse(bookapi).buildUpon()
                .appendQueryParameter("key", "AIzaSyAmhi3tG8hjd_3NrGGV3yL0ggVjcHlzoGY")
                .appendQueryParameter("q", book)
                .appendQueryParameter("Content-Type" , "application/json")
                .appendQueryParameter("Authorization" , "327098358281-u6f2gb56f3i38uhvrnnvdbk1nik800gm.apps.googleusercontent.com")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}