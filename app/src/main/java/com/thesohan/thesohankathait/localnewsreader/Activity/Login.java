package com.thesohan.thesohankathait.localnewsreader.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thesohan.thesohankathait.localnewsreader.Model.User;
import com.thesohan.thesohankathait.localnewsreader.R;
import com.thesohan.thesohankathait.localnewsreader.Utility.CheckNetwork;
import com.thesohan.thesohankathait.localnewsreader.Utility.DownloadTask;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private EditText nameEditText;
    private EditText phoneEditText;
    public GoogleSignInClient googleSignInClient;
    private Button googleSignInButton;
    private static final int SIGNUP_REQUESTCODE=1;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!CheckNetwork.isNetworkAvailable(this))
            warningAndExit();


        DownloadTask downloadTask=new DownloadTask();
            downloadTask.execute("https://newsapi.org/v2/top-headlines?sources=the-hindu&apiKey=eb1b6923ed7f413aa31dc9ab9a52647d");


        progressDialog=new ProgressDialog(this);
        googleSignInButton=findViewById(R.id.googlesigninbutton);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                signIn();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient=GoogleSignIn.getClient(this,gso);

        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }

    private void warningAndExit() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Please make sure you have a working internet connection")
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setPositiveButton("try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();

    }

    public void signIn(){

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            User.setUser(sharedPreferences.getString("NAME",null),sharedPreferences.getString("EMAIL",null),sharedPreferences.getString("PHOTOURL",null));
            Log.i("name",User.currentUser.getName());
            progressDialog.dismiss();
            moveToNewsActivity();
        }
        else {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, SIGNUP_REQUESTCODE);
        }
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.dismiss();
        if(requestCode==SIGNUP_REQUESTCODE && resultCode==RESULT_OK && data!=null){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "googel sign in failed", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signInWithCredential", "signInWithCredential:success");
                           //saving user info in shared preferences
                            editor.putString("NAME",mAuth.getCurrentUser().getDisplayName());
                           editor.putString("EMAIL",mAuth.getCurrentUser().getEmail());
                           editor.putString("PHOTOURL",mAuth.getCurrentUser().getPhotoUrl().toString());
                           editor.apply();
                           User.setUser(mAuth.getCurrentUser().getDisplayName(),mAuth.getCurrentUser().getEmail(),mAuth.getCurrentUser().getPhotoUrl().toString());
                           storeUserInFirebase();
                           progressDialog.dismiss();
                           moveToNewsActivity();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signInFailed", "signInWithCredential:failure", task.getException());

                        }

                    }
                });
    }

    private void storeUserInFirebase() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.setValue(User.getCurrentUser()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(), "user Saved", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void moveToNewsActivity() {
    Intent intent=new Intent(Login.this,NewsActivity.class);
    startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }



}
