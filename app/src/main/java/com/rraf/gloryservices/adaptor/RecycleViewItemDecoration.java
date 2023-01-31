package com.rraf.gloryservices.adaptor;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;

    public RecycleViewItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = spacing;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 12) {
            outRect.top = spacing;
        }
    }
}