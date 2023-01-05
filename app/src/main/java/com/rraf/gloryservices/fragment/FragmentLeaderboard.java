package com.rraf.gloryservices.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapteLeaderBoard;
import com.rraf.gloryservices.adaptor.LeaderboardClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FragmentLeaderboard extends Fragment {

    RecyclerView recv;
    AdapteLeaderBoard adapter;
    ArrayList<LeaderboardClass> lists;
    DatabaseReference db, dbS;

    public FragmentLeaderboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pointSystem();
        TextView topf = view.findViewById(R.id.tv_top);
        topf.setText("-");
        db = FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima");
        recv = view.findViewById(R.id.recv_leaderboard);
        recv.setLayoutManager(new LinearLayoutManager(getContext()));
        recv.setHasFixedSize(true);
        lists = new ArrayList<>();
        adapter = new AdapteLeaderBoard(getContext(), lists);
        recv.setAdapter(adapter);
        db.orderByChild("nama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lists.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    lists.add(ds.getValue(LeaderboardClass.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void pointSystem(){
        int point = 5000;
        Map<String, Object> map = new HashMap<>();
        map.put("point",point);
        db = FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima");
        dbS = FirebaseDatabase.getInstance().getReference("Service").child("dataService");
            if(dbS.child("iStatus").get().toString().equals("LUNAS")){
                db.child(dbS.child("iPenerima").get().toString()).updateChildren(map);
                Toast.makeText(getContext(), "Point Berhasil di tambahkan", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "gagal memuat Point", Toast.LENGTH_SHORT).show();
            }
    }
}