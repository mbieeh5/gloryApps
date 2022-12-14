package com.rraf.gloryservices.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapterDataService;
import com.rraf.gloryservices.adaptor.OutputClass;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recV;
    ArrayList<OutputClass> list;
    AdapterDataService adapter;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home2);

        recV = findViewById(R.id.recycleView_service);
        db = FirebaseDatabase.getInstance().getReference("Service").child("dataService");
        recV.setHasFixedSize(true);
        recV.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AdapterDataService(this, list);
        recV.setAdapter(adapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){
                    OutputClass oclass = ds.getValue(OutputClass.class);
                    list.add(oclass);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}