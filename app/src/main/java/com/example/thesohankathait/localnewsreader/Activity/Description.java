package com.example.thesohankathait.localnewsreader.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thesohankathait.localnewsreader.Model.News;
import com.example.thesohankathait.localnewsreader.R;
import com.squareup.picasso.Picasso;

public class Description extends AppCompatActivity {
   private TextView description,reporterName,date,titleName;
   private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);


        description=findViewById(R.id.descriptionTextView);
        reporterName=findViewById(R.id.reporter);
        date=findViewById(R.id.dateAndTime);
        titleName=findViewById(R.id.newstitle);
        image=findViewById(R.id.newsImageView);

        Intent descriptionIntent = getIntent();
        News news=new News();
        news.setReporter(descriptionIntent.getStringExtra("REPORTER"));
        news.setDateAndTime(descriptionIntent.getStringExtra("DATEANDTIME"));
        news.setTitle(descriptionIntent.getStringExtra("TITLE"));
        news.setImageURl(descriptionIntent.getStringExtra("NEWSIMGE"));
        news.setDescription(descriptionIntent.getStringExtra("DESCRIPTION"));

        date.setText(news.getDateAndTime());
        reporterName.setText(news.getReporter());
        titleName.setText(news.getTitle());
        description.setText(news.getDescription());
//                    Log.i("imgeUrl",news.getImageURl());
        Picasso.get().load(news.getImageURl()).into(image);

    }
}
