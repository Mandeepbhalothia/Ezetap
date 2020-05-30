package com.example.ezetap;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezetap.adapter.TransactionAdapter;
import com.example.ezetap.model.Transaction;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView transactionRv;
    TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.title));

        /*init recycler view and set adapter*/
        initRecyclerView();

    }

    private void initRecyclerView() {
        transactionRv = findViewById(R.id.transactionRv);

        /*get List of transactions from json*/
        transactionAdapter = new TransactionAdapter(getTransactionData());

        transactionRv.setLayoutManager(new LinearLayoutManager(this));

        /*divider*/
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(getDrawable(R.drawable.divider)));
        transactionRv.addItemDecoration(itemDecorator);

        transactionRv.setAdapter(transactionAdapter);


    }

    private ArrayList<Transaction> getTransactionData() {
        ArrayList<Transaction> transactionsList = new ArrayList<>();

        /*load json file from asset*/
        String jsonString = loadJSONFromAsset();
        if (jsonString != null) {
            Gson gson = new Gson();
            Transaction[] transactions = gson.fromJson(jsonString, Transaction[].class);
            transactionsList = new ArrayList<>(Arrays.asList(transactions));
        }

        return transactionsList;
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("transactions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            int readLength = is.read(buffer);
            Log.d("TAG", "loadJSONFromAsset: "+readLength);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}