package com.rraf.gloryservices.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.Home;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapterDataService;
import com.rraf.gloryservices.adaptor.OutputClass;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recV;
    ArrayList<OutputClass> list;
    AdapterDataService adapter;
    DatabaseReference db;
    ImageView img;
    ImageFilterButton filter;
    SwipeRefreshLayout sr;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recV.setLayoutManager(layoutManager);
        recV.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new AdapterDataService(this, list);
        recV.setAdapter(adapter);
        sr = findViewById(R.id.refresh);
        db.orderByChild("iTgl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){
                    OutputClass oclass = ds.getValue(OutputClass.class);
                    list.add(oclass);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.add(oclass);
                finish();
                startActivity(getIntent());
                sr.setRefreshing(false);
            }
        });

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