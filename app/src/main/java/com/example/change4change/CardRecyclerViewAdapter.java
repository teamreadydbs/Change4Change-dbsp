package com.example.change4change;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private ArrayList<CharityEntry> charityEntries;

    public CardRecyclerViewAdapter(ArrayList<CharityEntry> alCharityEntries) {
        charityEntries = alCharityEntries;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_charity_info, parent, false);
        CardViewHolder cvh = new CardViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CharityEntry currentItem = charityEntries.get(position);

        holder.cardLogo.setImageResource(currentItem.getCharityLogo());
        holder.cardImage.setImageResource(currentItem.getCharityPicture());

        holder.cardTitle.setText(currentItem.getCardTitle());
        holder.cardTitleSecondary.setText(currentItem.getCardTitleSecondary());
        holder.cardContent.setText(currentItem.getCardContent());
    }

    @Override
    public int getItemCount() {
        return charityEntries.size();
    }
}
