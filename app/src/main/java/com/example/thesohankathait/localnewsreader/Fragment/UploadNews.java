package com.example.thesohankathait.localnewsreader.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thesohankathait.localnewsreader.Model.News;
import com.example.thesohankathait.localnewsreader.Model.User;
import com.example.thesohankathait.localnewsreader.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UploadNews extends Fragment {
    private Button imageView;
    EditText titleEditText,descriptionEditText;
    Button uploadButton;
    Uri imageUri=null;
    String imageURl=null;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.upload,container,false);
        imageView=view.findViewById(R.id.uploadImage);
        titleEditText=view.findViewById(R.id.titleEditText);
       descriptionEditText=view.findViewById(R.id.descriptionEditText);
       uploadButton=view.findViewById(R.id.uploadButton);
       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               selectImage();
           }
       });
       uploadButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(everyThingIsSet()) {
                showProgressDialog();
                   uploadImage(imageUri);

               }else
                   Toast.makeText(getContext(), "Please fill the required info first!", Toast.LENGTH_SHORT).show();
           }
       });

        return view;
    }

    private void showProgressDialog() {
    }

    private boolean everyThingIsSet() {
    if(imageUri!=null&&!titleEditText.getText().toString().trim().equals("")&&!descriptionEditText.getText().toString().trim().equals(""))
        return true;
        else
        return false;

    }

    private void selectImage() {
        Intent chooseAndSetImageIntent=new Intent(Intent.ACTION_GET_CONTENT);
        chooseAndSetImageIntent.setType("image/*");
        startActivityForResult(chooseAndSetImageIntent,1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==getActivity().RESULT_OK&&data!=null){
             imageUri=data.getData();
             imageView.setText("Image Selected");
        }
    }

    private void uploadImage(Uri imageUri) {
        UUID uuid=UUID.randomUUID();
        FirebaseStorage.getInstance().getReference().child(uuid.toString()).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(getContext(), "file uploaded", Toast.LENGTH_SHORT).show();
                        imageURl=uri.toString();
                        uploadNewsIntoFirebase();
                    }
                });
            }
        });
    }

    private void uploadNewsIntoFirebase() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String date = sdf.format(new Date());
        News news=new News(User.currentUser.getName(),descriptionEditText.getText().toString(),titleEditText.getText().toString(),date,imageURl);

        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("News").push().setValue(news).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "news SAved", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static UploadNews newInstance() {
        
        Bundle args = new Bundle();
        
        UploadNews fragment = new UploadNews();
        fragment.setArguments(args);
        return fragment;
    }
}
