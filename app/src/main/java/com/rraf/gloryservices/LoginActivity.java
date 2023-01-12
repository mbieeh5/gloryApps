package com.rraf.gloryservices;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    EditText u_name, p_wd;
    Button lgn_btn;
    String username, password;
    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Toast.makeText(this, "gagal kembali", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();


        u_name = findViewById(R.id.username);
        u_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                    lgn_btn.setEnabled(true);
            }
        });
        if(mAuth.getCurrentUser() != null) {
            Intent home = new Intent(LoginActivity.this, Home.class);
            startActivity(home);
        }
        p_wd = findViewById(R.id.password);
        lgn_btn = findViewById(R.id.login);
        lgn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekLogin();
            }
        });
    }
    private void cekLogin() {
        username = u_name.getText().toString();
        password = p_wd.getText().toString();

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent home = new Intent(LoginActivity.this, Home.class);
                            startActivity(home);
                            Toast.makeText(LoginActivity.this,"Welcome Aboard Captain", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong Credential Please Contact IT Support", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}