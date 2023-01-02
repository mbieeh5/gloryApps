package com.rraf.gloryservices.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.activity.AddActivity;
import com.rraf.gloryservices.activity.HomeActivity;

import org.w3c.dom.Text;

import java.util.Map;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        Button btnInput = view.findViewById(R.id.btnInput);
        TextView nama = view.findViewById(R.id.tv_nama);
        TextView point = view.findViewById(R.id.pointtxt);
        mdb = FirebaseDatabase.getInstance().getReference("Service").child("dataService").child("iStatus");
        mdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sts = (String) snapshot.getValue();
                if (snapshot.exists()) {
                    if (sts.equals("LUNAS")) {
                        Map<String, Objects> map = (Map<String, Objects>) snapshot.getChildren();
                        Objects pointmentah = map.get("LUNAS");
                        int Mvalue = Integer.parseInt(String.valueOf(pointmentah));
                        int sum = Mvalue * 5000;
                        point.setText(String.valueOf(sum));
                    }
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
                    }
                }



        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityAdd();
            }
        });
        return view;
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