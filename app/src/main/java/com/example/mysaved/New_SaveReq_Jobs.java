package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class New_SaveReq_Jobs extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference root;
    private DatabaseReference dbcount;
    private ViewHolder_SaveReqJob viewHolder_saveReqJob;
    private ArrayList<ReqJobList> list;
    private String currentUserId;
    private FirebaseAuth auth2;
    private Button back;
    private TextView savejobs;
    private int count = 0;
    private ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_save_req_jobs);

        load = findViewById(R.id.d_progressBar2);
        back = findViewById(R.id.d_reqtest_btn2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewReqJob_Homepage.class));
                finish();
            }
        });

        auth2 = FirebaseAuth.getInstance();
        currentUserId = auth2.getCurrentUser().getUid();
        savejobs = findViewById(R.id.tv_reqcount);
        dbcount = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);

        dbcount.child("SaveRequestedjobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    count = (int) snapshot.getChildrenCount();
                    savejobs.setText(Integer.toString(count));

                }else {
                    savejobs.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.d_reqrecycle2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        viewHolder_saveReqJob = new ViewHolder_SaveReqJob(this,list);

        recyclerView.setAdapter(viewHolder_saveReqJob);

        root = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);

        root.child("SaveRequestedjobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String reqid = dataSnapshot.child("reqid").getValue(String.class);
                    String reqjobid = dataSnapshot.child("reqjobid").getValue(String.class);
                    String date =dataSnapshot.child("date").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String phone1 = dataSnapshot.child("phone1").getValue(String.class);
                    String c_age1 = dataSnapshot.child("c_age1").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String email1 = dataSnapshot.child("email1").getValue(String.class);
                    String gender = dataSnapshot.child("gender").getValue(String.class);
                    String img = dataSnapshot.child("img").getValue(String.class);

                    ReqJobList reqJobList = new ReqJobList(reqid,reqjobid,date,name,title,phone1,c_age1,description,email1,gender,img);
                    list.add(reqJobList);
                    load.setVisibility(View.INVISIBLE);
                }
                viewHolder_saveReqJob.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}