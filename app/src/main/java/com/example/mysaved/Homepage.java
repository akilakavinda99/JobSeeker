package com.example.mysaved;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Homepage extends AppCompatActivity {



    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("create_job");
    private ViewHolder_Homepage viewHolder_homepage,member;
    private ArrayList<HomeList> list;
    private DatabaseReference fvrtref,fvrt_listRef;
    //fvrtref for imagebutton (bookmark check or not)
    //fvrt_listRef for privacy
    private Boolean fvrtChecker = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toast.makeText(Homepage.this, "Welcome", Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.d_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Homepage.this));



        list = new ArrayList<>();
        viewHolder_homepage = new ViewHolder_Homepage(Homepage.this , list);




        recyclerView.setAdapter(viewHolder_homepage);

        //use value event listener instead of child event listner
        root.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HomeList homelist = dataSnapshot.getValue(HomeList.class);
                    list.add(homelist);
                }
                viewHolder_homepage.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
