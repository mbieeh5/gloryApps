package com.rraf.gloryservices.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class PointSystem {
    private DatabaseReference mDatabase;

    public PointSystem(DatabaseReference database) {
        mDatabase = database;
    }

    public void addPoint(String penerima) {
        final DatabaseReference refDataPenerima = mDatabase.child("Users").child("dataPenerima").child(penerima);
        refDataPenerima.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean pointAdded = dataSnapshot.child("pointAdded").getValue(Boolean.class);
                if (pointAdded == null || !pointAdded) {
                    refDataPenerima.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            Integer currentPoint = mutableData.child("point").getValue(Integer.class);
                            if (currentPoint == null) {
                                currentPoint = 0;
                            }
                            mutableData.child("point").setValue(currentPoint + 5000);
                            mutableData.child("pointAdded").setValue(false);
                            return Transaction.success(mutableData);
                        }
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                            // Do something when the transaction is completed
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
