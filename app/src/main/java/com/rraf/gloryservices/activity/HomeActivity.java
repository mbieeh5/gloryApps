package com.rraf.gloryservices.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rraf.gloryservices.Home;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapterDataService;
import com.rraf.gloryservices.adaptor.OutputClass;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity{

    RecyclerView recV;
    ArrayList<OutputClass> list;
    AdapterDataService adapter;
    DatabaseReference db;
    ImageView img;
    ImageFilterButton filter;
    SwipeRefreshLayout sr;
    TextView tvAtas;
    private Integer valueAtas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home2);
        img = findViewById(R.id.backbtn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
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
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(false);
        recV.setLayoutManager(layoutManager);
        recV.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new AdapterDataService(this, list);
        recV.setAdapter(adapter);
        final ProgressBar pb = findViewById(R.id.loading);
        sr = findViewById(R.id.refresh);
        DateTimeFormatter dtf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("d/M/yyyy");
        }
        LocalDate tanggals = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            tanggals = LocalDate.now();
        }
        String tanggal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            tanggal = tanggals.format(dtf);
        }
        db.orderByChild("iTgl").equalTo(tanggal).addValueEventListener(new ValueEventListener() {
        int total = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    total++;
                    pb.setVisibility(View.GONE);
                    OutputClass oclass = ds.getValue(OutputClass.class);
                    list.add(oclass);
                    adapter.notifyDataSetChanged();
                    sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            list.add(oclass);
                            sr.setRefreshing(false);
                        }
                    });
                        tvAtas = findViewById(R.id.titleAtas);
                            if(tvAtas != null){
                                tvAtas.setText(getString(R.string.total_data_service, total));
                            }
                }}else{
                    sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            snapshot.exists();
                            Toast.makeText(HomeActivity.this, "Tidak Ada Service Hari Ini", Toast.LENGTH_SHORT).show();
                    sr.setRefreshing(false);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void muncul() {
        DialogPlus dialogPlus = DialogPlus.newDialog(HomeActivity.this)
                .setGravity(Gravity.CENTER)
                .setMargin(50, 0, 50, 0)
                .setContentHolder(new ViewHolder(R.layout.sort_item_edit))
                .setExpanded(false)
                .create();
        View a = (LinearLayout) dialogPlus.getHolderView();
        dialogPlus.show();
        TextView fTgl = a.findViewById(R.id.fTgl);
        fTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog =  new DatePickerDialog(
                        HomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        fTgl.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        final ProgressBar pb = findViewById(R.id.loading);
        Button btnCari = a.findViewById(R.id.btnCariType);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                Query query = db.orderByChild("iHp");
                query.addValueEventListener(new ValueEventListener() {
                int total = 0;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pb.setVisibility(View.GONE);
                        list.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            OutputClass oclass = ds.getValue(OutputClass.class);
                            if(oclass != null){
                                oclass.setiHp(oclass.getiHp().toUpperCase());
                                list.add(oclass);
                                total++;
                            }
                            tvAtas = findViewById(R.id.titleAtas);
                                if(tvAtas != null){
                                    tvAtas.setText(getString(R.string.total_data_service, total));
                                }
                        dialogPlus.dismiss();
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        Button cari = a.findViewById(R.id.btnCari);
        cari.setOnClickListener(new View.OnClickListener() {
            int total = 0;
            @Override
            public void onClick(View v) {
                list.removeAll(list);
                Query query = db.orderByChild("iTgl").equalTo(fTgl.getText().toString());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                                list.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                OutputClass oclass = ds.getValue(OutputClass.class);
                                list.add(oclass);
                                total++;
                                dialogPlus.dismiss();
                            }
                            tvAtas = findViewById(R.id.titleAtas);
                            if(tvAtas != null){
                                tvAtas.setText(getString(R.string.total_data_service, total));
                            }
                        }else {
                            Toast.makeText(HomeActivity.this, "Data Tidak DiTemukan", Toast.LENGTH_SHORT).show();
                        }
                    adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}