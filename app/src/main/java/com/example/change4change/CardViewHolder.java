package com.example.change4change;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {

    public AppCompatImageView cardLogo;
    public AppCompatImageView cardImage;
    public TextView cardTitle;
    public TextView cardTitleSecondary;
    public TextView cardContent;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        cardLogo = itemView.findViewById(R.id.card_logo);
        cardImage = itemView.findViewById(R.id.card_image);
        cardTitle = itemView.findViewById(R.id.card_title);
        cardTitleSecondary = itemView.findViewById(R.id.card_title_secondary);
        cardContent = itemView.findViewById(R.id.card_content);
    }
}
