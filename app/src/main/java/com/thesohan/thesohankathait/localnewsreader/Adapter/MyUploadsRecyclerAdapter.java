package com.thesohan.thesohankathait.localnewsreader.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thesohan.thesohankathait.localnewsreader.Activity.Description;
import com.thesohan.thesohankathait.localnewsreader.Fragment.LocalNews;
import com.thesohan.thesohankathait.localnewsreader.Model.News;
import com.thesohan.thesohankathait.localnewsreader.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyUploadsRecyclerAdapter extends RecyclerView.Adapter<MyUploadsRecyclerAdapter.MyViewHolder>  {

    public static ArrayList<News> newsList;
    Context context;


    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView reporter,dateAndTime,title;
        public ImageView newsImage;
        public Button deleteButton;

        public MyViewHolder(View view) {
            super(view);
            deleteButton=view.findViewById(R.id.delete);
            newsImage=view.findViewById(R.id.newsImageViewt);
            reporter=view.findViewById(R.id.reporter);
            dateAndTime=view.findViewById(R.id.dateAndTime);
            title=view.findViewById(R.id.newstitle);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Deleted "+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    int a=getAdapterPosition();
                    deleteNews(getAdapterPosition());
                }
            });
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

    private void deleteNews(final int adapterPosition) {
        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("News").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for(DataSnapshot finalDatasnapshot:dataSnapshot.getChildren()){
//                    Log.d("datanap",dataSnapshot.getKey());

                    final News news = finalDatasnapshot.getValue(News.class);
                if (isEqual(newsList.get(adapterPosition), news)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.ic_warning_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d("size:", "" + LocalNews.newsArrayList.size());
//                                    Log.d("newstobedeleted", dataSnapshot.getValue(News.class).getTitle());
                                    deleteNewsFromLocalNewsList(news);
//                                    LocalNews.newsArrayList.remove(dataSnapshot.getValue(News.class));
//                                    Log.d("size:",""+LocalNews.newsArrayList.size());
                                    LocalNews.adapter.notifyDataSetChanged();
                                    dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                                Toast.makeText(context, "News Deleted ", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            newsList.remove(adapterPosition);
                                            notifyDataSetChanged();


                                        }
                                    });

                                }
                            })
                            .setNegativeButton("No", null)
                            .setTitle("Are you sure?")
                            .show();
                }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void deleteNewsFromLocalNewsList(News value) {
        for(int i=0;i<LocalNews.newsArrayList.size();i++){
            if(isEqual(value,LocalNews.newsArrayList.get(i))){
                LocalNews.newsArrayList.remove(i);
                return;
            }
        }


    }

    private boolean isEqual(News news, News news1) {
        return news.getDateAndTime().equals(news1.getDateAndTime()) &&
                news.getDescription().equals(news1.getDescription()) &&
                news.getImageURl().equals(news1.getImageURl()) &&
                news.getReporter().equals(news1.getReporter()) &&
                news.getTitle().equals(news1.getTitle());


    }

    public MyUploadsRecyclerAdapter(ArrayList<News> newsList, Context context) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_uploads_row,parent,false);
        return new MyViewHolder(v);
    }
}