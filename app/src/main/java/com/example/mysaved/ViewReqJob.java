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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Objects;

public class ViewReqJob extends AppCompatActivity {
    ImageView ReqJobImage,imageView20;
    TextView tv_req_job_title, ReqJobName, ReqJobAge, ReqJobGender, ReqJobDescription;
    Button btn_emaill_jv3, btn_call_jvr;
    DatabaseReference databaseReference;
    String imageUrl,userid,jobid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userid = getIntent().getStringExtra("user_id");
        jobid = getIntent().getStringExtra("job_id");
        setContentView(R.layout.activity_view_req_job);
        ReqJobImage = findViewById(R.id.imageView2);
        tv_req_job_title = findViewById(R.id.tv_req_job_title);
        ReqJobName = findViewById(R.id.textView6);
        ReqJobAge = findViewById(R.id.textView7);
        ReqJobGender = findViewById(R.id.textView8);
        ReqJobDescription = findViewById(R.id.textView10);
        btn_emaill_jv3 = findViewById(R.id.btn_emaill_jv3);
        btn_call_jvr = findViewById(R.id.btn_call_jvr);
        imageView20 = findViewById(R.id.imageView20);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("job_request").child(userid).child(jobid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null && snapshot.exists()) {
                    tv_req_job_title.setText(snapshot.child("title").getValue().toString());
                    ReqJobName.setText(snapshot.child("name").getValue().toString());
                    ReqJobAge.setText(snapshot.child("c_age1").getValue().toString());
                    ReqJobGender.setText(snapshot.child("gender").getValue().toString());
                    ReqJobDescription.setText(snapshot.child("description").getValue().toString());


                    imageUrl = (snapshot.child("img").getValue().toString());

                    Picasso.get().load(imageUrl).fit().into(ReqJobImage);


//                    resize(2000,1000)

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//
//        btn_call_jv4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + textView2.getText().toString()));
//                startActivity(intent);
//            }
//        });
//
//        //send email button function
//        btn_email_jv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String uriText =
//                        "mailto:" + jobEmail_tv_jobView.getText().toString() +
//                                "?subject=" + Uri.encode("JobSeeker : " + jobTitle_tv.getText().toString()) +
//                                "&body=" + Uri.encode("");
//
//                Uri uri = Uri.parse(uriText);
//
//                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
//                sendIntent.setData(uri);
//
//                startActivity(Intent.createChooser(sendIntent, "Send email"));
//
//            }
//        });

//        Edit_JobView_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), EditJob.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("UserID",userid);
//                intent.putExtra("JobID",jobid);
//                intent.putExtra("JOBTITLE", jobTitle_tv.getText().toString());
//                intent.putExtra("LOCATION",jobLocation_tv.getText().toString());
//                intent.putExtra("JOBTYPE", jobType_tv_jobView.getText().toString());
//                intent.putExtra("JOBCOMPANY",jobCompany_tv_jobView.getText().toString());
//                intent.putExtra("SALARY",jobSalary_tv_jobView.getText().toString());
//                intent.putExtra("POSTDATE", jobDate_tv_jobView.getText().toString());
//                intent.putExtra("EMAIL",jobEmail_tv_jobView.getText().toString());
//                intent.putExtra("MOBILE",textView2.getText().toString());
//                intent.putExtra("DESCRIPTION",jobDescription_tv_jobView.getText().toString());
//                intent.putExtra("Imageurl",imageUrl);
//
//
//                startActivity(intent);
//                //first passing value entering value and then the exit value
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


    }
}




