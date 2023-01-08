package com.rraf.gloryservices.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapterDataTransfer;
import com.rraf.gloryservices.adaptor.OutputTfClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class EditTransferActivity extends AppCompatActivity{

    RecyclerView recV;
    ArrayList<OutputTfClass> listTf;
    AdapterDataTransfer adapter;
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
        db = FirebaseDatabase.getInstance().getReference("Transfer").child("dataTransfer");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(false);
        recV.setLayoutManager(layoutManager);
        recV.setHasFixedSize(true);
        listTf = new ArrayList<>();
        adapter = new AdapterDataTransfer(this, listTf);
        recV.setAdapter(adapter);
        sr = findViewById(R.id.refresh);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate tanggals = LocalDate.now();
        String tanggal = tanggals.format(dtf);
        db.orderByChild("iTgl").equalTo(tanggal).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    listTf.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    OutputTfClass oclass = ds.getValue(OutputTfClass.class);
                    listTf.add(oclass);
                    adapter.notifyDataSetChanged();
                    sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            listTf.add(oclass);
                            sr.setRefreshing(false);
                        }
                    });
                }}else{
                    sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            snapshot.exists();
                            Toast.makeText(EditTransferActivity.this, "Tidak Ada Transfer Hari Ini", Toast.LENGTH_SHORT).show();
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
        DialogPlus dialogPlus = DialogPlus.newDialog(EditTransferActivity.this)
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
                        EditTransferActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        Button btnCari = a.findViewById(R.id.btnCariType);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTf.removeAll(listTf);
                Query query = db.orderByChild("iBank").startAt("Aa").endAt("zZ");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            OutputTfClass oclass = ds.getValue(OutputTfClass.class);
                            listTf.add(oclass);
                            dialogPlus.dismiss();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        Button cari = a.findViewById(R.id.btnCari);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTf.removeAll(listTf);
                Query query = db.orderByChild("iTgl").equalTo(fTgl.getText().toString());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                                listTf.clear();
                            for(DataSnapshot ds : snapshot.getChildren()){
                                OutputTfClass oclass = ds.getValue(OutputTfClass.class);
                                listTf.add(oclass);
                                dialogPlus.dismiss();
                            }
                        }else {
                            Toast.makeText(EditTransferActivity.this, "Data Tidak DiTemukan", Toast.LENGTH_SHORT).show();
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