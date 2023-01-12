package com.rraf.gloryservices;

import static com.rraf.gloryservices.R.*;

import android.os.Bundle;
import android.widget.Toast;

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
        int id = item.getItemId();
        if(id == R.id.n_home){
            replaceFragment(new FragmentHome());
        }else if(id == R.id.n_leaderboard){
            replaceFragment(new FragmentLeaderboard());
        }else if(id == R.id.n_history){
            replaceFragment(new FragmentHistory());
        }else if(id == R.id.n_profile){
            replaceFragment(new FragmentProfile());
        }else if(id == R.id.n_setting){
            replaceFragment(new FragmentSettings());
        }else{
            Toast.makeText(this, "how you get there??", Toast.LENGTH_SHORT).show();
        }
        return true;
    });
    }

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    public void onBackPressed(){
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            Toast.makeText(getBaseContext(), "tapi boonggg wkwkwkwk", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Tekan 2x untuk keluar",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }



    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(id.fHome, fragment);
        ft.commit();
    }
}


