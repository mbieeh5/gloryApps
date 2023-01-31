package com.rraf.gloryservices.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.activity.AddActivity;
import com.rraf.gloryservices.adaptor.AdapteLeaderBoard;
import com.rraf.gloryservices.adaptor.AdapterDataRedeem;
import com.rraf.gloryservices.adaptor.AdapterDataRedeemDonasi;
import com.rraf.gloryservices.adaptor.AdapterDataServiceHistory;
import com.rraf.gloryservices.adaptor.AdapterDataTransfer;
import com.rraf.gloryservices.adaptor.LeaderboardClass;
import com.rraf.gloryservices.adaptor.OutputClass;
import com.rraf.gloryservices.adaptor.OutputRedeemClass;
import com.rraf.gloryservices.adaptor.OutputRedeemDonasiClass;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentPoint extends Fragment {

    ArrayList<OutputRedeemClass> list;
    ArrayList<OutputRedeemDonasiClass> listD;
    ArrayAdapter<String> adapters;
    AdapterDataRedeem adapter;
    AdapterDataRedeemDonasi adapterD;
    DatabaseReference db, dbD, dbP;
    AutoCompleteTextView atek;
    //String[] items = {"aldi", "amri", "seli", "sindy", "hilda", "wardah", "RRAF" };
    TextView tvPoint;
    Button btnhis;

    public interface OnDataPass{
        void onDataPass(String data);
    }
    private OnDataPass dataPasser;

    public FragmentPoint() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_redeem_point, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        atek = view.findViewById(R.id.ATnama);
        tvPoint = view.findViewById(R.id.tvPoint);
        dbP = FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima");
        tvPoint.setText("-");

        final List<String> items = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima").orderByChild("nama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        String name = ds.child("nama").getValue(String.class);
                        items.add(name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapters = new ArrayAdapter<String>(getContext(), R.layout.dropdown_list, items);
        atek.setAdapter(adapters);

        atek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                dbP.orderByChild("nama").equalTo(item).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot ds : snapshot.getChildren()){
                                LeaderboardClass oclas = ds.getValue(LeaderboardClass.class);
                                if(oclas != null){
                                Integer point = oclas.getPoint();
                                String formattedString = NumberFormat.getNumberInstance(Locale.US).format(point);
                                tvPoint.setText(formattedString);
                                }
                            }
                        }else {
                            Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnhis = view.findViewById(R.id.btnHistoryPoint);
        btnhis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               replaceFragment(new FragmentHistoryRedeem());
            }
        });

        ///Redeem Point
        RecyclerView recv = view.findViewById(R.id.recV_redeemPoint);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        lm.setStackFromEnd(false);
        recv.setLayoutManager(lm);
        recv.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new AdapterDataRedeem(getContext(), list);
        recv.setAdapter(adapter);
        db = FirebaseDatabase.getInstance().getReference("Data").child("linkRedeem");
        db.orderByChild("gJudul").startAt("A").endAt("Z\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    list.add(ds.getValue(OutputRedeemClass.class));
                    adapter.notifyDataSetChanged();
                }
                }else {
                    Toast.makeText(getContext(), "noData Boss", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ///Redeem Donasi
        RecyclerView recvD = view.findViewById(R.id.recV_redeemPointDonasi);
        listD = new ArrayList<>();
        adapterD = new AdapterDataRedeemDonasi(getContext(), listD);
        LinearLayoutManager lmD = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        lmD.setStackFromEnd(false);
        recvD.setLayoutManager(lmD);
        recvD.setHasFixedSize(true);
        recvD.setAdapter(adapterD);
        dbD = FirebaseDatabase.getInstance().getReference("Data").child("linkDonasi");
        dbD.orderByChild("gJudul").startAt("A").endAt("Z\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    listD.clear();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        listD.add(ds.getValue(OutputRedeemDonasiClass.class));
                        adapterD.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(getContext(), "noData Boss", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fHome, fragment);
        ft.commit();
    }
}
