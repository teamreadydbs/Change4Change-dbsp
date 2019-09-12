package com.example.change4change;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransacationViewHolder> {
    private ArrayList<TransactionEntry> transactionEntries;

    public TransactionRecyclerViewAdapter(ArrayList<TransactionEntry> alTransactionEntries) {
        transactionEntries = alTransactionEntries;
    }

    @Override
    public TransacationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rewards_info, parent, false);
        TransacationViewHolder tvh = new TransacationViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TransacationViewHolder holder, int position) {
        TransactionEntry currentItem = transactionEntries.get(position);

        holder.lbID.setText(currentItem.getLbID());
        holder.lbTransType.setText(currentItem.getLbTranstype());
        holder.lbTransAmt.setText(currentItem.getLbTransAmt());
        holder.lbDateInfo.setText(currentItem.getLbDateInfo());
    }

    @Override
    public int getItemCount() {
        return transactionEntries.size();
    }

}