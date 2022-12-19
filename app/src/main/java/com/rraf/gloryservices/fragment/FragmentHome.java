package com.rraf.gloryservices.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rraf.gloryservices.R;
import com.rraf.gloryservices.activity.AddActivity;
import com.rraf.gloryservices.activity.HomeActivity;

public class FragmentHome extends Fragment {

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

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome();
            }
        });
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