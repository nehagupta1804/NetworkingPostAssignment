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

public class UserAsyncTask extends AsyncTask<String,Void,User> {

    UserDownloadListener listener;
    User user;
    UserAsyncTask(UserDownloadListener listener)
    {
        this.listener=listener;
    }

    @Override
    protected User doInBackground(String... strings) {
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
            JSONObject rootobject=new JSONObject(result);
            String name=rootobject.getString("name");
            String username=rootobject.getString("username");
            String email=rootobject.getString("email");
            user=new User(name,username,email);
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

        return(user);
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        listener.onDownload(user);
    }
}
