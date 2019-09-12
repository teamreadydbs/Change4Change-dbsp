package com.example.change4change;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransacationViewHolder extends RecyclerView.ViewHolder {

    TextView lbID;
    TextView lbTransType;
    TextView lbTransAmt;
    TextView lbDateInfo;


    public TransacationViewHolder(@NonNull View itemView) {
        super(itemView);
        lbID = itemView.findViewById(R.id.lbID);
        lbTransType = itemView.findViewById(R.id.lbTransType);
        lbTransAmt = itemView.findViewById(R.id.lbTransAmt);
        lbDateInfo = itemView.findViewById(R.id.lbDateInfo);

    }
}
