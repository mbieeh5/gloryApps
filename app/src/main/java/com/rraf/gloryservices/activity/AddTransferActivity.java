package com.rraf.gloryservices.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.InputTransferClass;
import com.rraf.gloryservices.databinding.ActivityAddTransferBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTransferActivity extends AppCompatActivity {

    ActivityAddTransferBinding binding;
    String iTgl, iBank, iNama, iNorek, iTfMana, Id;
    Integer iNom;
    FirebaseDatabase db;
    DatabaseReference reference;
    ArrayAdapter<String> adapter;
    String[] items = {"Glory1", "Glory2", "Downline"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTransferBinding.inflate((getLayoutInflater()));
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
                        AddTransferActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        adapter = new ArrayAdapter<String>(this, R.layout.dropdown_list, items);
        binding.autoComplete.setAdapter(adapter);
        binding.autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                iTfMana = item;
                Toast.makeText(AddTransferActivity.this, "TFan Mana: "+item, Toast.LENGTH_SHORT).show();
            }
        });


        binding.btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Id = new Date().getTime() +"-"+binding.iNorek.getText().toString()+binding.iBank.getText().toString();
                iTgl = binding.iTgl.getText().toString();
                iBank = binding.iBank.getText().toString();
                iNama = binding.iNama.getText().toString();
                iNorek = binding.iNorek.getText().toString();
                iNom = Integer.parseInt(binding.iNom.getText().toString());
                iTfMana = binding.autoComplete.getText().toString();

                if(!iTgl.isEmpty() && !iNama.isEmpty() && !iBank.isEmpty()) {
                    InputTransferClass inputTransferClass = new InputTransferClass(Id ,iTgl, iBank, iNom, iNorek, iNama, iTfMana);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Transfer").child("dataTransfer");
                    reference.child(Id).setValue(inputTransferClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        binding.iTgl.setText("");
                        binding.iBank.setText("");
                        binding.iNama.setText("");
                        binding.iNom.setText("");
                        binding.iNorek.setText("");
                        binding.autoComplete.setText("");
                            Toast.makeText(AddTransferActivity.this, "Data Berhasil Di Input "+ FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(AddTransferActivity.this, "Gagal Teriput", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}