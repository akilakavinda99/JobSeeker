package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class  ViewJob extends AppCompatActivity {

    TextView jobtitle,tv_location_job,tv_jobtype_vjob,tv_company_jv,tv_salary_jv,jobType_tv,jobCompany_tv,job_salary_tv,
            tv_viewcounter,tv_dayscounter,tv_description_jobv,jobDescription_tv,tv_publishdate,jobPostDate_tv,tv_email_jv,
            jobEmail_tv,job_mobile_tv;
    ImageView img_compny,saveJob_img;

    Button btn_call_jv,btn_email_jv,button_job_edit;
    DatabaseReference databaseReference;
   String job_id;
   String imageUrl;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    Date pdate,cdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        jobtitle = findViewById(R.id.jobtitle);
        tv_location_job=findViewById(R.id.tv_location_job);
        tv_jobtype_vjob=findViewById(R.id.tv_jobtype_vjob);
        tv_company_jv=findViewById(R.id.tv_company_jv);
        tv_salary_jv=findViewById(R.id.tv_salary_jv);
        jobType_tv=findViewById(R.id.jobType_tv);
        jobCompany_tv=findViewById(R.id.jobCompany_tv);
        job_salary_tv=findViewById(R.id.job_salary_tv);
        tv_viewcounter=findViewById(R.id.tv_viewcounter);
        tv_dayscounter=findViewById(R.id.tv_dayscounter);
        tv_description_jobv=findViewById(R.id.tv_description_jobv);
        jobDescription_tv=findViewById(R.id.jobDescription_tv);
        tv_publishdate=findViewById(R.id.tv_publishdate);
        jobPostDate_tv=findViewById(R.id.jobPostDate_tv);
        tv_email_jv=findViewById(R.id.tv_email_jv);
        jobEmail_tv=findViewById(R.id.jobEmail_tv);
        job_mobile_tv=findViewById(R.id.job_mobile_tv);

        img_compny=findViewById(R.id.img_compny);
        saveJob_img=findViewById(R.id.saveJob_img);

        btn_call_jv=findViewById(R.id.btn_call_jv);
        btn_email_jv=findViewById(R.id.btn_email_jv);
        button_job_edit=findViewById(R.id.button_job_edit);





        databaseReference = FirebaseDatabase.getInstance().getReference().child("create_job").child("5yQhUWmMuCeXFBdbRi6htxb2Nhs2").child("1");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null && snapshot.exists()){
                    jobtitle.setText(snapshot.child("title").getValue().toString());
                    tv_location_job.setText(snapshot.child("district").getValue().toString());
                    jobType_tv.setText(snapshot.child("job_type").getValue().toString());
                    jobCompany_tv.setText(snapshot.child("name").getValue().toString());
                  job_salary_tv.setText(snapshot.child("salary1").getValue().toString());
                    jobPostDate_tv.setText(snapshot.child("date").getValue().toString());
                    jobEmail_tv.setText(snapshot.child("email1").getValue().toString());
                    job_mobile_tv.setText(snapshot.child("phone1").getValue().toString());

                    job_mobile_tv.setVisibility(View.INVISIBLE);


                    jobDescription_tv.setText(snapshot.child("description").getValue().toString());
                    imageUrl=(snapshot.child("img").getValue().toString());

                    Picasso.get().load(imageUrl).resize(350,150).centerCrop().into(img_compny);




                }

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });



        btn_call_jv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + job_mobile_tv.getText().toString()));
                startActivity(intent);
            }
        });

        //send email button function
        btn_email_jv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriText =
                        "mailto:"+jobEmail_tv.getText().toString() +
                                "?subject=" + Uri.encode("JobSeeker : "+jobtitle.getText().toString()) +
                                "&body=" + Uri.encode("");

                Uri uri = Uri.parse(uriText);

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);

                    startActivity(Intent.createChooser(sendIntent, "Send email"));

            }
        });

        button_job_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditJob.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("JOBTITLE",jobtitle.getText().toString());
                intent.putExtra("LOCATION",tv_location_job.getText().toString());
                intent.putExtra("JOBTYPE",jobType_tv.getText().toString());
                intent.putExtra("JOBCOMPANY",jobCompany_tv.getText().toString());
                intent.putExtra("SALARY",job_salary_tv.getText().toString());
                intent.putExtra("POSTDATE",jobPostDate_tv.getText().toString());
                intent.putExtra("EMAIL",jobEmail_tv.getText().toString());
                intent.putExtra("MOBILE",job_mobile_tv.getText().toString());
                intent.putExtra("DESCRIPTION",jobDescription_tv.getText().toString());
                intent.putExtra("Imageurl",imageUrl);


                startActivity(intent);
                //first passing value entering value and then the exit value
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);




            }
        });








    }



}