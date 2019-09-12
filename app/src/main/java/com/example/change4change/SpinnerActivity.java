package com.example.change4change;


import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SpinnerActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    String Merchant = "";
    boolean showMeAgainTrans = false;
    boolean autoDonation = false;
    String charityDefault ="";

    //User for User Object Class setters
    String udoUserId;
    String udoPassword;
    double udoBalance;
    int udoBankAccountNumber;
    String udoCharity;
    boolean udoDoNotShowAgain;
    String udoName;
    List<String> udoLastTransaction;
    boolean udoAutoDonation;
    boolean udoSetDefaultCharityDNSA;
    UserDO userAWSDO = new UserDO();
    List<CharitiesDO> logins;
    Thread thread = new Thread();
    Thread thread1 = new Thread();

    String dbCharityID = "";
    String dbCharityName = "";
    double dbCharityBal = 0.0;

    int itemID = 0;



    StoredData object = new StoredData();
    View view;
    private static final String TAG = SpinnerActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        view = findViewById(android.R.id.content);

        charityDefault = object.getCharity();
        autoDonation = object.getAutoDonation();
        showMeAgainTrans = object.getSetDefaultCharityDNSA();

        aws();
        if (charityDefault != "" && charityDefault != null) { getCharityInfo(charityDefault); }
        Log.d (TAG,"MERCHANT ID = " +object.getMerchantUserId());
        getMerchantInfo(object.getMerchantUserId());
        Snackbar.make(view, R.string.snack_bar_scan_qr, Snackbar.LENGTH_LONG)
                .setBackgroundTint(getResources().getColor(R.color.color_primary))
                .show();

        if (charityDefault == "" || charityDefault == null) {

            if (object.getSetDefaultCharityDNSA()) {
                //Go to payment confirm
                Intent dialogMoveToPaymentConfirm= new Intent(SpinnerActivity.this, PaymentConfirmHide.class);
                startActivity(dialogMoveToPaymentConfirm);
            }
            // Show Dialog
            Log.d(TAG,object.getPassword());
            showMessage(view);
        }
        else {
            if (autoDonation) {
                // Go to payment confirm
                Intent donateMoveToPaymentConfirm= new Intent(SpinnerActivity.this, Payment.class);
                startActivity(donateMoveToPaymentConfirm);
            }
            else {
                // Go to payment
                Intent donateMoveToPayment= new Intent(SpinnerActivity.this, Payment.class);
                startActivity(donateMoveToPayment);
            }
        }
    }

    String[] items = {"Cerebral Palsy Alliance Singapore",
            "Singapore Association for the Deaf",
            "Thye Hua Kwan Moral Charities", "Food from The Heart"};
    private void showMessage(final View view) {
        new MaterialAlertDialogBuilder(this, R.style.Theme_MaterialComponents_Light_Dialog_Alert)
                .setTitle("Default Charity")
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemID = i;
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'YES' Button

                        // WRITE CODES TO UPDATE DEFAULT CHARITY DATABASE IMMEDIATELY
//                              userObject.setDoNotShowAgain(true);
                        if (items[itemID] == "Cerebral Palsy Alliance Singapore") {
                            object.setCharity("CPAS");
                            object.setTransCharity("CPAS");
                            object.setSetDefaultCharityDNSA(true);
                            getCharityInfo("CPAS");
                        }
                        else if (items[itemID] == "Singapore Association for the Deaf") {
                            object.setCharity("SAD");
                            object.setTransCharity("SAD");
                            object.setSetDefaultCharityDNSA(true);
                            getCharityInfo("SAD");
                        }
                        else if (items[itemID] == "Thye Hua Kwan Moral Charities") {
                            object.setCharity("THKMC");
                            object.setTransCharity("THKMC");
                            object.setSetDefaultCharityDNSA(true);
                            getCharityInfo("THKMC");
                        }
                        else if (items[itemID] == "Food from The Heart") {
                            object.setCharity("FFTH");
                            object.setTransCharity("FFTH");
                            object.setSetDefaultCharityDNSA(true);
                            getCharityInfo("FFTH");
                        }

                        //Go to payment confirm
                        Intent dialogMoveToPayment= new Intent(view.getContext(), Payment.class);
                        startActivity(dialogMoveToPayment);
                        dialog.dismiss();
                    }})

                .setNegativeButton("Don't Show Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button

                        // WRITE CODES TO UPDATE setDefaultCharityDNSA TO TRUE DATABASE IMMEDIATELY

                        object.setSetDefaultCharityDNSA(true);
                        // Get Items from object class and set to variable
                        ObjSetter();
                        Log.d(TAG,"==========================" +udoPassword);
                        //place variables into object class used in dynamoMapper
                        updateAWSObjSetter(
                        udoUserId,
                        udoPassword,
                        udoBalance ,
                        udoBankAccountNumber,
                        udoCharity ,
                        udoName ,
                        udoLastTransaction,
                        udoDoNotShowAgain , true,
                        udoAutoDonation
                        );

                        // Feed object class into aws runnable
                        updateAWS(userAWSDO);


                        //Go to payment confirm
                        Intent dialogMoveToPaymentConfirm= new Intent(view.getContext(), PaymentConfirmHide.class);
                        startActivity(dialogMoveToPaymentConfirm);
                        dialog.dismiss();
                    }})
                .show();
    }

    public void updateAWS(final Object dataObj) {
        aws();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(dataObj);
                Log.d(TAG,"Update Complete!!");
            }
        }).start();
    }

    public void aws() {
        DynamoDBMapper dynamoDBMapper;
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

    public void getCharityInfo(final String charityID){
        thread = new Thread() {
            @Override
            public void run() {
                    CharitiesDO charityItem = dynamoDBMapper.load(CharitiesDO.class, charityID);
                    dbCharityID = charityItem.getUserId();
                    dbCharityName = charityItem.getCharityDescription();
                    dbCharityBal= charityItem.getCharityBalance();
                    object.setCharityUserId(dbCharityID);
                    object.setCharityBalance(dbCharityBal);
                    object.setCharityDescription(dbCharityName);
            }
        };
        thread.start();
    }

    public void getMerchantInfo(final String merchantID) {
        thread1 = new Thread() {
            @Override
            public void run() {
                MerchantDO merchantItem = dynamoDBMapper.load(MerchantDO.class, merchantID);
                String asdf = merchantItem.getMerchantName();
                object.setMerchantUserId(merchantItem.getUserId());
                object.setMerchantBalance(merchantItem.getMerchantBalance());
                object.setMerchantName(asdf);
                Log.d(TAG, object.getMerchantName() + " INSIDE METHOD");
            }
        };
        thread1.start();
    }


    public void ObjSetter() {
        udoUserId = object.getUsername();
        udoPassword = object.getPassword();
        udoBalance = object.getBalance();
        udoBankAccountNumber = object.getBankAccount();
        udoCharity = object.getCharity();
        udoName = object.getName();
        udoLastTransaction = object.getLastTransaction();
        udoDoNotShowAgain = object.getDoNotShowAgain();
        udoSetDefaultCharityDNSA = object.getSetDefaultCharityDNSA();
        udoAutoDonation = object.getAutoDonation();
    }

    public void updateAWSObjSetter(
            String id, String pass, double bal, int bankNo, String charityAWS, String nameAWS, List<String> lt,
            boolean dnsa, boolean setDefaultCharitydnsa, boolean auto) {

        userAWSDO.setUserId(id);
        userAWSDO.setPassword(pass);
        userAWSDO.setBalance(bal);
        userAWSDO.setBankAccount(bankNo);
        userAWSDO.setCharity(charityAWS);
        userAWSDO.setName(nameAWS);
        userAWSDO.setLastTransaction(lt);
        userAWSDO.setDoNotShowAgain(dnsa);
        userAWSDO.setSetDefaultCharityDNSA(setDefaultCharitydnsa);
        userAWSDO.setAutoDonation(auto);


    }

}
