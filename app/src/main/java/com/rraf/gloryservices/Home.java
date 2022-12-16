package com.rraf.gloryservices;

import static com.rraf.gloryservices.R.*;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.rraf.gloryservices.activity.AddActivity;
import com.rraf.gloryservices.activity.HomeActivity;
import com.rraf.gloryservices.activity.ProfileActivity;
import com.rraf.gloryservices.activity.SearchActivity;
import com.rraf.gloryservices.activity.SettingActivity;
import com.rraf.gloryservices.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        setContentView(binding.getRoot());

binding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case id.n_home:
                    ActivityHome();
                    break;
                case id.n_add:
                    ActivityAdd();
                    break;
                case id.n_history:
                    ActivitySearch();
                    break;
                case id.n_setting:
                    ActivitySetting();
                    break;
                case id.n_profile:
                    ActivityProfile();
                    break;

            }
            return true;
        });
    }

    private void ActivityHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    private void ActivityAdd() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
    private void ActivitySearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    private void ActivitySetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
    private void ActivityProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


}


