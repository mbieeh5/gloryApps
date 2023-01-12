package com.rraf.gloryservices.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapteLeaderBoard;
import com.rraf.gloryservices.adaptor.LeaderboardClass;
import com.rraf.gloryservices.adaptor.PointSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        TextView topf = view.findViewById(R.id.tv_top);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima");
        ref.orderByChild("point").limitToFirst(6).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> topPlayers = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("nama").getValue(String.class);
                    Integer point = ds.child("point").getValue(Integer.class);
                    if(point != null){
                        if(point > 0){
                            topf.setText(name);
                        }else{
                            topf.setText("-");
                        }
                    }
                }
                // Update the UI with the top players
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        db = FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima");
        recv = view.findViewById(R.id.recv_leaderboard);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setStackFromEnd(false);
        lm.setReverseLayout(false);
        recv.setLayoutManager(lm);
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
}