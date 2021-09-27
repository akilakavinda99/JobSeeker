package com.example.mysaved;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class RequestJob extends AppCompatActivity {

    //Variable declare

    EditText req_title, c_name, c_age, req_description, email, phone;
    Spinner spinerr_gen;
    Button creatr_req_btn;
    ImageView back2_btn,insertimg;
    ProgressBar progressBar2;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String phonePattern = "[0-9]{10}";
    int req_id = 0;
    Long Lcount;
    Uri imageUri;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    // Get Current user

    String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    //Database Connection

    DatabaseReference root = FirebaseDatabase.getInstance().getReference("job_request").child(userID);
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestjob);

        req_title = findViewById(R.id.et_job_title);
        c_name = findViewById(R.id.et_nameR);
        c_age = findViewById(R.id.et_ageR);
        req_description = findViewById(R.id.et_descriptionR);
        email = findViewById(R.id.et_emailR);
        phone = findViewById(R.id.et_phone_noR);
        spinerr_gen = (Spinner)findViewById(R.id.spinner_genderR);
        creatr_req_btn = findViewById(R.id.btn_createR);
        back2_btn = findViewById(R.id.backimgR);
        insertimg = findViewById(R.id.image_add_imageR);
        progressBar2 = findViewById(R.id.progressBarR);
        progressBar2.setVisibility(View.INVISIBLE);
        req_description.setMovementMethod(new ScrollingMovementMethod());

        insertimg.setOnClickListener(new View.OnClickListener() {

            //Gallery Access

            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });

        //Get previous job count from database
        DatabaseReference rr = FirebaseDatabase.getInstance().getReference().child("user").child(userID);
        rr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lcount = (Long) snapshot.child("ReqId").getValue();
                assert Lcount != null;
                req_id = Lcount.intValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyRequestedJobs.class));
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });

        creatr_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //This codes are execuded after click

                String name = c_name.getText().toString();
                String title = req_title.getText().toString();
                String c_age1 = c_age.getText().toString();
                String description = req_description.getText().toString();
                String email1 = email.getText().toString();
                String phone1 = phone.getText().toString();
                String gender = spinerr_gen.getSelectedItem().toString();

                //Get current date

                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                //Data pass to helper class

                RequestHelperClass helperClass = new RequestHelperClass(name,title,c_age1,description,email1,phone1,gender,date);


                //Validations

                if(TextUtils.isEmpty(title)){
                    req_title.setError("Job Title is Required");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    c_name.setError("Your Name is Required");
                    return;
                }

                if(TextUtils.isEmpty(c_age1)){
                    c_age.setError("Age is Required");
                    return;
                }
                if(TextUtils.isEmpty(description)){
                    req_description.setError("Description is Required");
                    return;
                }
                if(email.getText().toString().isEmpty()) {
                    email.setError("Email is Required");
                }else {
                    if (!email.getText().toString().trim().matches(emailPattern)) {
                        email.setError("Invalid Email Address");
                        return;
                    }
                }
                if(TextUtils.isEmpty(phone1)){
                    phone.setError("Phone is Required");
                }else {
                    if (!phone.getText().toString().trim().matches(phonePattern)) {
                        phone.setError("Invalid Phone Number");
                        return;
                    }
                }
                if (gender.equals("Enter Gender")){
                    Toast.makeText(RequestJob.this, "Select Your Gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }else{
                    Toast.makeText(RequestJob.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }

                //increment one by job count
                req_id++;

                root.child(String.valueOf(req_id)).setValue(helperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });

            }
        });

    }

    //Image get from gallery

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            insertimg.setImageURI(imageUri);
        }
    }

    //Upload to Firebase storage

    private void uploadToFirebase(Uri uri){
        StorageReference reference = FirebaseStorage.getInstance().getReference();

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            //Generate Url

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                    //Get image url

                    @Override
                    public void onSuccess(Uri uri) {
                        String modelId = "img";
                        root.child(String.valueOf(req_id)).child(modelId).setValue(uri.toString());
                        progressBar2.setVisibility(View.INVISIBLE);
                        Toast.makeText(RequestJob.this, "Request Create Successful", Toast.LENGTH_SHORT).show();
                        insertimg.setImageResource(R.drawable.baseline_add_circle_24);

                        //update new job count to database
                        DatabaseReference rr = FirebaseDatabase.getInstance().getReference().child("user").child(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("ReqId",req_id);
                        rr.updateChildren(user);
                        startActivity(new Intent(getApplicationContext(), MyRequestedJobs.class));
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar2.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar2.setVisibility(View.INVISIBLE);
                Toast.makeText(RequestJob.this, "Request Create Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}