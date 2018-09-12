package com.example.thesohankathait.localnewsreader.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thesohankathait.localnewsreader.Adapter.LocalNewsRecyclerAdapter;
import com.example.thesohankathait.localnewsreader.Adapter.MyUploadsRecyclerAdapter;
import com.example.thesohankathait.localnewsreader.Fragment.LocalNews;
import com.example.thesohankathait.localnewsreader.Model.News;
import com.example.thesohankathait.localnewsreader.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyUpload extends AppCompatActivity {
    private RecyclerView myUploadsRecyclerView;
    public static MyUploadsRecyclerAdapter adapter;
    public  static ArrayList<News> newsArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_upload);

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
