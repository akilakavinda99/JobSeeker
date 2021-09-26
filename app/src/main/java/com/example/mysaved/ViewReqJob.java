package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

public class ViewReqJob extends AppCompatActivity {

    ImageView ReqJobImage,backbtn_img;
    TextView tv_req_job_title, ReqJobName, ReqJobAge, ReqJobGender, ReqJobDescription, tv_emailR, tv_phoneR,textView5;
    Button btn_emaill_jv3, btn_call_jvr, edit_reqJob_btn;

    DatabaseReference databaseReference;
    String imageUrl, userid, jobid;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userid = getIntent().getStringExtra("user_id");
        jobid = getIntent().getStringExtra("ReqId");
        setContentView(R.layout.activity_view_req_job);
        ReqJobImage = findViewById(R.id.imageView2);
        tv_req_job_title = findViewById(R.id.tv_req_job_title);
        ReqJobName = findViewById(R.id.textView6);
        ReqJobAge = findViewById(R.id.textView7);
        ReqJobGender = findViewById(R.id.textView8);
        ReqJobDescription = findViewById(R.id.tv_employee_description);
        btn_emaill_jv3 = findViewById(R.id.btn_emaill_jv3);
        btn_call_jvr = findViewById(R.id.btn_call_jvr);
        textView5 = findViewById(R.id.tv_requested_date);
        edit_reqJob_btn = findViewById(R.id.edit_reqJob_btn);
        tv_emailR = findViewById(R.id.tv_emailR);
        tv_phoneR = findViewById(R.id.tv_phoneR);
        backbtn_img=findViewById(R.id.imageView20);

        ReqJobDescription.setMovementMethod(new ScrollingMovementMethod());

        fAuth = FirebaseAuth.getInstance();




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
                    tv_phoneR.setText(snapshot.child("phone1").getValue().toString());
                    tv_emailR.setText(snapshot.child("email1").getValue().toString());
                    textView5.setText(snapshot.child("date").getValue().toString());


                    imageUrl = (snapshot.child("img").getValue().toString());

                    Picasso.get().load(imageUrl).fit().into(ReqJobImage);


//                    resize(2000,1000)

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if (fAuth.getCurrentUser() != null)
            if ( fAuth.getCurrentUser().getUid().equals(userid)){
                edit_reqJob_btn.setVisibility(View.VISIBLE);
            } else edit_reqJob_btn.setVisibility(View.INVISIBLE);

        //check if user is already logged in
        if (fAuth.getCurrentUser() == null){
            edit_reqJob_btn.setVisibility(View.INVISIBLE);
        }

        btn_call_jvr .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tv_phoneR.getText().toString()));
                startActivity(intent);
            }
        });

        //send email button function
        btn_emaill_jv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriText =
                        "mailto:" + tv_emailR.getText().toString() +
                                "?subject=" + Uri.encode("JobSeeker : " + tv_req_job_title.getText().toString()) +
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
                startActivity(new Intent(getApplicationContext(), NewReqJob_Homepage.class));
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            }
        });

        edit_reqJob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditJobReq.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("UserID", userid);
                intent.putExtra("JobID", jobid);
                intent.putExtra("JOBTITLE", tv_req_job_title.getText().toString());
                intent.putExtra("NAME", ReqJobName.getText().toString());
                intent.putExtra("AGE", ReqJobAge.getText().toString());
                intent.putExtra("GENDER", ReqJobGender.getText().toString());
                intent.putExtra("DESCRIPTION", ReqJobDescription.getText().toString());
                intent.putExtra("MOBILE", tv_phoneR.getText().toString());
                intent.putExtra("EMAIL", tv_emailR.getText().toString());
                intent.putExtra("DATE", textView5.getText().toString());
                intent.putExtra("imageurl", imageUrl);



                startActivity(intent);
                //first passing value entering value and then the exit value
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });
    }
}







