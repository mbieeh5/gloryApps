package com.rraf.gloryservices.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rraf.gloryservices.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class AdapteLeaderBoard extends RecyclerView.Adapter<AdapteLeaderBoard.DataViewHolder> {

    Context context;
    ArrayList<LeaderboardClass> list;

    public AdapteLeaderBoard(Context context, ArrayList<LeaderboardClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapteLeaderBoard.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_layout_leaderboard, parent, false);
        return new DataViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapteLeaderBoard.DataViewHolder holder, int position) {
        LeaderboardClass oc = list.get(position);
        String formattedString = NumberFormat.getNumberInstance(Locale.US).format(oc.getPoint());
        holder.oPoint.setText(formattedString);
        holder.oNama.setText(oc.getNama().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class DataViewHolder extends RecyclerView.ViewHolder{

        TextView oNama, oPoint;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
        oNama = itemView.findViewById(R.id.d_nama);
        oPoint = itemView.findViewById(R.id.d_point);
        }
    }

}


