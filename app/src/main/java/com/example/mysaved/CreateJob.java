package com.example.mysaved;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class CreateJob extends AppCompatActivity {

    EditText company_name, job_title, salary, job_description, email, phone;
    Spinner type_spinner, district_spinner;
    Button create_btn;
    ImageView b1_btn, cimg, addimage;
    ProgressBar progressBar;
    Uri imageUri;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference reference2 = FirebaseStorage.getInstance().getReference();

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
        cimg = findViewById(R.id.imageView_cimg);
        addimage = findViewById(R.id.imageButton_addimage);
        progressBar = findViewById(R.id.progressBar_cj);


        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("create_job");


        String name = company_name.getText().toString();
        String title = job_title.getText().toString();
        String salary1 = salary.getText().toString();
        String description = job_description.getText().toString();
        String email1 = email.getText().toString();
        String phone1 = phone.getText().toString();
        String job_type = type_spinner.getSelectedItem().toString();
        String district = district_spinner.getSelectedItem().toString();

        JobHelperClass helperClass = new JobHelperClass(name,title,salary1,description,email1,phone1,job_type,district);

        reference.child(phone1).setValue(helperClass);

            }
        });

    }
}


