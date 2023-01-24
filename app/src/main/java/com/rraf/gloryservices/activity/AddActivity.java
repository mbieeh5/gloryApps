package com.rraf.gloryservices.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.InputClass;
import com.rraf.gloryservices.databinding.ActivityAddBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;
    String iTgl, iHp, iRsk, iHrg, iPenerima, Id;
    FirebaseDatabase db;
    DatabaseReference reference;
    ArrayAdapter<String> adapter;
    //String[] items = {"aldi", "amri", "seli", "sindy", "hilda", "rere" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate((getLayoutInflater()));
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(binding.getRoot());
                Calendar calendar = Calendar.getInstance();
                    final int year = calendar.get(Calendar.YEAR);
                    final int month = calendar.get(Calendar.MONTH);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.cvTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =  new DatePickerDialog(
                        AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        binding.iTgl.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
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
        adapter = new ArrayAdapter<String>(this, R.layout.dropdown_list, items);
        binding.autoComplete.setAdapter(adapter);
        binding.autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                iPenerima = item;
                Toast.makeText(AddActivity.this, "Penerima: "+item, Toast.LENGTH_SHORT).show();
            }
        });


        binding.btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Id = "data" +new Date().getTime();
                iTgl = binding.iTgl.getText().toString();
                iHp = binding.iHp.getText().toString();
                iRsk = binding.iRsk.getText().toString();
                iHrg = binding.iHrg.getText().toString();
                iPenerima = binding.autoComplete.getText().toString();

                if(!iTgl.isEmpty() && !iHp.isEmpty() && !iRsk.isEmpty() && !iHrg.isEmpty()) {
                    InputClass inputClass = new InputClass(Id ,iTgl, iHp, iRsk, iHrg, iPenerima);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Service").child("dataService");
                    reference.child(Id).setValue(inputClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        binding.iTgl.setText("");
                        binding.iHp.setText("");
                        binding.iRsk.setText("");
                        binding.iHrg.setText("");
                        binding.autoComplete.setText("");
                            Toast.makeText(AddActivity.this, "Data Berhasil Di Input Captain", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(AddActivity.this, "Gagal Teriput", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}