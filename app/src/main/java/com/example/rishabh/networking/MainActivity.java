package com.example.rishabh.networking;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTask myTask = new MyTask();
        myTask.execute("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=7bf504b0af4945dab09b4b62f65fd695");

    }



    class MyTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
//           make network call here
            String currentUrl = strings[0];
            try {
                URL url = new URL(currentUrl);

                //Open a new Connection using the URL
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                //Store the contents of the web-page as a Stream
                InputStream inputStream = httpURLConnection.getInputStream();

                //Create a Scanner from the Stream to get data in a human readable form
                Scanner scanner = new Scanner(inputStream);

                //Tells the scanner to read the file from the very start to the very end of file
                scanner.useDelimiter("\\A");

                String result = "";

                if (scanner.hasNext()) {
                    //Read the entire content of scanner in a go, otherwise scanner reads individual bytes one by one
                    result = scanner.next();
                }
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";

        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
        Response response = convertJsonToResponse(s);
        ArrayList<Article> articleArrayList = response.getArticles();
            rv = findViewById(R.id.recyclerView);
            LinearLayoutManager llm =new LinearLayoutManager(getBaseContext());
            rv.setLayoutManager(llm);
            NewsAdapter newsAdapter = new NewsAdapter(getBaseContext(),articleArrayList);
            rv.setAdapter(newsAdapter);


        }
    }

    @Nullable
    private Response convertJsonToResponse(String json)
    {

        try {
            JSONObject jsonObject = new JSONObject(json);

            String status = jsonObject.getString("status");
            Integer totalResults = jsonObject.getInt("totalResults");

            JSONArray jsonArray = jsonObject.getJSONArray("articles");

            ArrayList<Article> articleArrayList = new ArrayList<>();

            for(int i=0;i< jsonArray.length(); i++)
            {
                JSONObject articleObject = jsonArray.getJSONObject(i);
                String author = articleObject.getString("author");
                String title = articleObject.getString("title");
                String desc = articleObject.getString("description");
                String url = articleObject.getString("url");
                String imageUrl = articleObject.getString("urlToImage");
                String published = articleObject.getString("publishedAt");

                Article article = new Article(author,title,desc ,url,imageUrl,published);
                articleArrayList.add(article);
            }

            Response response = new Response(status,totalResults,articleArrayList);

            return response;


        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;


    }

}
