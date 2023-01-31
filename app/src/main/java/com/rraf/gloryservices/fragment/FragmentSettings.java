package com.rraf.gloryservices.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.LoginActivity;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapterDataPengaturan;
import com.rraf.gloryservices.adaptor.OutputPengaturan;
import com.rraf.gloryservices.adaptor.RecycleViewItemDecoration;
import com.rraf.gloryservices.adaptor.logoutcallback;

import java.util.ArrayList;

public class FragmentSettings extends Fragment implements logoutcallback {

    AdapterDataPengaturan adapter;
    ArrayList<OutputPengaturan> list;

    DatabaseReference dbSt;

    public FragmentSettings() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        TextView tvV = view.findViewById(R.id.idVersi);
        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            String version = packageInfo.versionName;
            tvV.setText(getString(R.string.version_666_666_666_beta,version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        RecyclerView recvS = view.findViewById(R.id.recv_settings);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setStackFromEnd(false);
        lm.setReverseLayout(false);
        recvS.setLayoutManager(lm);
        recvS.addItemDecoration(new RecycleViewItemDecoration(-20));
        recvS.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new AdapterDataPengaturan(getContext(), this, list);
        recvS.setAdapter(adapter);
        dbSt = FirebaseDatabase.getInstance().getReference("Data").child("pengaturan");
        dbSt.orderByChild("pengaturan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    list.clear();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        list.add(ds.getValue(OutputPengaturan.class));
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getContext(), "Gagal Memuat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    @Override
    public void onLogout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}