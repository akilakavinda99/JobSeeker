package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NewReqJob_Homepage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference root;
    private ViewHolder_ReqHomepage viewHolder_reqHomepage;
    private ArrayList<ReqJobList> list;
    private Button saveReqjobs;
    private SearchView searchView;
    private ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_req_job_homepage);

        load = findViewById(R.id.d_progressBar3);

        saveReqjobs = findViewById(R.id.d_reqtest_btn);
        saveReqjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser()!= null) {
                    startActivity(new Intent(getApplicationContext(), New_SaveReq_Jobs.class));
                }else{
                    Toast.makeText(NewReqJob_Homepage.this, "Please Login First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView = findViewById(R.id.d_reqrecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(NewReqJob_Homepage.this));

        list = new ArrayList<>();


        root = FirebaseDatabase.getInstance().getReference();

        root.child("job_request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String reqid = dataSnapshot.getKey();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String reqjobid = ds.getKey();
                        String date =ds.child("date").getValue(String.class);
                        String name = ds.child("name").getValue(String.class);
                        String title = ds.child("title").getValue(String.class);
                        String phone1 = ds.child("phone1").getValue(String.class);
                        String c_age1 = ds.child("c_age1").getValue(String.class);
                        String description = ds.child("description").getValue(String.class);
                        String email1 = ds.child("email1").getValue(String.class);
                        String gender = ds.child("gender").getValue(String.class);
                        String img = ds.child("img").getValue(String.class);

                        ReqJobList reqJobList = new ReqJobList(reqid,reqjobid,date,name,title,phone1,c_age1,description,email1,gender,img);
                        list.add(reqJobList);
                        load.setVisibility(View.INVISIBLE);

                     }

                    Collections.sort(list, new Comparator<ReqJobList>() {
                        @Override
                        public int compare(ReqJobList reqJobList, ReqJobList t1) {
                            return reqJobList.date.compareToIgnoreCase(t1.date);
                        }
                    });
                    Collections.reverse(list);
                    viewHolder_reqHomepage = new ViewHolder_ReqHomepage(NewReqJob_Homepage.this,list);

                    recyclerView.setAdapter(viewHolder_reqHomepage);
                    viewHolder_reqHomepage.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView = (SearchView) findViewById(R.id.d_reqsearch);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                viewHolder_reqHomepage.getFilter().filter(s);
                return false;
            }
        });
    }
}