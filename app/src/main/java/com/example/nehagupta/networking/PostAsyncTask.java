package com.example.nehagupta.networking;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class PostAsyncTask extends AsyncTask<String,Void,ArrayList<Post>> {

    PostDownloadListener listener;
    PostAsyncTask(PostDownloadListener listener)
    {
        this.listener=listener;
    }

    @Override
    protected ArrayList<Post> doInBackground(String... strings) {

        ArrayList<Post>  titles=new ArrayList<>();
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

            JSONArray posts=new JSONArray(result);
           // Gson gson=new Gson();
            //ArrayList<Post> posts=gson.fromJson(result,new TypeToken<ArrayList<Post>>(){}.getType());for(int i=0;i<posts.length();i++)
            for(int i=0;i<posts.length();i++)
            {
                JSONObject postObject=posts.getJSONObject(i);
                int user_id=postObject.getInt("userId");
                int id=postObject.getInt("id");
                String title=postObject.getString("title");
                String body=postObject.getString("body");
                Post post=new Post(id,user_id,"Post"+id,title,body);
                titles.add(post);
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

        return(titles);
    }

    @Override
    protected void onPostExecute(ArrayList<Post> strings) {
        super.onPostExecute(strings);
        listener.onDownload(strings);
    }
}

