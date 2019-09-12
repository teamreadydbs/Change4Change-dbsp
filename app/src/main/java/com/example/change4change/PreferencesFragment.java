package com.example.change4change;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class PreferencesFragment extends Fragment {

    private static final String TAG = PreferencesFragment.class.getName();

    DynamoDBMapper dynamoDBMapper;

    UserDO userDO = new UserDO();
    StoredData object = new StoredData();
    Boolean autoDonateChanger = false;
    MaterialButton btnDonate;

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preferences, null);

        btnDonate = view.findViewById(R.id.btnDonate);

        autoDonateChanger = object.getAutoDonation();

        if (autoDonateChanger) {
            btnDonate.setText("Disallow Auto Donation");
        }
        else {
            btnDonate.setText("Allow Auto Donation");
        }

        btnDonate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                object.setDoNotShowAgain(!autoDonateChanger);
                if (!autoDonateChanger) {
                    btnDonate.setText("Disallow Auto Donation");
                }
                else {
                    btnDonate.setText("Allow Auto Donation");
                }
                updateUserData(!autoDonateChanger);
                updateDB();

            }
        });


        return view;
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

    public void updateUserData(Boolean change) {
        userDO.setUserId(object.getUsername());
        userDO.setPassword(object.getPassword());
        userDO.setAutoDonation(change);
        userDO.setBalance(object.getBalance());
        userDO.setBankAccount(object.getBankAccount());
        userDO.setCharity(object.getCharity());
        userDO.setDoNotShowAgain(object.getDoNotShowAgain());
        userDO.setLastTransaction(object.getLastTransaction());
        userDO.setName(object.getName());
        userDO.setSetDefaultCharityDNSA(object.getSetDefaultCharityDNSA());
    }
    private void loadFragment() {
        Fragment frg = null;
        frg = getFragmentManager().findFragmentByTag(TAG);
//        frg1 = getFragmentManager().findFragmentById(HomeFragment.);
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.replace(R.id.fragment_container, PreferencesFragment.this);
        ft.commit();
    }


    //Update AWS database
    public void updateDB() {
        aws();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(userDO);
                Log.d(TAG,"Update Success!!");
                // Item saved
            }
        }).start();
    }
}
