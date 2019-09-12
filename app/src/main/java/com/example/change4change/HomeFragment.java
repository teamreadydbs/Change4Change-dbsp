package com.example.change4change;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    // Tagging for Logcat
    private static final String TAG = HomeFragment.class.getName();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    StoredData userObject = new StoredData();
    String Username = "";
    String Password = "";
    String Name ="";
    Integer BankAccount = 0;
    double Balance = 0.0;
    List<String> LastTransaction;
    List<TransactionsDO> transactionsDOList = new ArrayList<TransactionsDO>();

    String Charity ="";
    double CharityBalance = 0.0;
    boolean DoNotShowAgain;
    CharitiesDO userItem = new CharitiesDO();
    TransactionsDO transactionsDO = new TransactionsDO();
    // Data could be taken from database
    ArrayList<TransactionEntry> transactionEntries = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("#0.00");

    DynamoDBMapper dynamoDBMapper;
    private AmazonDynamoDB amazonDynamoDB;

    TextView bankAccount;
    TextView userName;
    TextView bankBalance;
    View view;



    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, null);

        getUserData();
        userName = view.findViewById(R.id.lbGreetings);
        bankAccount = view.findViewById(R.id.lbBankAccount);
        bankBalance = view.findViewById(R.id.lbBankBalanceInfo);

        String s = BankAccount.toString();
        if (s.length() > 8) {
            String FormattedBankAccount = s.substring(0,3)+ "-" + s.substring(3,8) + "-" + s.substring(8);
            bankAccount.setText(FormattedBankAccount);
        }
        else {
            bankAccount.setText(BankAccount.toString());
        }

        Log.d(TAG, userObject.getPassword() + "======================");

        Log.d(TAG, LastTransaction + "======================");

        String BalanceFormated = df.format(Balance);

        userName.setText("Welcome Back,\n" + Name);

        bankBalance.setText("$ " + BalanceFormated);

        final FloatingActionButton button = view.findViewById(R.id.fabScanQR);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                aws();
                getCharityBalance(Charity);
                Intent scanQRCodeActivity= new Intent(getActivity(), ScanQR.class);
                startActivity(scanQRCodeActivity);
            }
        });

        loadTransaction(LastTransaction);
        initializeList();
        return view;
    }

    private void getUserData() {
        Username = userObject.getUsername();
        Password = userObject.getPassword();
        Name = userObject.getName();
        BankAccount = userObject.getBankAccount();
        Balance = userObject.getBalance();
        LastTransaction = userObject.getLastTransaction();
        Charity = userObject.getCharity();
        DoNotShowAgain = userObject.getDoNotShowAgain();
    }


    public void aws(){

        AWSMobileClient.getInstance().initialize(getActivity(), new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d(TAG, "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        // AWSMobileClient enables AWS user credentials to access your table
        AWSMobileClient.getInstance().initialize(getActivity()).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();

    }

    public void getCharityBalance(final String Charity) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    userItem = dynamoDBMapper.load(CharitiesDO.class, Charity);
                    CharityBalance = userItem.getCharityBalance();
                    StoredData test = new StoredData();
                    test.setCharityBalance(CharityBalance);
                    userObject.setTransCharity(Charity);
                }
                catch (Exception e) {}
            }
        }).start();
    }

    public void initializeList() {

//        Log.d(TAG,transactionsDOList.toString() + "asdfsdafsdafasdf");
//        if (transactionEntries.size() == 0) {
//
//            for (int i = transactionsDOS.size() - 1; i >= 0; i--) {
//                transactionEntries.add(new TransactionEntry(
//                    transactionsDO.getTransMerchant(),
//                    "ATR",
//                    df.format(transactionsDO.getTransAmt()),
//                    transactionsDO.getTransDate()
//                ));
//
//                if (transactionsDO.getTransCharity() !="") {
//                    transactionEntries.add(new TransactionEntry(
//                            transactionsDO.getTransCharity(),
//                            "ATR",
//                            df.format(transactionsDO.getTransAmtCharity()),
//                            transactionsDO.getTransDate()
//                    ));
//                }
//            }
//        }
            recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new TransactionRecyclerViewAdapter(transactionEntries);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
    }


    public void loadTransaction (List<String> s) {
        for (int i = 0; i < s.size(); i++) {
            getTransactionsAWS(s.get(i));
            Log.d(TAG, s.get(i));
        }
    }

    public void getTransactionsAWS(final String id) {
        aws();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    transactionsDO = dynamoDBMapper.load(TransactionsDO.class, id);
                    Log.d(TAG, transactionsDO.toString() + "====================");
                    transactionsDOList.add(transactionsDO);
                    Log.d(TAG, transactionsDOList.toString());
                    transactionEntries.add(new TransactionEntry(
                            transactionsDO.getTransMerchant(),
                            "ATR",
                            df.format(transactionsDO.getTransAmt()),
                            transactionsDO.getTransDate()
                    ));

                    if (transactionsDO.getTransCharity() !="") {
                        transactionEntries.add(new TransactionEntry(
                                transactionsDO.getTransCharity(),
                                "Donation",
                                df.format(transactionsDO.getTransAmtCharity()),
                                transactionsDO.getTransDate()
                        ));
                    }
                }
                catch (Exception e) {}
            }
        }).start();
    }
}
