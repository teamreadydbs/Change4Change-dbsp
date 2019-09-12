package com.example.change4change;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    // Tagging for Logcat
    private static final String TAG = MainActivity.class.getName();

    // Instantiating Variables for UserDO object usage
    StoredData userObject = new StoredData();
    String Username = "";
    String Password = "";
    String Name ="";
    Integer BankAccount = 0;
    double Balance = 0.0;
    List<String> LastTransaction;
    String Charity ="";
    double CharityBalance = 0.0;
    boolean DoNotShowAgain;

    List<TransactionsDO> transactionsDOList = new ArrayList<TransactionsDO>();
    TransactionsDO transactionsDO = new TransactionsDO();

    DynamoDBMapper dynamoDBMapper;
    Thread thread = new Thread();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUserData();
        if (!DoNotShowAgain) { showMessage(); }
        loadTransaction(LastTransaction);

        //loading the default fragment
        loadFragment(new HomeFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //Get user info after logging in


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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.bottom_nav_home:
                fragment = new HomeFragment();
                break;

            case R.id.bottom_nav_ranking:
                fragment = new CharityRankingFragment();
                break;

            case R.id.bottom_nav_rewards:
                fragment = new CharityRewardsFragment();
                break;

            case R.id.bottom_nav_charity:
                fragment = new CharityInfoFragment();
                break;

            case R.id.bottom_nav_preferences:
                fragment = new PreferencesFragment();
                break;
        }

        updateDbObj(userObject.getUsername(), userObject.getPassword());
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .detach(fragment)
                    .attach(fragment)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void aws(){
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        // AWSMobileClient enables AWS user credentials to access your table
        AWSMobileClient.getInstance().initialize(this).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();

    }

    private void updateDbObj(final String LoginUsername, final String LoginPassword) {
        aws();
        thread = new Thread() {
            @Override
            public void run() {

                UserDO userItem = dynamoDBMapper.load(
                        UserDO.class,
                        LoginUsername,
                        LoginPassword);

                userObject.setUsername(LoginUsername);
                userObject.setPassword(LoginPassword);
                userObject.setBalance(userItem.getBalance());
                userObject.setBankAccount(userItem.getBankAccount());
                userObject.setCharity(userItem.getCharity());
                userObject.setLastTransaction(userItem.getLastTransaction());
                userObject.setName(userItem.getName());
                userObject.setDoNotShowAgain(userItem.getDoNotShowAgain());
                userObject.setAutoDonation(userItem.getAutoDonation());
                userObject.setSetDefaultCharityDNSA(userItem.getSetDefaultCharityDNSA());
            }
        };
        thread.start();


    }

    private void showMessage() {
        new MaterialAlertDialogBuilder(this, R.style.Theme_MaterialComponents_Light_Dialog_Alert)
                .setTitle("Change for your Change")
                .setMessage("In this new build, we have added a charity function. You can donate money on every transaction you make.")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Don't Show Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.dismiss();
                        userObject.setDoNotShowAgain(true);

                        // WRITE CODES TO UPDATE DATABASE IMMEDIATELY
                    }})
                .show();
    }

    public void loadTransaction (List<String> s) {

        for (int i = 0; i < s.size(); i++) {
            TransactionsDO listadder = getTransactionsAWS(s.get(i));
            transactionsDOList.add(listadder);
        }

        userObject.setTransactionsDOS(transactionsDOList);
    }

    public TransactionsDO getTransactionsAWS(final String id) {
        aws();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    transactionsDO = dynamoDBMapper.load(TransactionsDO.class, id);
                    Log.d(TAG, transactionsDO.toString() + "====================");
                }
                catch (Exception e) {}
            }
        }).start();
        return transactionsDO;
    }

}
