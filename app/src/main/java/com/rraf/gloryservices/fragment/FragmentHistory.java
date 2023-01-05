package com.rraf.gloryservices.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapterDataServiceHistory;
import com.rraf.gloryservices.adaptor.OutputClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FragmentHistory extends Fragment {

    RecyclerView recv;
    AdapterDataServiceHistory adapter;
    ArrayList<OutputClass> list;
    DatabaseReference db;
    CardView CL, CC, CP;

    public FragmentHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference("Service").child("dataService");
        recv = view.findViewById(R.id.recycleView_service);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recv.setLayoutManager(lm);
        recv.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new AdapterDataServiceHistory(getContext(), list);
        recv.setAdapter(adapter);
        db.orderByChild("iTgl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    list.add(ds.getValue(OutputClass.class));
                        adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        CL = view.findViewById(R.id.CVl);
        CL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.orderByChild("iStatus").equalTo("LUNAS").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if(snapshot.exists()){
                        for(DataSnapshot ds : snapshot.getChildren()){
                            OutputClass oclas = ds.getValue(OutputClass.class);
                            list.add(oclas);
                            adapter.notifyDataSetChanged();
                        }
                        }else {
                            Toast.makeText(getContext(), "Data Belum Ada Yang Lunas", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        CC = view.findViewById(R.id.CVc);
        CC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.orderByChild("iStatus").equalTo("CANCELED").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if(snapshot.exists()){
                        for(DataSnapshot ds : snapshot.getChildren()){
                            OutputClass oclass = ds.getValue(OutputClass.class);
                            list.add(oclass);
                            adapter.notifyDataSetChanged();
                        }
                        }else{
                            Toast.makeText(getContext(), "Tidak Ada Yang Canceled", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}