package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class New_Saved_Jobs extends AppCompatActivity {

    Button nav_logout,nav_login;
    DrawerLayout drawerLayout;
    TextView nav_home_txt, nav_savedreqjobs_txt, nav_myjobs_txt, nav_requestedjobs_txt, nav_myreqjobs_txt,
            nav_savedjobs_txt, nav_profile_txt;
    FirebaseAuth fAuth;
    String userID;

    private RecyclerView recyclerView;
    private DatabaseReference root;
    private DatabaseReference dbcount;
    private ViewHolder_SaveJobs viewHolder_saveJobs;
    private ArrayList<HomeList> list;
    private String currentUserId;
    private FirebaseAuth auth2;
    private TextView savejobs;
    private int count = 0;
    private ProgressBar load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_saved_jobs);


        drawerLayout = findViewById(R.id.drawer_layout);

        nav_login = findViewById(R.id.btn_nav_login);
        nav_logout = findViewById(R.id.btn_nav_logout);

        nav_home_txt = findViewById(R.id.tv_nav_home);
        nav_myjobs_txt = findViewById(R.id.tv_nav_myjobs);
        nav_savedjobs_txt = findViewById(R.id.tv_nav_savedjobs);
        nav_requestedjobs_txt = findViewById(R.id.tv_nav_requestedjobs);
        nav_myreqjobs_txt = findViewById(R.id.tv_nav_myreqjobs);
        nav_savedreqjobs_txt = findViewById(R.id.tv_nav_savedreqjobs);
        nav_profile_txt = findViewById(R.id.tv_nav_profile);

        //get current instance of firebase authentication
        fAuth = FirebaseAuth.getInstance();

        //check if user is already logged in
        if (fAuth.getCurrentUser() == null){
            Intent i = new Intent(getApplicationContext(), UserloginActivity.class);
            startActivity(i);
        } else {
            userID = fAuth.getCurrentUser().getUid();
            nav_login.setVisibility(View.INVISIBLE);
            nav_logout.setVisibility(View.VISIBLE);


            auth2 = FirebaseAuth.getInstance();
            currentUserId = auth2.getCurrentUser().getUid();
            savejobs = findViewById(R.id.tv_count);
            dbcount = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);


            dbcount.child("savejobs").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        count = (int) snapshot.getChildrenCount();
                        savejobs.setText(Integer.toString(count));
                        System.out.println(count);
                    } else {
                        savejobs.setText("0");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("The read failed: " + error.getCode());
                    Toast.makeText(New_Saved_Jobs.this, "DataBase Error try again", Toast.LENGTH_SHORT).show();
                }
            });

            recyclerView = findViewById(R.id.d_recycle2);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            list = new ArrayList<>();
            viewHolder_saveJobs = new ViewHolder_SaveJobs(this, list);


            recyclerView.setAdapter(viewHolder_saveJobs);
            root = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);

            root.child("savejobs").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

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

                        HomeList home = new HomeList(id, jobid, date, name, title, salary1, job_type, description, email1, phone1, district, img);
                        list.add(home);

                    }
                    viewHolder_saveJobs.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("The read failed: " + error.getCode());
                    Toast.makeText(New_Saved_Jobs.this, "DataBase Error try again", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
    public void ClickMenu(View view) {
        //open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer layer
        drawerLayout.openDrawer(GravityCompat.START);

    }

    //navigation drawer button functions
    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getApplicationContext(), Homepage_new.class);
        startActivity(i);

    }
    //side_nav buttons
    public void navClickHome(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), Homepage_new.class));


    }

    public void navClickMyjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), MyJobListings.class));

    }

    public void navClickSavedjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), New_Saved_Jobs.class));

    }

    public void navClickRequestedjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), NewReqJob_Homepage.class));

    }

    public void navClickMyreqjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), MyRequestedJobs.class));

    }

    public void navClickSavedReqjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), New_SaveReq_Jobs.class));

    }

    public void navClickProfile(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.black));

        startActivity(new Intent(getApplicationContext(), Profile.class));

    }
}