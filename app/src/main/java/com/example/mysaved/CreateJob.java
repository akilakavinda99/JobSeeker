package com.example.mysaved;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateJob extends AppCompatActivity {

    EditText company_name, job_title, salary, job_description, email, phone;
    Spinner type_spinner, district_spinner , city_spinner;;
    Button create_btn;
    ImageView b1_btn, cimg, addimage;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job);

        company_name = findViewById(R.id.et_company);
        job_title = findViewById(R.id.et_title);
        salary = findViewById(R.id.et_salary);
        job_description = findViewById(R.id.et_description);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_email);
        type_spinner = (Spinner)findViewById(R.id.et_jobtype);
        district_spinner = (Spinner)findViewById(R.id.et_dis);
        city_spinner = (Spinner)findViewById(R.id.et_city);
        create_btn = findViewById(R.id.btn_create);
        b1_btn = findViewById(R.id.imageView_b1);
        cimg = findViewById(R.id.imageView_cimg);
        addimage = findViewById(R.id.imageButton_addimage);

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

        JobHelperClass helperClass = new JobHelperClass(name,title,salary1,description,email1,phone1);

        reference.child(phone1).setValue(helperClass);

            }
        });

    }
}
