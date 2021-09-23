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
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Homepage_new extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference root;
    private ViewHolder_Homepage viewHolder_homepage;
    private ArrayList<HomeList> list;
    private Button savejobs;
//    private CheckBox save;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_new);


        savejobs = findViewById(R.id.d_test_btn);
        savejobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(), New_Saved_Jobs.class));

                }else{
                    Toast.makeText(Homepage_new.this,"Please Login!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
        Toast.makeText(Homepage_new.this, "Welcome", Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.d_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Homepage_new.this));

        list = new ArrayList<>();


        DatabaseReference dbsave = FirebaseDatabase.getInstance().getReference("user")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("savejobs");

//        save = findViewById(R.id.d_img_bookmark);
//        save.setChecked(true);

        root = FirebaseDatabase.getInstance().getReference();

        root.child("create_job").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.getKey();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String jobid = ds.getKey();
                        String name = ds.child("name").getValue(String.class);
                        String title = ds.child("title").getValue(String.class);
                        String salary1 = ds.child("salary1").getValue(String.class);
                        String job_type = ds.child("job_type").getValue(String.class);
                        String description = ds.child("description").getValue(String.class);
                        String email1 = ds.child("email1").getValue(String.class);
                        String phone1 = ds.child("phone1").getValue(String.class);
                        String district = ds.child("district").getValue(String.class);
                        String img = ds.child("img").getValue(String.class);

                        HomeList home = new HomeList(id, jobid, name, title, salary1, job_type, description, email1, phone1, district, img);
                        list.add(home);
                    }
                    viewHolder_homepage = new ViewHolder_Homepage(Homepage_new.this, list);

                    recyclerView.setAdapter(viewHolder_homepage);
                    viewHolder_homepage.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search = (SearchView) findViewById(R.id.d_search);
        search.setImeOptions(EditorInfo.IME_ACTION_DONE);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                viewHolder_homepage.getFilter().filter(s);
                System.out.println(s);
                return false;
            }
        });
    }
}