package com.example.nehagupta.networking;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    CustomAdapter adapter;
    ArrayList<Post> posts = new ArrayList<Post>();
    ProgressBar progressBar;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);
        progressBar=findViewById(R.id.progressbar);
        adapter=new CustomAdapter(this,posts);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }
    public void fetchData(View view)
    {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        PostAsyncTask task=new PostAsyncTask(new PostDownloadListener(){
            @Override
            public void onDownload(ArrayList<Post> titles) {
                posts.clear();
                posts.addAll(titles);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        });
        task.execute("https://jsonplaceholder.typicode.com/posts");
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this,DescribeActivity.class);
        Post post = posts.get(i);
        id=post.getUserid();
        intent.putExtra("post_id",post.getPostid());
        intent.putExtra("user_id",post.getUserid());
        intent.putExtra("title",post.getTitle());
        intent.putExtra("body",post.getBody());
        startActivityForResult(intent,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==3)
        {
            if(resultCode==1)
            {
                    PostAsyncTask task=new PostAsyncTask(new PostDownloadListener(){
                        @Override
                        public void onDownload(ArrayList<Post> titles) {
                            posts.clear();
                            posts.addAll(titles);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    task.execute("https://jsonplaceholder.typicode.com/users/"+id+"/posts");

            }
        }

    }
}
