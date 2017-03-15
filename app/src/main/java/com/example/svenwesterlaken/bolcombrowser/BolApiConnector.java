package com.example.svenwesterlaken.bolcombrowser;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * Created by Sven Westerlaken on 14-3-2017.
 */

public class BolApiConnector extends AsyncTask<String, Void, String> {

    private ProductListener listener = null;

    public BolApiConnector(ProductListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        BufferedReader reader = null;
        String response = "";

        try {
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();

            if (!(connection instanceof HttpURLConnection)) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                response += line;
            }

        } catch (MalformedURLException e) {

            Log.e("UserAPI", e.getLocalizedMessage());
            return null;

        } catch (IOException e) {

            Log.e("UserAPI", e.getLocalizedMessage());
            return null;

        } catch (Exception e) {

            Log.e("UserAPI", e.getLocalizedMessage());
            return null;

        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return response;
    }

    protected void onPostExecute(String response) {
        if(response == null || response.equals("")) {
            return;
        }

        try {
            JSONObject json = new JSONObject(response);
            JSONArray results = json.getJSONArray("products");
            final int numberOfProducts = results.length();

            Log.i("JSON ARRAY", "" + numberOfProducts);

            for(int i=0; i < numberOfProducts; i++) {
                JSONObject product = results.getJSONObject(i);
                JSONArray images = product.getJSONArray("images");

                String title = product.getString("title");
                String specsTag = product.getString("specsTag");
                String summary = product.getString("summary");
                String longDescription = product.getString("longDescription");
                String smallImageUrl = images.getJSONObject(1).getString("url");
                String largeImageUrl = images.getJSONObject(3).getString("url");

                Product item = new Product(title, specsTag, summary, longDescription, smallImageUrl, largeImageUrl);
                listener.onProductAvailable(item);
            }

        } catch (JSONException e) {
            Log.e("JSON", e.getLocalizedMessage());
        }
    }

    public interface ProductListener {
        void onProductAvailable(Product p);
    }
}
