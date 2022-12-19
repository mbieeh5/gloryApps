package com.rraf.gloryservices;

import static com.rraf.gloryservices.R.*;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rraf.gloryservices.databinding.ActivityHomeBinding;
import com.rraf.gloryservices.fragment.FragmentHistory;
import com.rraf.gloryservices.fragment.FragmentHome;
import com.rraf.gloryservices.fragment.FragmentLeaderboard;
import com.rraf.gloryservices.fragment.FragmentProfile;
import com.rraf.gloryservices.fragment.FragmentSettings;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
        replaceFragment(new FragmentHome());

    binding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case id.n_home:
                    replaceFragment(new FragmentHome());
                    break;
                case id.n_leaderboard:
                    replaceFragment(new FragmentLeaderboard());
                    break;
                case id.n_history:
                    replaceFragment(new FragmentHistory());
                    break;
                case id.n_profile:
                    replaceFragment(new FragmentProfile());
                    break;
                case id.n_setting:
                    replaceFragment(new FragmentSettings());
                    break;
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(id.fHome, fragment);
        ft.commit();
    }
}


