package com.example.change4change;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentConfirm extends AppCompatActivity {

    // Tagging for Logcat
    private static final String TAG = PaymentConfirm.class.getName();

    DynamoDBMapper dynamoDBMapper;
    StoredData object = new StoredData();
    UserDO userDO = new UserDO();
    CharitiesDO charitiesDO = new CharitiesDO();
    MerchantDO merchantDO = new MerchantDO();
    TransactionsDO transactionsDO = new TransactionsDO();
    LocalDateTime today = LocalDateTime.now();
    String s = today.toString().replaceAll("([:.-])", "");
    List<String> udoLastTransaction;

    TextView lbBankBalanceInfo;

    int bankAccountNumber;
    double totalAmt = 0.0;
    String merchantName ="";
    String merchantId;
    String charityId;
    String charityName;
    String bankAccountNumberFormatter ="";
    String FormattedBankAccount = "";
    DecimalFormat df = new DecimalFormat("#0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm);

        totalAmt = object.getTransAmt() + object.getTransAmtCharity();

        lbBankBalanceInfo = findViewById(R.id.lbBankBalanceInfo);
        final TextView edMerchantAmt = findViewById(R.id.edMerchantAmt);
        final TextView edCharityAmt = findViewById(R.id.edCharityAmt);
        MaterialButton btnConfirm = findViewById(R.id.btnConfirm);

        final TextView lbBankAccountNo = findViewById(R.id.lbBankAccountNo);
        final TextView lbMerchantName = findViewById(R.id.lbMerchantName);
        final TextView lbMerchantInfo = findViewById(R.id.lbMerchantInfo);
        final TextView lbCharityName = findViewById(R.id.lbCharityName);
        final TextView lbCharityInfo = findViewById(R.id.lbCharityInfo);

        String merchantFormatted = df.format(object.getTransAmt());
        String charityFormatted = df.format(object.getTransAmtCharity());
        String totalFormatted = df.format(object.getTransAmt() + object.getTransAmtCharity());

        bankAccountNumber = object.getBankAccount();
        merchantId = object.getMerchantUserId();
        merchantName = object.getMerchantName();
        charityId = object.getCharityUserId();
        charityName = object.getCharityDescription();
        bankAccountNumberFormatter = String.valueOf(bankAccountNumber);

        lbBankBalanceInfo.setText(totalFormatted);
        edMerchantAmt.setText(merchantFormatted);
        edCharityAmt.setText(charityFormatted);

        if (bankAccountNumberFormatter.length() > 8) {
            FormattedBankAccount = bankAccountNumberFormatter.substring(0,3)+ "-" + bankAccountNumberFormatter.substring(3,8) + "-" + bankAccountNumberFormatter.substring(8);
        }
        else {
            FormattedBankAccount = bankAccountNumberFormatter;
        }

        initializeData(lbBankAccountNo, lbMerchantName, lbMerchantInfo, lbCharityName, lbCharityInfo);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                updateDB();
                Intent moveToPaymentSuccess = new Intent(PaymentConfirm.this, PaymentSuccess.class);
                startActivity(moveToPaymentSuccess);
                finish();
            }
        });

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


    //Update UserDO Balance & last trans

    public void updateUserData() {
        udoLastTransaction = object.getLastTransaction();
        udoLastTransaction.add(s);
        userDO.setUserId(object.getUsername());
        userDO.setPassword(object.getPassword());
        userDO.setAutoDonation(object.getAutoDonation());
        userDO.setBalance(object.getBalance() - totalAmt);
        userDO.setBankAccount(object.getBankAccount());
        userDO.setCharity(object.getCharity());
        userDO.setDoNotShowAgain(object.getDoNotShowAgain());
        userDO.setLastTransaction(udoLastTransaction);
        userDO.setName(object.getName());
        userDO.setSetDefaultCharityDNSA(object.getSetDefaultCharityDNSA());
    }

    //Update CharityDO balance
    public void updateCharityData() {
        charitiesDO.setUserId(object.getCharityUserId());
        charitiesDO.setCharityBalance(object.getCharityBalance() +object.getTransAmtCharity());
        charitiesDO.setCharityDescription(object.getCharityDescription());
    }

    //Update Merchant Balance balance
    public void updateMerchantData() {
        merchantDO.setUserId(object.getMerchantUserId());
        merchantDO.setMerchantBalance(object.getMerchantBalance() +object.getTransAmt());
        merchantDO.setMerchantName(object.getMerchantName());
    }

    public void updateTransactionData() {


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sd = formatter.format(date);

        transactionsDO.setUserId(s);
        transactionsDO.setTransAmt(object.getTransAmt());
        transactionsDO.setTransAmtCharity(object.getTransAmtCharity());
        transactionsDO.setTransCharity(object.getCharity());
        transactionsDO.setTransDate(sd);
        transactionsDO.setTransMerchant(object.getMerchantUserId());
        transactionsDO.setTransUser(object.getUsername());
    }

    //Update AWS database
    public void updateDB() {
        updateUserData();
        updateCharityData();
        updateMerchantData();
        updateTransactionData();
        aws();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(userDO);
                dynamoDBMapper.save(charitiesDO);
                dynamoDBMapper.save(merchantDO);
                dynamoDBMapper.save(transactionsDO);

                Log.d(TAG,"Update Success!!");
                // Item saved
            }
        }).start();
    }

    public void initializeData(TextView lbBankAccountNo, TextView lbMerchantName, TextView lbMerchantInfo, TextView lbCharityName, TextView lbCharityInfo){
        lbBankAccountNo.setText(FormattedBankAccount);
        lbMerchantName.setText(merchantName);
        lbMerchantInfo.setText(merchantId);
        lbCharityName.setText(charityName);
        lbCharityInfo.setText(charityId);
    }
}
