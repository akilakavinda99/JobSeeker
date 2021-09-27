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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditJobReq extends AppCompatActivity {
    EditText et_jobtitle, et_name, et_age, et_description, et_email, et_phone;
    Spinner et_gender_editjobReq;
    Button btn_editreq;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z0-9]+\\.+[a-z]+";
    String phonePattern = "[0-9]{10}";
    DatabaseReference databaseReference;
    ImageView imageView_deletejobReq, edit_reqjob_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jobreq);

        String UserID = getIntent().getExtras().getString("UserID");
        String ReqJobID = getIntent().getExtras().getString("JobID");
        String EName = getIntent().getExtras().getString("NAME");
        String JobTitle = getIntent().getExtras().getString("JOBTITLE");
        String EAge = getIntent().getExtras().getString("AGE");
        String EDescription = getIntent().getExtras().getString("DESCRIPTION");
        String EEmail = getIntent().getExtras().getString("EMAIL");
        String EPhone = getIntent().getExtras().getString("MOBILE");
        String EGender = getIntent().getExtras().getString("GENDER");
        String date = getIntent().getExtras().getString("DATE");
        String Image = getIntent().getExtras().getString("imageurl");


        et_name = findViewById(R.id.et_name_editjob);
        et_jobtitle = findViewById(R.id.et_jobtitle_editjob);
        et_age = findViewById(R.id.et_age_editjob);
        et_description = findViewById(R.id.et_description_editjob);
        et_email = findViewById(R.id.et_email_editjob);
        et_phone = findViewById(R.id.et_phone_editjob);
        et_gender_editjobReq = (Spinner) findViewById(R.id.et_gender_editjobReq);
        btn_editreq = findViewById(R.id.btn_editjobreq);
        imageView_deletejobReq = findViewById(R.id.img_dlt);
        edit_reqjob_back_btn = findViewById(R.id.editreqjob_back_btn);

        et_name.setText(EName);
        et_jobtitle.setText(JobTitle);
        et_age.setText(EAge);
        et_email.setText(EEmail);
        et_phone.setText(EPhone);
        et_description.setText(EDescription);


        ArrayAdapter<CharSequence> adapterE = ArrayAdapter.createFromResource(this, R.array.gender_vp, android.R.layout.simple_spinner_item);
        adapterE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_gender_editjobReq.setAdapter(adapterE);
        if (EGender != null) {
            int spinnerPosition = adapterE.getPosition(EGender);
            et_gender_editjobReq.setSelection(spinnerPosition);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("job_request").child(UserID).child(ReqJobID);


        btn_editreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String title = et_jobtitle.getText().toString();
                String c_age1 = et_age.getText().toString();
                String description = et_description.getText().toString();
                String email1 = et_email.getText().toString();
                String phone1 = et_phone.getText().toString();
                String gender = et_gender_editjobReq.getSelectedItem().toString();


                RequestHelperClass helperClass = new RequestHelperClass(name, title, c_age1, description, email1, phone1, gender, date);

                if (TextUtils.isEmpty(name)) {
                    et_name.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(title)) {
                    et_jobtitle.setError("Job Title is Required");
                    return;
                }
                if (TextUtils.isEmpty(c_age1)) {
                    et_age.setError("Age is Required");
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    et_description.setError("Description is Required");
                    return;
                }
                if (et_email.getText().toString().isEmpty()) {
                    et_email.setError("Email is Required");
                } else {
                    if (!et_email.getText().toString().trim().matches(emailPattern)) {
                        et_email.setError("Invalid Email Address");
                        return;
                    }
                }
                if (TextUtils.isEmpty(phone1)) {
                    et_phone.setError("Phone is Required");
                } else {
                    if (!et_phone.getText().toString().trim().matches(phonePattern)) {
                        et_phone.setError("Invalid Phone Number");
                        return;
                    }
                }
                if (gender.equals("Select Gender")) {
                    Toast.makeText(EditJobReq.this, "Select Gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseReference.setValue(helperClass);
                databaseReference.child("img").setValue(Image).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditJobReq.this, " Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), ViewReqJob.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("user_id", UserID);
                        intent.putExtra("job_id", ReqJobID);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    }
                });


            }
        });

        edit_reqjob_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewReqJob.class));
            }
        });

        imageView_deletejobReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditJobReq.this);
                builder.setMessage("Do You Want To Delete?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        databaseReference.removeValue();
                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),NewReqJob_Homepage.class));

                    }
                }).setNegativeButton("Cancel", null);
//                databaseReference.removeValue();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}