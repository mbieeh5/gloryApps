package com.rraf.gloryservices.fragment;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.rraf.gloryservices.activity.HomeActivity;
import com.rraf.gloryservices.adaptor.AdapterDataServiceHistory;
import com.rraf.gloryservices.adaptor.OutputClass;
import com.rraf.gloryservices.adaptor.PointSystem;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentHistory extends Fragment {

    RecyclerView recv;
    AdapterDataServiceHistory adapter;
    ArrayList<OutputClass> list;
    DatabaseReference db;
    FloatingActionButton fab;

    public FragmentHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference("Service").child("dataService");
        recv = view.findViewById(R.id.recycleView_service);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        recv.setLayoutManager(lm);
        recv.setHasFixedSize(true);
        ProgressBar pb = view.findViewById(R.id.loadingHistory);
        list = new ArrayList<>();
        adapter = new AdapterDataServiceHistory(getContext(), list);
        recv.setAdapter(adapter);
        TextView Tot = view.findViewById(R.id.tvTotal);
        TextView title = view.findViewById(R.id.tvTitleHistory);
        db.orderByChild("iTgl").addValueEventListener(new ValueEventListener() {
        int total = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    pb.setVisibility(View.GONE);
                    list.add(ds.getValue(OutputClass.class));
                    total++;
                    Tot.setText(String.format(Locale.getDefault(), "%d",total));
                    title.setText(getString(R.string.history_semua_data));
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fab = view.findViewById(R.id.FABHistory);
        if(getContext() != null){
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_add_24);
        fab.setImageDrawable(drawable);
        fab.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.orange_base_250));
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v);
                popup.inflate(R.menu.fab_menu_history);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int ID = item.getItemId();
                        if(ID == R.id.fLunas){
                            db.orderByChild("iStatus").equalTo("LUNAS").addValueEventListener(new ValueEventListener() {
                                int total = 0;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    list.clear();
                                    if(snapshot.exists()){
                                        for(DataSnapshot ds : snapshot.getChildren()){
                                            OutputClass oclas = ds.getValue(OutputClass.class);
                                            list.add(oclas);
                                            total++;
                                            popup.dismiss();
                                            Log.d("> ", oclas.getiPenerima()+" > "+oclas.getiTgl());
                                            Tot.setText(String.format(Locale.getDefault(), "%d",total));
                                            title.setText(getString(R.string.history_data_lunas));
                                        }
                                            adapter.notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(getContext(), "Data Belum Ada Yang Lunas", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(ID == R.id.fCancel){
                            db.orderByChild("iStatus").equalTo("CANCELED").addValueEventListener(new ValueEventListener() {
                                int total = 0;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    list.clear();
                                    if(snapshot.exists()){
                                        for(DataSnapshot ds : snapshot.getChildren()){
                                            OutputClass oclass = ds.getValue(OutputClass.class);
                                            list.add(oclass);
                                            popup.dismiss();
                                            total++;
                                            Tot.setText(String.format(Locale.getDefault(), "%d",total));
                                            title.setText(getString(R.string.history_data_cancel));
                                        }
                                            adapter.notifyDataSetChanged();
                                    }else{
                                        Toast.makeText(getContext(), "Tidak Ada Yang Canceled", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else if(ID == R.id.fTanggal){
                            DialogPlus dialogPlus = DialogPlus.newDialog(getContext())
                                    .setGravity(Gravity.CENTER)
                                    .setMargin(50, 0, 50, 0)
                                    .setContentHolder(new ViewHolder(R.layout.sort_item_edit))
                                    .setExpanded(false)
                                    .create();
                            View a = (LinearLayout) dialogPlus.getHolderView();
                            dialogPlus.show();
                            TextView fTgl = a.findViewById(R.id.fTgl);
                            fTgl.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Calendar calendar = Calendar.getInstance();
                                    final int year = calendar.get(Calendar.YEAR);
                                    final int month = calendar.get(Calendar.MONTH);
                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                    DatePickerDialog datePickerDialog =  new DatePickerDialog(
                                            getContext(), new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int day) {
                                            month = month + 1;
                                            String date = day+"/"+month+"/"+year;
                                            fTgl.setText(date);
                                        }
                                    },year,month,day);
                                    datePickerDialog.show();
                                }
                            });
                            Button cari = a.findViewById(R.id.btnCari);
                            Button btnCari = a.findViewById(R.id.btnCariType);
                            btnCari.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pb.setVisibility(View.VISIBLE);
                                    Query query = db.orderByChild("iHp");
                                    query.addValueEventListener(new ValueEventListener() {
                                        int total = 0;
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            pb.setVisibility(View.GONE);
                                            list.clear();
                                            for(DataSnapshot ds : snapshot.getChildren()){
                                                OutputClass oclass = ds.getValue(OutputClass.class);
                                                if(oclass != null){
                                                    oclass.setiHp(oclass.getiHp().toUpperCase());
                                                    String tgl = oclass.getiTgl();
                                                    list.add(oclass);
                                                    total++;
                                                title.setText(getString(R.string.history_semua_data));
                                                Tot.setText(String.format(Locale.getDefault(), "%d",total));
                                                }
                                                popup.dismiss();
                                                dialogPlus.dismiss();
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            });
                            cari.setOnClickListener(new View.OnClickListener() {
                                int total = 0;
                                @Override
                                public void onClick(View v) {
                                    list.removeAll(list);
                                    Query query = db.orderByChild("iTgl").equalTo(fTgl.getText().toString());
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                list.clear();
                                                for(DataSnapshot ds : snapshot.getChildren()){
                                                    OutputClass oclass = ds.getValue(OutputClass.class);
                                                    list.add(oclass);
                                                    total++;
                                                    popup.dismiss();
                                                    dialogPlus.dismiss();
                                                }
                                                    title.setText(getString(R.string.history_data_tgl,fTgl.getText().toString()));
                                                Tot.setText(String.format(Locale.getDefault(), "%d",total));
                                            adapter.notifyDataSetChanged();
                                            }else {
                                                Toast.makeText(getContext(), "Data Tidak DiTemukan", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            });
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }
}