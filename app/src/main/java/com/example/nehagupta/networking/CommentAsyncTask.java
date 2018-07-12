package com.example.nehagupta.networking;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class CommentAsyncTask extends AsyncTask<String,Void,ArrayList<String>> {

   ArrayList<String> comments= new ArrayList<>();
    CommentDownloadListener listener;
    CommentAsyncTask(CommentDownloadListener listener)
    {
        this.listener=listener;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {

        String urlString=strings[0];
        try
        {
            URL url= new URL(urlString);
            HttpsURLConnection urlConnection=(HttpsURLConnection)url.openConnection();
            urlConnection.connect();
            InputStream inputStream=urlConnection.getInputStream();
            Scanner scanner=new Scanner(inputStream);
            String result="";
            while(scanner.hasNext())
            {
                result=result+scanner.next();
            }
            /*JSONObject rootobject=new JSONObject(result);
            JSONObject data=rootobject.getJSONObject("data");
            JSONArray courses=data.getJSONArray("courses");
            */
            JSONArray posts=new JSONArray(result);

            for(int i=0;i<posts.length();i++)
            {
                JSONObject postObject=posts.getJSONObject(i);
                String body=postObject.getString("body");
                comments.add(body);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return(comments);
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        listener.onDownload(strings);
    }
}

