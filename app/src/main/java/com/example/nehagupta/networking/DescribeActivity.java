package com.example.nehagupta.networking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DescribeActivity extends AppCompatActivity {

    TextView nameTextview;
    TextView usernameTextview;
    TextView mailTextview;
    TextView titleTextview;
    TextView bodyTextview;
    TextView commentTextview;
    String title;
    String body;
    int userid;
    int postid;
    ProgressBar progressBar;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe);
        nameTextview=findViewById(R.id.name);
        usernameTextview=findViewById(R.id.username);
        mailTextview=findViewById(R.id.email);
        titleTextview=findViewById(R.id.title);
        bodyTextview=findViewById(R.id.body);
        commentTextview=findViewById(R.id.comments);
        progressBar=findViewById(R.id.progressbar);
        layout=findViewById(R.id.layout);
        layout.setVisibility(View.GONE);
        Intent intent=getIntent();
         title =intent.getStringExtra("title");
         body=intent.getStringExtra("body");
         userid=intent.getIntExtra("user_id",0);
         postid=intent.getIntExtra("post_id",0);
    }
    public void fetchData(View view)
    {
        progressBar.setVisibility(View.VISIBLE);

        UserAsyncTask task=new UserAsyncTask(new UserDownloadListener(){
            @Override
            public void onDownload(User user) {
                titleTextview.setText(title);
                bodyTextview.setText(body);
                String name=user.getName();
                String username=user.getUsername();
                String email=user.getEmail();
                nameTextview.setText(name);
                usernameTextview.setText(username);
                mailTextview.setText(email);
                layout.setVisibility(View.VISIBLE);

            }
        });
        task.execute("https://jsonplaceholder.typicode.com/users/"+userid);
        CommentAsyncTask task1=new CommentAsyncTask(new  CommentDownloadListener(){
            @Override
            public void onDownload(ArrayList<String> strings) {

                String cumString="";
                for(int i=0;i<strings.size();i++)
                    cumString+= strings.get(i)+"\n";
                commentTextview.setText(cumString);
                progressBar.setVisibility(View.GONE);

            }
        });
        task1.execute("https://jsonplaceholder.typicode.com/posts/"+postid+"/comments");
    }

    public void get_posts(View view)
    {
        Intent intent =new Intent();
        setResult(1,intent);
        finish();
    }

}
