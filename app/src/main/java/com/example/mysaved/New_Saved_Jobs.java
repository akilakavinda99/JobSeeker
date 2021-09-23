package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class New_Saved_Jobs extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference root;
    private DatabaseReference dbcount;
    private ViewHolder_SaveJobs viewHolder_saveJobs;
    private ArrayList<HomeList> list;
    private String currentUserId;
    private FirebaseAuth auth2;
    private Button back;
    private TextView savejobs;
    private int count = 0;
    private ProgressBar load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_saved_jobs);

        load = findViewById(R.id.d_progressbar);

        back = findViewById(R.id.d_test_btn2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Homepage_new.class));
            }
        });



        auth2 = FirebaseAuth.getInstance();
        currentUserId = auth2.getCurrentUser().getUid();
        savejobs = findViewById(R.id.tv_count);
        dbcount = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);


        dbcount.child("savejobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        count =(int) snapshot.getChildrenCount();
                        savejobs.setText(Integer.toString(count));
                        System.out.println(count);
                    } else {
                       savejobs.setText("0");
                   }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.d_recycle2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        viewHolder_saveJobs = new ViewHolder_SaveJobs(this,list);


        recyclerView.setAdapter(viewHolder_saveJobs);
        root = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);

        root.child("savejobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String id = dataSnapshot.child("id").getValue(String.class);
                    String jobid = dataSnapshot.child("jobid").getValue(String.class);
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String salary1 = dataSnapshot.child("salary1").getValue(String.class);
                    String job_type = dataSnapshot.child("job_type").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String email1 = dataSnapshot.child("email1").getValue(String.class);
                    String phone1 = dataSnapshot.child("phone1").getValue(String.class);
                    String district = dataSnapshot.child("district").getValue(String.class);
                    String img = dataSnapshot.child("img").getValue(String.class);

                    HomeList home = new HomeList(id,jobid,date,name,title,salary1,job_type,description,email1,phone1,district,img);
                    list.add(home);
                    load.setVisibility(View.INVISIBLE);

//                    for(DataSnapshot ds : dataSnapshot.getChildren()){
//                        String jobid = ds.getKey();
//                        String name = ds.child("name").getValue(String.class);
//                        String title = ds.child("title").getValue(String.class);
//                        String salary1 = ds.child("salary1").getValue(String.class);
//                        String job_type = ds.child("job_type").getValue(String.class);
//                        String description = ds.child("description").getValue(String.class);
//                        String email1 = ds.child("email1").getValue(String.class);
//                        String phone1 = ds.child("phone1").getValue(String.class);
//                        String district = ds.child("district").getValue(String.class);
//                        String img = ds.child("img").getValue(String.class);
//
//                        HomeList home = new HomeList(id,jobid,name,title,salary1,job_type,description,email1,phone1,district,img);
//                        list.add(home);
//                    }
                }
                viewHolder_saveJobs.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });


    }
}