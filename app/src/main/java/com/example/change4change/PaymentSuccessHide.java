package com.example.change4change;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentSuccessHide extends AppCompatActivity {

    private static final String TAG = PaymentSuccessHide.class.getName();

    StoredData object = new StoredData();
    Thread thread = new Thread();

    Double Balance = 0.0;
    int BankAccount = 0;
    String Charity = "";
    String Name = "";
    List<String> LastTransaction;
    boolean AutoDonation = false;
    boolean DoNotShowAgain = false;
    boolean SetDefaultCharityDNSA = false;

    int bankAccountNumber;
    String merchantName ="";
    String merchantId;
    String bankAccountNumberFormatter ="";
    String FormattedBankAccount = "";
    DecimalFormat df = new DecimalFormat("#0.00");

    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success_hide);

        final TextView lbBankBalanceInfo = findViewById(R.id.lbBankBalanceInfo);
        final TextView edMerchantAmt = findViewById(R.id.edMerchantAmt);

        MaterialButton btnConfirm = findViewById(R.id.btnBackToHome);

        final TextView lbBankAccountNo = findViewById(R.id.lbBankAccountNo);
        final TextView lbMerchantName = findViewById(R.id.lbMerchantName);
        final TextView lbMerchantInfo = findViewById(R.id.lbMerchantInfo);



        String merchantFormatted = df.format(object.getTransAmt());
        String totalFormatted = df.format(object.getTransAmt() + object.getTransAmtCharity());

        bankAccountNumber = object.getBankAccount();
        merchantId = object.getMerchantUserId();
        merchantName = object.getMerchantName();
        bankAccountNumberFormatter = String.valueOf(bankAccountNumber);

        lbBankBalanceInfo.setText(totalFormatted);
        edMerchantAmt.setText(merchantFormatted);

        if (bankAccountNumberFormatter.length() > 8) {
            FormattedBankAccount = bankAccountNumberFormatter.substring(0,3)+ "-" + bankAccountNumberFormatter.substring(3,8) + "-" + bankAccountNumberFormatter.substring(8);
        }
        else {
            FormattedBankAccount = bankAccountNumberFormatter;
        }

        initializeData(lbBankAccountNo, lbMerchantName, lbMerchantInfo);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aws();
                setUserObj(object.getUsername(), object.getPassword());
                // Code here executes on main thread after user presses button
                Intent moveToPaymentSuccess = new Intent(PaymentSuccessHide.this, HomeActivity.class);
                moveToPaymentSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(moveToPaymentSuccess);
                finish();

            }
        });


    }

    public void initializeData(TextView lbBankAccountNo, TextView lbMerchantName, TextView lbMerchantInfo){
        lbBankAccountNo.setText(FormattedBankAccount);
        lbMerchantName.setText(merchantName);
        lbMerchantInfo.setText(merchantId);
    }

    public void setUserObj (final String u, final String p) {
        thread = new Thread() {
            @Override
            public void run() {
                UserDO userItem = dynamoDBMapper.load(UserDO.class, u, p);

                Balance = userItem.getBalance();
                BankAccount = userItem.getBankAccount();
                Charity = userItem.getCharity();
                Name =userItem.getName();
                LastTransaction = userItem.getLastTransaction();
                AutoDonation = userItem.getAutoDonation();
                DoNotShowAgain = userItem.getDoNotShowAgain();
                SetDefaultCharityDNSA = userItem.getSetDefaultCharityDNSA();

                object.setUsername(u);
                object.setPassword(p);
                object.setBalance(Balance);
                object.setBankAccount(BankAccount);
                object.setCharity(Charity);
                object.setLastTransaction(LastTransaction);
                object.setName(Name);
                object.setAutoDonation(AutoDonation);
                object.setDoNotShowAgain(DoNotShowAgain);
                object.setSetDefaultCharityDNSA(SetDefaultCharityDNSA);
            }
        };
        thread.start();

    }

    public void aws(){
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d(TAG, "AWSMobileClient is instantiated and you are connected to AWS!");
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


}