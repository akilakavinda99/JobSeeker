package com.example.mysaved;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyRequestedJobs extends AppCompatActivity {

    TextView requestlist;
    Button new_btn;
    RecyclerView recyclerView;
    FirebaseAuth fAuth;
    String userID;

    private RequestAdapter adapter;
    private ArrayList<ModelRequest> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requested_jobs);

        setContentView(R.layout.activity_my_requested_jobs);
        //get userID
        fAuth = FirebaseAuth.getInstance();
        requestlist = findViewById(R.id.tv_mrj);
        new_btn = findViewById(R.id.btn_newrequest);

        //Check user already login
        if (fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), UserloginActivity.class));
        }

        new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), CreateJob.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                else {
                    startActivity(new Intent(getApplicationContext(), UserloginActivity.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }

            }
        });

        userID = fAuth.getCurrentUser().getUid();
        recyclerView = findViewById(R.id.my_requested_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MyRequestedJobs.this,2));

        list = new ArrayList<>();
        adapter = new RequestAdapter(MyRequestedJobs.this,list);

        recyclerView.setAdapter(adapter);

        DatabaseReference root = FirebaseDatabase.getInstance().getReference("job_request").child(userID);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    String ReqId = ds.getKey();
                    String name = ds.child("name").getValue(String.class);
                    String title = ds.child("title").getValue(String.class);
                    String c_age1 = ds.child("c_age1").getValue(String.class);
                    String img = ds.child("img").getValue(String.class);
                    String email1 = ds.child("email1").getValue(String.class);
                    String phone1 = ds.child("phone1").getValue(String.class);
                    String gender = ds.child("gender").getValue(String.class);
                    ModelRequest modelRequest = new ModelRequest(ReqId,name,title,c_age1,img,email1,phone1,gender);
                    list.add(modelRequest);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}