package com.example.thesohankathait.localnewsreader.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thesohankathait.localnewsreader.Activity.Description;
import com.example.thesohankathait.localnewsreader.Model.News;
import com.example.thesohankathait.localnewsreader.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class LocalNewsRecyclerAdapter extends RecyclerView.Adapter<LocalNewsRecyclerAdapter.MyViewHolder>  {

    public static ArrayList<News> newsList;
    Context context;


    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView reporter,dateAndTime,title;
        public ImageView newsImage;

        public MyViewHolder(View view) {
            super(view);
            newsImage=view.findViewById(R.id.newsImageView);
            reporter=view.findViewById(R.id.reporter);
            dateAndTime=view.findViewById(R.id.dateAndTime);
            title=view.findViewById(R.id.newstitle);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent descriptionIntent=new Intent(context,Description.class);
                            News news=newsList.get(getAdapterPosition());
                            descriptionIntent.putExtra("REPORTER",news.getReporter());
                            descriptionIntent.putExtra("DATEANDTIME",news.getDateAndTime());
                            descriptionIntent.putExtra("TITLE",news.getTitle());
                            descriptionIntent.putExtra("NEWSIMGE",news.getImageURl());
                            descriptionIntent.putExtra("DESCRIPTION",news.getDescription());
                            context.startActivity(descriptionIntent);

                        }
                    });
                }
            });
        }
    }

    public LocalNewsRecyclerAdapter(ArrayList<News> newsList,Context context) {
        this.newsList = newsList;
        this.context=context;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        News news=newsList.get(position);
        holder.dateAndTime.setText(news.getDateAndTime());
        holder.reporter.setText(news.getReporter());
        holder.title.setText(news.getTitle());
        Log.i("imgeUrl",news.getImageURl());
        Picasso.get().load(news.getImageURl()).into(holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_news_row,parent,false);
        return new MyViewHolder(v);
    }
}