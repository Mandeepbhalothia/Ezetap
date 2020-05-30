package com.example.ezetap.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezetap.R;
import com.example.ezetap.model.Transaction;
import com.example.ezetap.utils.Common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    ArrayList<Transaction> transactionsList;

    public TransactionAdapter(ArrayList<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        if (position >= 0 && position < transactionsList.size()) {
            holder.setData(transactionsList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView amountTv, txnDateTv, referenceTv, cardTypeTv;
        ImageView statusIv;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            amountTv = itemView.findViewById(R.id.amountTv);
            txnDateTv = itemView.findViewById(R.id.transactionTv);
            referenceTv = itemView.findViewById(R.id.referenceTv);
            cardTypeTv = itemView.findViewById(R.id.cardTv);
            statusIv = itemView.findViewById(R.id.statusIv);

        }

        public void setData(Transaction transaction) {
            amountTv.setText(transaction.getAmount());
            txnDateTv.setText(getFormattedTxnDate(transaction.getTxnDate()));
            referenceTv.setText(transaction.getReference());
            cardTypeTv.setText(transaction.getCardType());
            String status = transaction.getStatus();
            if (status.equals(Common.SUCCESS)) {
                statusIv.setImageResource(R.drawable.ic_success);
            } else {
                statusIv.setImageResource(R.drawable.ic_failed);
            }

        }

        private String getFormattedTxnDate(String txnDate) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:s", Locale.getDefault());
            long time;
            try {
                time = Long.parseLong(txnDate);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return "TimeCastException";
            }

            return sdf.format(time);
        }

    }

}
