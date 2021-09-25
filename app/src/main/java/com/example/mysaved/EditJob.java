package com.example.mysaved;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditJob extends AppCompatActivity {
    EditText et_companyed,et_titleed,et_salaryed,et_descriptioned,et_emailed,et_phoneed;
    Spinner  et_jobtypeed,et_dised;
    Button btn_edit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z0-9]+\\.+[a-z]+";
    String phonePattern = "[0-9]{10}";
    DatabaseReference databaseReference;
    ImageView imageView_deletejob, imageView155;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);
        String UserID = getIntent().getExtras().getString("UserID");
        String JobID = getIntent().getExtras().getString("JobID");
        String CompanyName= getIntent().getExtras().getString("JOBCOMPANY");
        String JobTitle= getIntent().getExtras().getString("JOBTITLE");
        String Salary= getIntent().getExtras().getString("SALARY");
//      String JobType= getIntent().getExtras().getString("JOBTYPE");
        String JobType= getIntent().getExtras().getString("JOBTYPE");
        String Description= getIntent().getExtras().getString("DESCRIPTION");
        String Email = getIntent().getExtras().getString("EMAIL");
        String Mobile = getIntent().getExtras().getString("MOBILE");
        String District= getIntent().getExtras().getString("LOCATION");
        String date = getIntent().getExtras().getString("POSTDATE");
        String Image = getIntent().getExtras().getString("Imageurl");






        et_companyed=findViewById(R.id.et_companyed);
        btn_edit=findViewById(R.id.btn_edit);
        et_titleed=findViewById(R.id.et_titleed);
        et_salaryed=findViewById(R.id.et_salaryed);
        et_descriptioned=findViewById(R.id.et_descriptioned);
        et_emailed=findViewById(R.id.et_emailed);
        et_phoneed=findViewById(R.id.et_phoneed);
        et_jobtypeed=(Spinner)findViewById(R.id.et_jobtypeed);
        et_dised=(Spinner)findViewById(R.id.et_dised);
        imageView_deletejob=findViewById(R.id.imageView_deletejob);
        imageView155 = findViewById(R.id.imageView155);


        et_companyed.setText(CompanyName);
        et_titleed.setText(JobTitle);
       et_salaryed.setText(Salary);
       et_emailed.setText(Email);
       et_phoneed.setText(Mobile);
       et_descriptioned.setText(Description);

        imageView155.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewjobM.class));
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.job_type_ddm, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_jobtypeed.setAdapter(adapter);
        if (JobType != null) {
            int spinnerPosition = adapter.getPosition(JobType);
            et_jobtypeed.setSelection(spinnerPosition);
        }

        ArrayAdapter<CharSequence> adapterr = ArrayAdapter.createFromResource(this, R.array.districts_smm, android.R.layout.simple_spinner_item);
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       et_dised.setAdapter(adapterr);
        if (District != null) {
            int spinnerPosition = adapterr.getPosition(District);
            et_dised.setSelection(spinnerPosition);
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("create_job").child(UserID).child(JobID);


//      et_jobtypeed.setText(Spinner)(JobType);




            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = et_companyed.getText().toString();
                    String title =  et_titleed.getText().toString();
                    String salary1 = et_salaryed.getText().toString();
                    String description = et_descriptioned.getText().toString();
                    String email1 = et_emailed.getText().toString();
                    String phone1 = et_phoneed.getText().toString();
                    String job_type = et_jobtypeed.getSelectedItem().toString();
                    String district = et_dised.getSelectedItem().toString();


                    JobHelperClass helperClass = new JobHelperClass(name,title,salary1,description,email1,phone1,job_type,district,date);

                    if(TextUtils.isEmpty(name)){
                        et_companyed.setError("Company Name is Required");
                        return;
                    }
                    if(TextUtils.isEmpty(title)){
                        et_titleed.setError("Job Title is Required");
                        return;
                    }
                    if(TextUtils.isEmpty(salary1)){
                        et_salaryed.setError("Salary is Required");
                        return;
                    }
                    if(TextUtils.isEmpty(description)){
                        et_descriptioned.setError("Description is Required");
                        return;
                    }
                    if(et_emailed.getText().toString().isEmpty()) {
                       et_emailed.setError("Email is Required");
                    }else {
                        if (!et_emailed.getText().toString().trim().matches(emailPattern)) {
                            et_emailed.setError("Invalid Email Address");
                            return;
                        }
                    }
                    if(TextUtils.isEmpty(phone1)){
                        et_phoneed.setError("Phone is Required");
                    }else {
                        if (!et_phoneed.getText().toString().trim().matches(phonePattern)) {
                            et_phoneed.setError("Invalid Phone Number");
                            return;
                        }
                    }
                    if (job_type.equals("Select Job Type")){
                        Toast.makeText(EditJob.this, "Select Job Type", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (district.equals("Select District")){
                        Toast.makeText(EditJob.this, "Select a District", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    databaseReference.setValue(helperClass);
                    databaseReference.child("img").setValue(Image).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditJob.this, " Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), ViewjobM.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("user_id",UserID);
                            intent.putExtra("job_id",JobID );
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        }
                    });
                }
            });

        imageView_deletejob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(EditJob.this);
                builder.setMessage("Do You Want To Delete?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        databaseReference.removeValue();
               Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Homepage_new.class));

                    }
                }).setNegativeButton("Cancel",null);
//                databaseReference.removeValue();
//               Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                AlertDialog alert =builder.create();
                alert.show();
            }
        });
    }
}