package com.example.thesohankathait.localnewsreader.Utility;

import android.os.AsyncTask;
import android.util.Log;

import com.example.thesohankathait.localnewsreader.Adapter.TopNewsRecyclerAdapter;
import com.example.thesohankathait.localnewsreader.Fragment.TopNews;
import com.example.thesohankathait.localnewsreader.Model.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadTask extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result="";
        URL url;
        HttpURLConnection urlConnection;
            try {
                url=new URL(strings[0]);
                urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream=urlConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                int data=inputStreamReader.read();
                while(data!=-1){
                    result+=(char)data;
                    data=inputStreamReader.read();
                }
                Log.i("result",result);
            return result;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(result);
            if(jsonObject.getString("status").equals("ok")){
            JSONArray jsonArray=jsonObject.getJSONArray("articles");
            Log.i("length",""+jsonArray.length());
            for(int i=0;i<jsonArray.length();i++){
                News news=new News();
                news.setReporter(jsonArray.getJSONObject(i).getString("author"));
                news.setDateAndTime(jsonArray.getJSONObject(i).getString("publishedAt"));
                news.setDescription(jsonArray.getJSONObject(i).getString("description"));
                news.setTitle(jsonArray.getJSONObject(i).getString("title"));
                news.setImageURl(jsonArray.getJSONObject(i).getString("urlToImage"));
                TopNews.newsArrayList.add(news);
                if(TopNews.adapter!=null)
                    TopNews.adapter.notifyDataSetChanged();
                Log.i("name",jsonArray.getJSONObject(i).getString("author"));

            }
//                TopNewsRecyclerAdapter.newsList=newsArrayList;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }
}
