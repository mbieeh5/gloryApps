package com.rraf.gloryservices.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.InputClass;
import com.rraf.gloryservices.databinding.ActivityAddBinding;
import com.rraf.gloryservices.databinding.ActivityHomeBinding;

import java.util.Calendar;
import java.util.Random;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;
    String iTgl, iHp, iRsk, iHrg;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate((getLayoutInflater()));
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
                Calendar calendar = Calendar.getInstance();
                    final int year = calendar.get(Calendar.YEAR);
                    final int month = calendar.get(Calendar.MONTH);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.iTgl.setOnClickListener(new View.OnClickListener() {
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

        binding.btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iTgl = binding.iTgl.getText().toString();
                iHp = binding.iHp.getText().toString();
                iRsk = binding.iRsk.getText().toString();
                iHrg = binding.iHrg.getText().toString();
                if(!iTgl.isEmpty() && !iHp.isEmpty() && !iRsk.isEmpty() && !iHrg.isEmpty()) {
                    InputClass inputClass = new InputClass(iTgl, iHp, iRsk, iHrg);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Service");
                    reference.child("dataService").push().setValue(inputClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        binding.iTgl.setText("");
                        binding.iHp.setText("");
                        binding.iRsk.setText("");
                        binding.iHrg.setText("");

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