package com.example.nehagupta.networking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends ArrayAdapter {


    ArrayList<Post> posts;
    LayoutInflater inflater;
    Context context;
    public CustomAdapter(@NonNull Context context, ArrayList<Post> posts) {
        super(context, 0,posts);
        this.posts = posts;
        this.context=context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

    }
    public int getCount()
    {
        return(posts.size());
    }
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
           View  output = inflater.inflate(R.layout.post_row_layout, parent, false);
            TextView nameTextView = output.findViewById(R.id.postName);
            Post name=posts.get(position);
            String nm=name.getPost();
            nameTextView.setText(nm);
            return(output);
    }

}
