package com.example.mysaved;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CreateJob extends AppCompatActivity {

    //Variable declare

    EditText company_name, job_title, salary, job_description, email, phone;
    Spinner type_spinner, district_spinner;
    Button create_btn;
    ImageView b1_btn,addimage;
    ProgressBar progressBar;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String phonePattern = "[0-9]{10}";
    int job_id = 0;
    Uri imageUri;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

   // Get Current user

    String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    //Database Connection

    DatabaseReference root = FirebaseDatabase.getInstance().getReference("create_job").child(userID);
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        company_name = findViewById(R.id.et_company);
        job_title = findViewById(R.id.et_title);
        salary = findViewById(R.id.et_salary);
        job_description = findViewById(R.id.et_description);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_phone);
        type_spinner = (Spinner)findViewById(R.id.et_jobtype);
        district_spinner = (Spinner)findViewById(R.id.et_dis);
        create_btn = findViewById(R.id.btn_create);
        b1_btn = findViewById(R.id.imageView_b1);
        addimage = findViewById(R.id.imageButton_addimage);
        progressBar = findViewById(R.id.progressBar_cj);
        progressBar.setVisibility(View.INVISIBLE);

        //Auto increment value

        addimage.setOnClickListener(new View.OnClickListener() {

            //Gallery Access

            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //This codes are execuded after click

                String name = company_name.getText().toString();
                String title = job_title.getText().toString();
                String salary1 = salary.getText().toString();
                String description = job_description.getText().toString();
                String email1 = email.getText().toString();
                String phone1 = phone.getText().toString();
                String job_type = type_spinner.getSelectedItem().toString();
                String district = district_spinner.getSelectedItem().toString();

                //Get current date

                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                //Data pass to helper class

                JobHelperClass helperClass = new JobHelperClass(name,title,salary1,description,email1,phone1,job_type,district,date);
                job_id++;

                //Validations

                if(TextUtils.isEmpty(name)){
                    company_name.setError("Company Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(title)){
                    job_title.setError("Job Title is Required");
                    return;
                }
                if(TextUtils.isEmpty(salary1)){
                    salary.setError("Salary is Required");
                    return;
                }
                if(TextUtils.isEmpty(description)){
                    job_description.setError("Description is Required");
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
                if (job_type.equals("Select Job Type")){
                    Toast.makeText(CreateJob.this, "Select Job Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (district.equals("Select District")){
                    Toast.makeText(CreateJob.this, "Select a District", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }else{
                    Toast.makeText(CreateJob.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }

                root.child(String.valueOf(job_id)).setValue(helperClass);
            }
        });

    }

    //Image get from gallery

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            addimage.setImageURI(imageUri);
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
                        root.child(String.valueOf(job_id)).child(modelId).setValue(uri.toString());
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(CreateJob.this, "Job Create Successful", Toast.LENGTH_SHORT).show();
                        addimage.setImageResource(R.drawable.baseline_add_circle_24);
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
                Toast.makeText(CreateJob.this, "Job Create Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}


