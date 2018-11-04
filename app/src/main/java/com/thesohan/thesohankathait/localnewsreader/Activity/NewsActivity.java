package com.thesohan.thesohankathait.localnewsreader.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.thesohan.thesohankathait.localnewsreader.Fragment.Functionality;
import com.thesohan.thesohankathait.localnewsreader.Fragment.policyFragment;
import com.thesohan.thesohankathait.localnewsreader.R;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;

public class NewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private  AlertDialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(Color.BLACK);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addDifferentFragment(Functionality.newInstance(0));
    }

    private void addDifferentFragment(Fragment replacableFragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,replacableFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       if (id == R.id.myUploads) {
           Intent intent=new Intent(NewsActivity.this,MyUpload.class);
           startActivity(intent);

        } else if (id == R.id.top_news) {
           addDifferentFragment(Functionality.newInstance(0));

        } else if (id == R.id.local_news) {
           addDifferentFragment(Functionality.newInstance(1));

        } else if (id == R.id.logout) {
           showAlertDialog();

       }
       else if(id==R.id.privacy){

           addDifferentFragment(new policyFragment());
       }
       else if(id==R.id.nav_share){
           //share the app link
           String appLink="http://play.google.com/store/apps/details?id=com.thesohan.thesohankathait.localnewsreader";
           Intent sendIntent = new Intent();
           sendIntent.setAction(Intent.ACTION_SEND);
           sendIntent.putExtra(Intent.EXTRA_TEXT, appLink);
           sendIntent.setType("text/plain");
           startActivity(sendIntent);
       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAlertDialog() {
        builder=new AlertDialog.Builder(this)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                            FirebaseAuth.getInstance().signOut();
                        SharedPreferences sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.clear().commit();
                        finish();
                        builder.dismiss();
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.dismiss();
                    }
                })
                .show();



    }
}
