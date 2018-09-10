package com.example.thesohankathait.localnewsreader.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thesohankathait.localnewsreader.Model.News;
import com.example.thesohankathait.localnewsreader.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TopNewsRecyclerAdapter extends RecyclerView.Adapter<TopNewsRecyclerAdapter.MyViewHolder> {

    private ArrayList<News> newsList=new ArrayList<>();
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
                   View v=LayoutInflater.from(context).inflate(R.layout.description,null,false);

                    TextView description,reporterName,date,titleName;
                     ImageView image;
                     description=v.findViewById(R.id.descriptionTextView);
                     reporterName=v.findViewById(R.id.reporter);
                     date=v.findViewById(R.id.dateAndTime);
                     titleName=v.findViewById(R.id.newstitle);
                     image=v.findViewById(R.id.newsImageView);
                    News news=newsList.get(getAdapterPosition());
                    date.setText(news.getDateAndTime());
                    reporterName.setText(news.getReporter());
                    titleName.setText(news.getTitle());
                    description.setText(news.getDescription());
//                    Log.i("imgeUrl",news.getImageURl());
                    Picasso.get().load(news.getImageURl()).into(image);

                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setView(v).show();
                }
            });
        }
    }

    public TopNewsRecyclerAdapter(ArrayList<News> newsList, Context context) {
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