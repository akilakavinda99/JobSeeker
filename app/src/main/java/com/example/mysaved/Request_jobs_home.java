package com.example.mysaved;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Request_jobs_home extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_jobs_home);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference().child("user");
        reference.setValue("Testing Database");
    }
}