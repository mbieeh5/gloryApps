package com.rraf.gloryservices.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rraf.gloryservices.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_search);
    }
}