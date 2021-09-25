package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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

public class ViewjobM extends AppCompatActivity {
    TextView jobTitle_tv, jobLocation_tv, jobType_tv_jobView, jobCompany_tv_jobView, jobSalary_tv_jobView, jobViews_tv_jobView,  jobDescription_tv_jobView,
            jobEmail_tv_jobView, jobDate_tv_jobView,jobDaysAgo_tv_jobView,textView2;
    ImageView jobImage_tv_jobView,backbtn_img;

    Button btn_email_jv2, btn_call_jv4,Edit_JobView_btn;
    DatabaseReference databaseReference;
    String job_id;
    String imageUrl;
    String userid;
    FirebaseAuth fAuth;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    Date postDate, currentDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewjob_m);

     userid = getIntent().getStringExtra("user_id");
        String jobid = getIntent().getStringExtra("job_id");

        jobTitle_tv = findViewById(R.id.jobTitle_tv);
        jobLocation_tv = findViewById(R.id.jobLocation_tv);
        jobType_tv_jobView = findViewById(R.id.jobType_tv_jobView);
        jobCompany_tv_jobView = findViewById(R.id.jobCompany_tv_jobView);
        jobSalary_tv_jobView = findViewById(R.id.jobSalary_tv_jobView);
        jobViews_tv_jobView = findViewById(R.id.jobViews_tv_jobView);
        jobDescription_tv_jobView = findViewById(R.id.jobDescription_tv_jobView);
        jobEmail_tv_jobView = findViewById(R.id.jobEmail_tv_jobView);
        jobDate_tv_jobView = findViewById(R.id.jobDate_tv_jobView);
        jobDaysAgo_tv_jobView=findViewById(R.id.jobDaysAgo_tv_jobView);
        Edit_JobView_btn=findViewById(R.id.Edit_JobView_btn);
        textView2=findViewById(R.id.textView2);
        backbtn_img=findViewById(R.id.imageView22);

        jobDescription_tv_jobView.setMovementMethod(new ScrollingMovementMethod());


        try {
            currentDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        jobImage_tv_jobView = findViewById(R.id.jobImage_tv_jobView);


        btn_email_jv2 = findViewById(R.id.btn_email_jv2);
        btn_call_jv4 = findViewById(R.id.btn_call_jv4);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("create_job").child(userid).child(jobid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null && snapshot.exists()) {
                    jobTitle_tv.setText(snapshot.child("title").getValue().toString());
                    jobLocation_tv.setText(snapshot.child("district").getValue().toString());
                    jobType_tv_jobView.setText(snapshot.child("job_type").getValue().toString());
                    jobCompany_tv_jobView.setText(snapshot.child("name").getValue().toString());
                    jobSalary_tv_jobView.setText(snapshot.child("salary1").getValue().toString());
                    jobDate_tv_jobView.setText(snapshot.child("date").getValue().toString());
                    jobEmail_tv_jobView.setText(snapshot.child("email1").getValue().toString());
                    textView2.setText(snapshot.child("phone1").getValue().toString());

                   textView2.setVisibility(View.INVISIBLE);


                    jobDescription_tv_jobView.setText(snapshot.child("description").getValue().toString());
                    imageUrl = (snapshot.child("img").getValue().toString());

                    Picasso.get().load(imageUrl).fit().into(jobImage_tv_jobView);

                    try {
                        postDate = sdf.parse(Objects.requireNonNull(snapshot.child("date").getValue()).toString());
                        dateDifferent(postDate, currentDate );


                        System.out.println(postDate+"sdsddfdf");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    fAuth = FirebaseAuth.getInstance();
                    showDifference();

//                    resize(2000,1000)

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_call_jv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + textView2.getText().toString()));
                startActivity(intent);
            }
        });

        //send email button function
        btn_email_jv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriText =
                        "mailto:" + jobEmail_tv_jobView.getText().toString() +
                                "?subject=" + Uri.encode("JobSeeker : " + jobTitle_tv.getText().toString()) +
                                "&body=" + Uri.encode("");

                Uri uri = Uri.parse(uriText);

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);

                startActivity(Intent.createChooser(sendIntent, "Send email"));

            }
        });

        backbtn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Homepage_new.class));
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });

        Edit_JobView_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditJob.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("UserID",userid);
                intent.putExtra("JobID",jobid);
                intent.putExtra("JOBTITLE", jobTitle_tv.getText().toString());
                intent.putExtra("LOCATION",jobLocation_tv.getText().toString());
                intent.putExtra("JOBTYPE", jobType_tv_jobView.getText().toString());
                intent.putExtra("JOBCOMPANY",jobCompany_tv_jobView.getText().toString());
                intent.putExtra("SALARY",jobSalary_tv_jobView.getText().toString());
                intent.putExtra("POSTDATE", jobDate_tv_jobView.getText().toString());
                intent.putExtra("EMAIL",jobEmail_tv_jobView.getText().toString());
                intent.putExtra("MOBILE",textView2.getText().toString());
                intent.putExtra("DESCRIPTION",jobDescription_tv_jobView.getText().toString());
                intent.putExtra("Imageurl",imageUrl);


                startActivity(intent);
                //first passing value entering value and then the exit value
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);




            }
        });


    }

    public Long dateDifferent(Date postDate, Date currentDate) {
        long diff =currentDate.getTime() - postDate.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long difference = time.convert(diff, TimeUnit.MILLISECONDS);
//        System.out.println(diffrence+"\n"+diff+"\n"+pdate+"\n"+cdate);
        System.out.println(postDate+"\n"+currentDate);

        Log.d("DATE",postDate+""+currentDate);

        return difference;

    }

    public void showDifference() {
        if (dateDifferent(postDate, currentDate) == 0) {
            jobDaysAgo_tv_jobView.setText("Less than 24hrs ago");
        } else if (dateDifferent(postDate, currentDate) == 1) {
            jobDaysAgo_tv_jobView.setText(dateDifferent(postDate, currentDate).toString() + " Day ago");
        } else {
            jobDaysAgo_tv_jobView.setText(dateDifferent(postDate, currentDate).toString() + " Days ago");
        }

        if (fAuth.getCurrentUser() != null)
            if ( fAuth.getCurrentUser().getUid().equals(userid)){
                Edit_JobView_btn.setVisibility(View.VISIBLE);
            } else Edit_JobView_btn.setVisibility(View.INVISIBLE);

        //check if user is already logged in
        if (fAuth.getCurrentUser() == null){
            Edit_JobView_btn.setVisibility(View.INVISIBLE);
        }
    }
}

