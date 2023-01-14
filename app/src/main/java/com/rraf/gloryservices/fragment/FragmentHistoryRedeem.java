package com.rraf.gloryservices.fragment;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdapterDataRedeemHistory;
import com.rraf.gloryservices.adaptor.AdapterDataServiceHistory;
import com.rraf.gloryservices.adaptor.OutputClass;
import com.rraf.gloryservices.adaptor.OutputRedeemHistoryClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FragmentHistoryRedeem extends Fragment {

    RecyclerView recv;
    AdapterDataRedeemHistory adapter;
    ArrayList<OutputRedeemHistoryClass> list;
    DatabaseReference db;

    public FragmentHistoryRedeem() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_redeem, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference("Users").child("historyRedeem");
        recv = view.findViewById(R.id.recycleView_history_redeem);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recv.setLayoutManager(lm);
        recv.setHasFixedSize(true);
        ProgressBar pb = view.findViewById(R.id.loading_data_history);
        list = new ArrayList<>();
        adapter = new AdapterDataRedeemHistory(getContext(), list);
        recv.setAdapter(adapter);
        TextView tvTot = view.findViewById(R.id.TvTot_history);
        db.orderByChild("sn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total = 0;
                if(snapshot.exists()){
                  pb.setVisibility(View.GONE);
                    for(DataSnapshot ds : snapshot.getChildren()){
                        list.add(ds.getValue(OutputRedeemHistoryClass.class));
                        int point = ds.child("hJmlRedeem").getValue(Integer.class);
                            total += point;
                    }
                    String formattedNumber = String.format(Locale.US,"%,d",total);
                    tvTot.setText(getString(R.string.total_point_terredeem, formattedNumber));
                    adapter.notifyDataSetChanged();
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        pb.setVisibility(View.VISIBLE);

    }
}