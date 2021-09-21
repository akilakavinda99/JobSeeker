package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Objects;

public class MyJobListings extends AppCompatActivity {

    TextView joblist;
    Button create_btn;
    RecyclerView recyclerView;
    FirebaseAuth fAuth;
    String userID;

    private MyAdapter adapter;
    private ArrayList<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_job_listings);
        fAuth = FirebaseAuth.getInstance();
        joblist = findViewById(R.id.tv_mrjl);
        create_btn = findViewById(R.id.btn_newreq);

        if (fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), UserloginActivity.class));
        }

        create_btn.setOnClickListener(new View.OnClickListener() {
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
        recyclerView = findViewById(R.id.my_listing_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyJobListings.this));

        list = new ArrayList<>();
        adapter = new MyAdapter(MyJobListings.this,list);

        recyclerView.setAdapter(adapter);

        DatabaseReference root = FirebaseDatabase.getInstance().getReference("create_job").child(userID);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                       String JobID = ds.getKey();
                       String name = ds.child("name").getValue(String.class);
                       String title = ds.child("title").getValue(String.class);
                       String job_type = ds.child("job_type").getValue(String.class);
                       String img = ds.child("img").getValue(String.class);
                       String salary1 = ds.child("salary1").getValue(String.class);
                       String phone1 = ds.child("phone1").getValue(String.class);
                       String district = ds.child("district").getValue(String.class);
                       Model model = new Model(JobID,name,title,job_type,img,salary1,phone1,district);
                       list.add(model);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}