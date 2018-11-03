package com.thesohan.thesohankathait.localnewsreader.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.thesohan.thesohankathait.localnewsreader.Adapter.MyUploadsRecyclerAdapter;
import com.thesohan.thesohankathait.localnewsreader.Model.News;
import com.thesohan.thesohankathait.localnewsreader.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyUpload extends AppCompatActivity {
    private RecyclerView myUploadsRecyclerView;
    public static MyUploadsRecyclerAdapter adapter;
    public  static ArrayList<News> newsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_upload);
        newsArrayList=new ArrayList<>();

        fetchLocalNews();

                myUploadsRecyclerView=findViewById(R.id.myUploadNewsRecyclerView);
                adapter=new MyUploadsRecyclerAdapter(newsArrayList,this);

               myUploadsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                myUploadsRecyclerView.setHasFixedSize(true);
                myUploadsRecyclerView.setAdapter(adapter);

    }

    private void fetchLocalNews() {
        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("News").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                News news=dataSnapshot.getValue(News.class);
                newsArrayList.add(news);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
