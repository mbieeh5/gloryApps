package com.rraf.gloryservices.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.Home;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapterDataService;
import com.rraf.gloryservices.adaptor.OutputClass;
import com.rraf.gloryservices.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recV;
    ArrayList<OutputClass> list;
    AdapterDataService adapter;
    DatabaseReference db;
    ImageView img;
    ImageFilterButton filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home2);
        img = findViewById(R.id.backbtn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              kembali();
            }
        });

        filter = findViewById(R.id.filterButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muncul();
            }
        });

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

    private void kembali(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private void muncul() {
        Toast.makeText(this, "Sort Item Check", Toast.LENGTH_SHORT).show();
    }
}