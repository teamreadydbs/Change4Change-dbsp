package com.example.change4change;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CardRecyclerViewAdapterAllRewards extends RecyclerView.Adapter<CardViewHolderAllRewards> {

    private ArrayList<RewardsProductAllList> rewardList;

    public CardRecyclerViewAdapterAllRewards(ArrayList<RewardsProductAllList> alrewardList) {
        rewardList = alrewardList;
    }

    @Override
    public CardViewHolderAllRewards onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rewards_product_info, parent, false);
        CardViewHolderAllRewards cvhal = new CardViewHolderAllRewards(v);
        return cvhal;
    }

    @Override
    public void onBindViewHolder(CardViewHolderAllRewards holder, int position) {
        RewardsProductAllList currentItem = rewardList.get(position);

        holder.cardLogo.setImageResource(currentItem.getCardLogo());
        holder.cardTitle.setText(currentItem.getCardTitle());
        holder.cardDescription.setText(currentItem.getCardTitleSecondary());

    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }
}
