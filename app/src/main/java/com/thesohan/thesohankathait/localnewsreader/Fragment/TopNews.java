package com.thesohan.thesohankathait.localnewsreader.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesohan.thesohankathait.localnewsreader.Adapter.TopNewsRecyclerAdapter;
import com.thesohan.thesohankathait.localnewsreader.Model.News;
import com.thesohan.thesohankathait.localnewsreader.R;
import com.google.android.gms.ads.MobileAds;
//import com.example.thesohankathait.localnewsreader.Utility.DownloadTask;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TopNews extends Fragment {
   private RecyclerView topNewsRecyclerView;
    public static TopNewsRecyclerAdapter adapter;
    public  static ArrayList<News> newsArrayList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.top_news,container,false);

//        AdView adView = new AdView(getContext());
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        MobileAds.initialize(getContext(),"ca-app-pub-3834478485267663~5363622764");


// TODO: Add adView to your view hierarchy.


        topNewsRecyclerView =view.findViewById(R.id.topNewsRecyclerView);
        topNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        topNewsRecyclerView.setHasFixedSize(true);
        adapter=new TopNewsRecyclerAdapter(newsArrayList,getActivity());
        topNewsRecyclerView.setAdapter(adapter);
        return view;
    }

    public static TopNews newInstance() {

        Bundle args = new Bundle();

        TopNews fragment = new TopNews();
        fragment.setArguments(args);
        return fragment;
    }
}
