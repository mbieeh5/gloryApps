package com.rraf.gloryservices.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.AdminInputClass;

public class AdminPanelActivity extends AppCompatActivity {

    ArrayAdapter<String> adapterS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        getSupportActionBar().hide();
        String nick = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        TextView namaA = findViewById(R.id.tv_namaAdmin);
        namaA.setText(nick);
        Button inputA = findViewById(R.id.btnInputAdmin);
        Button EditA = findViewById(R.id.btnEditAdmin);
        Button CutiA = findViewById(R.id.btnCutiAdmin);
        inputA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(AdminPanelActivity.this)
                        .setGravity(Gravity.BOTTOM)
                        .setMargin(0, 0, 0, 0)
                        .setContentHolder(new ViewHolder(R.layout.activity_admin_add))
                        .setExpanded(false)
                        .create();
                View a = (ConstraintLayout) dialogPlus.getHolderView();
                dialogPlus.show();
                EditText ePb = a.findViewById(R.id.penerimaBaru);
                String penerima = ePb.getText().toString();
                Integer point = 0;
                Boolean pointAdded = false;
                Button btnSi = a.findViewById(R.id.btnSimpanAdmin);
                /*btnSi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AdminInputClass adminInputClass = new AdminInputClass(penerima,point, pointAdded);
                        FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima").child(penerima).setValue(adminInputClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AdminPanelActivity.this, "Berhasil Di Tambah", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });*/
            }
        });
        EditA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(AdminPanelActivity.this)
                        .setGravity(Gravity.BOTTOM)
                        .setMargin(0, 0, 0, 0)
                        .setContentHolder(new ViewHolder(R.layout.activity_admin_edit))
                        .setExpanded(false)
                        .create();
                View a = (ConstraintLayout) dialogPlus.getHolderView();
                dialogPlus.show();
                AutoCompleteTextView atekS = a.findViewById(R.id.atekSRedeem);
                String[] items = {"OPEN", "CLOSE"};
                adapterS = new ArrayAdapter<String>(AdminPanelActivity.this, R.layout.dropdown_list, items);
                atekS.setAdapter(adapterS);
                Button simpanR = a.findViewById(R.id.btnSimpenAdmins);
                simpanR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        atekS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String statusS = parent.getItemAtPosition(position).toString();
                                atekS.setText(statusS);
                            }
                        });
                        FirebaseDatabase.getInstance().getReference("Data").child("statusRedeem").child("statusR").child("R").setValue(atekS.getText().toString());
                        Toast.makeText(AdminPanelActivity.this, "Berhasil Set Redeem", Toast.LENGTH_SHORT).show();
                        dialogPlus.dismiss();
                    }
                });
            }
        });
        CutiA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, AdminCutiActivity.class);
                startActivity(intent);
            }
        });
    }
}