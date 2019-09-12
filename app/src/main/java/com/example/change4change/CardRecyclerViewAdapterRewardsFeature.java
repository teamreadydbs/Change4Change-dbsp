package com.example.change4change;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CardRecyclerViewAdapterRewardsFeature extends RecyclerView.Adapter<CardViewHolderRewardsFeature> {

    private ArrayList<RewardsProductFeatureList> rewardList;

    public CardRecyclerViewAdapterRewardsFeature(ArrayList<RewardsProductFeatureList> alrewardList) {
        rewardList = alrewardList;
    }

    @Override
    public CardViewHolderRewardsFeature onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rewards_product_scroll, parent, false);
        CardViewHolderRewardsFeature cvhrf = new CardViewHolderRewardsFeature(v);
        return cvhrf;
    }

    @Override
    public void onBindViewHolder(CardViewHolderRewardsFeature holder, int position) {
        RewardsProductFeatureList currentItem = rewardList.get(position);

        holder.cardLogo.setImageResource(currentItem.getRewardImg());
        holder.cardTitle.setText(currentItem.getRewardTitle());
        holder.cardDescription.setText(currentItem.getRewardDescription());

    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

}
