package com.rraf.gloryservices.adaptor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rraf.gloryservices.LoginActivity;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.activity.AdminPanelActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AdapterDataPengaturan extends RecyclerView.Adapter<AdapterDataPengaturan.DataViewHolder> {

    Context context;
    ArrayList<OutputPengaturan> list;

    private logoutcallback logoutCallbacks;
    public AdapterDataPengaturan(Context context,logoutcallback logoutCallbacks, ArrayList<OutputPengaturan> list) {
        this.context = context;
        this.logoutCallbacks = logoutCallbacks;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterDataPengaturan.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_layout_pengaturan, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataPengaturan.DataViewHolder holder, int position) {
        OutputPengaturan oc = list.get(position);
        holder.gPengaturan.setText(oc.getPengaturan());
        holder.gPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muncul(oc.getPengaturan());
            }
        });
    }

    private void muncul(String pengaturan) {
        if(Objects.equals(pengaturan, "Logout")){
                logoutCallbacks.onLogout();
        }else if(Objects.equals(pengaturan, "About")){
            Toast.makeText(context, "Aplikasi Made By: Rraf", Toast.LENGTH_SHORT).show();
        }else if(Objects.equals(pengaturan, "PengaturanTampilan")){
            Toast.makeText(context, "Gausah Di ubah ubah, input aja udah", Toast.LENGTH_SHORT).show();
        }else if(Objects.equals(pengaturan, "UpdateAplikasi")){
            String url = "https://drive.google.com/drive/folders/1P_-YicIG5ehfmxM-ScKTGXJje2QskZtu?usp=sharing";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        }else if(Objects.equals(pengaturan, "Admin Panel")){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    if (Objects.equals(user.getEmail(), "ali@admin.com")) {
                        Intent intent = new Intent(context, AdminPanelActivity.class);
                        context.startActivity(intent);
                    } else if (Objects.equals(user.getEmail(), "admin@rraf.rraf")) {
                        Intent intent = new Intent(context, AdminPanelActivity.class);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Admin Only", Toast.LENGTH_SHORT).show();
                    }
                }
        }else{
            Toast.makeText(context, "Feature Not Avaliable/Coming Soon", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class DataViewHolder extends RecyclerView.ViewHolder{

        TextView gPengaturan;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        gPengaturan = itemView.findViewById(R.id.dPengaturan);
        }
    }

}


