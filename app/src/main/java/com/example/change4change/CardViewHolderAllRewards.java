package com.example.change4change;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolderAllRewards extends RecyclerView.ViewHolder {

    public AppCompatImageView cardLogo;
    public TextView cardTitle;
    public TextView cardDescription;

    public CardViewHolderAllRewards(@NonNull View itemView) {
        super(itemView);
        cardLogo = itemView.findViewById(R.id.card_logo);
        cardTitle = itemView.findViewById(R.id.card_title);
        cardDescription = itemView.findViewById(R.id.card_title_secondary);
    }
}
