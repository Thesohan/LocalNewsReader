package com.example.thesohankathait.localnewsreader.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thesohankathait.localnewsreader.Activity.Description;
import com.example.thesohankathait.localnewsreader.Fragment.LocalNews;
import com.example.thesohankathait.localnewsreader.Model.News;
import com.example.thesohankathait.localnewsreader.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
            newsImage=view.findViewById(R.id.newsImageView);
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
        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("News").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                News news=dataSnapshot.getValue(News.class);
                if(isEqual(newsList.get(adapterPosition),news)){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setIcon(R.drawable.ic_warning_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                                Toast.makeText(context, "News Deleted ", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                      newsList.remove(adapterPosition);
                                       notifyDataSetChanged();
                                            LocalNews.adapter.notifyDataSetChanged();
                                        }
                                    });

                                }
                            })
                            .setNegativeButton("No",null)
                            .setTitle("Are you sure?")
                            .show();
                }
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