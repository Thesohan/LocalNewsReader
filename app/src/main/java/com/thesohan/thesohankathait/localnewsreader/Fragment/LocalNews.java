package com.thesohan.thesohankathait.localnewsreader.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesohan.thesohankathait.localnewsreader.Adapter.LocalNewsRecyclerAdapter;
import com.thesohan.thesohankathait.localnewsreader.Model.News;
import com.thesohan.thesohankathait.localnewsreader.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LocalNews extends Fragment {
    private RecyclerView topNewsRecyclerView;
    public static LocalNewsRecyclerAdapter adapter;
    public  static ArrayList<News> newsArrayList;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.top_news,container,false);

//         progressDialog=new ProgressDialog(getContext());
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//

        newsArrayList=new ArrayList<>();

        fetchLocalNews();
        topNewsRecyclerView =view.findViewById(R.id.topNewsRecyclerView);
        adapter=new LocalNewsRecyclerAdapter(newsArrayList,getActivity());

        topNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        topNewsRecyclerView.setHasFixedSize(true);
        topNewsRecyclerView.setAdapter(adapter);
        return view;
    }

    private void fetchLocalNews() {

        newsArrayList.clear();
        FirebaseDatabase.getInstance().getReference("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                News news=dataSnapshot.child("News").getValue(News.class)
                for(DataSnapshot finaldata:dataSnapshot.getChildren()){
                    Log.i("finaldata",finaldata.getKey());
                    if(finaldata.getKey().equals("News")){
                        finaldata.getRef().addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                Log.i("data",dataSnapshot.getValue(News.class).getTitle());
                                News news=dataSnapshot.getValue(News.class);

                                    if(!newsArrayList.contains(news))
                                     newsArrayList.add(news);
                                adapter.notifyDataSetChanged();
//                                if(progressDialog.isShowing())
//                                    progressDialog.dismiss();
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                                fetchLocalNews();
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
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//               fetchLocalNews();
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

    public static LocalNews newInstance() {

        Bundle args = new Bundle();

        LocalNews fragment = new LocalNews();
        fragment.setArguments(args);
        return fragment;
    }
}
