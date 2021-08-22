package com.example.mysaved;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CreateJob extends AppCompatActivity {

    //Variable declare

    EditText company_name, job_title, salary, job_description, email, phone;
    Spinner type_spinner, district_spinner;
    Button create_btn;
    ImageView b1_btn, cimg, addimage;
    ProgressBar progressBar;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String phonePattern = "[0-9]{10}";

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference reference2 = FirebaseStorage.getInstance().getReference();

    //ID declare

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

        //This codes are execuded after click

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

                //database child set

                reference.child(phone1).setValue(helperClass);
            }
        });


    }
}


