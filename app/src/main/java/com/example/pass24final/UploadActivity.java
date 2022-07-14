package com.example.pass24final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {


    ImageView UserAddharCard;
    Button uploadBtn;
    ProgressBar progressBar;
    FirebaseDatabase imgFirebaseDatabase;
    DatabaseReference imgFirebaseReference;
    StorageReference storageReference;
    Uri imageUri;
    Button next;
    String usernamefb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent intent = getIntent();
        usernamefb = intent.getStringExtra("username_passform2_to_Upload");

        uploadBtn = findViewById(R.id.upload_btn);
        progressBar = findViewById(R.id.progress_bar_upload);
        UserAddharCard = findViewById(R.id.upload_img);
        imgFirebaseDatabase = FirebaseDatabase.getInstance();
        imgFirebaseReference = imgFirebaseDatabase.getReference("userAddharCard");
        storageReference = FirebaseStorage.getInstance().getReference();


        progressBar.setVisibility(View.INVISIBLE);
        UserAddharCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    uploadToFB(imageUri);
                } else {
                    Toast.makeText(UploadActivity.this, "Please select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            UserAddharCard.setImageURI(imageUri);
        }
    }

    private void uploadToFB(Uri uri) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        model m = new model(uri.toString());
                        String modelId = imgFirebaseReference.push().getKey();
                        imgFirebaseReference.child(usernamefb).child(modelId).setValue(m);
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(UploadActivity.this, "Uploading Success fully!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UploadActivity.this, pass_form3.class);
                        intent.putExtra("username_Upload_to_passform3",usernamefb);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(UploadActivity.this, "Uploading Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}