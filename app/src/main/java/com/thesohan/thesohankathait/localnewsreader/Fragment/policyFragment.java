package com.thesohan.thesohankathait.localnewsreader.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thesohan.thesohankathait.localnewsreader.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class policyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.policy_fragment,container,false);
        final WebView webView = view.findViewById(R.id.webview);

        FirebaseDatabase.getInstance().getReference("link").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String url = dataSnapshot.getValue().toString();
//                    webView.loadUrl("https://www.journaldev.com");
                    WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);

                    webView.loadUrl(url);
                }
                catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    return view;
    }

    public static policyFragment newInstance() {
        
        Bundle args = new Bundle();
        
        policyFragment fragment = new policyFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
