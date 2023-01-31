package com.rraf.gloryservices.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.activity.AddActivity;
import com.rraf.gloryservices.activity.AddTransferActivity;
import com.rraf.gloryservices.activity.EditTransferActivity;
import com.rraf.gloryservices.activity.HomeActivity;
import com.rraf.gloryservices.adaptor.OutputClass;


import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class FragmentHome extends Fragment {
    private DatabaseReference mdb;
    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.

        // Your server communicates with FCM using this token
        // You can send it to your server now
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnInput = view.findViewById(R.id.btnInput);
        Button btnInputTf = view.findViewById(R.id.btnInputTf);
        btnInputTf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityInputTf();
            }
        });
        Button btnEditTf = view.findViewById(R.id.btnEditTf);
        btnEditTf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityEditTf();
            }
        });
        Button btnMutasiTf = view.findViewById(R.id.btnMutasiTf);
        btnMutasiTf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });
        TextView nama = view.findViewById(R.id.tv_nama);
        TextView points = view.findViewById(R.id.pointtxt);
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setGroupingUsed(true);
        mdb = FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima");
        mdb.orderByChild("point").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalp = 0;
                for(DataSnapshot ds : snapshot.getChildren()){
                    Object value = ds.child("point").getValue();
                    if(value != null){
                     totalp += Integer.parseInt(value.toString());
                    }
                    final String pointM = NumberFormat.getNumberInstance(Locale.US).format(totalp);
                    points.setText(pointM);
                }
                }
                else{
                    points.setText("0");
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    String email = user.getEmail();
                    nama.setText(email);
                    if(Objects.equals(user.getEmail(), "ali@admin.com")){
                        btnEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        ActivityHome();
                            }
                        });
                    }else if (Objects.equals(user.getEmail(), "admin@rraf.rraf")){
                        btnEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityHome();
                            }
                        });
                    }
                }
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityAdd();
            }
        });
        Button btnMutasi = view.findViewById(R.id.btnMutasi);
        btnMutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentHistory());
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fHome, fragment);
        ft.commit();
    }

    private void ActivityEditTf(){
        Intent intent = new Intent(getContext(), EditTransferActivity.class);
        startActivity(intent);
    }

    private void ActivityInputTf(){
        Intent intent = new Intent(getContext(), AddTransferActivity.class);
        startActivity(intent);
    }

    private void ActivityHome() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }
    private void ActivityAdd() {
        Intent intent = new Intent(getContext(), AddActivity.class);
        startActivity(intent);
    }
}