package com.example.change4change;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentConfirmHide extends AppCompatActivity {

    private static final String TAG = PaymentConfirmHide.class.getName();

    DynamoDBMapper dynamoDBMapper;
    StoredData object = new StoredData();
    UserDO userDO = new UserDO();
    MerchantDO merchantDO = new MerchantDO();
    TransactionsDO transactionsDO = new TransactionsDO();

    LocalDateTime today = LocalDateTime.now();
    String s = today.toString().replaceAll("([:.-])", "");
    List<String> udoLastTransaction;

    String roundValue ="";

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
        setContentView(R.layout.activity_payment_confirm_hide);

        final EditText edMerchantAmt = findViewById(R.id.edMerchantAmt);
        final TextView lbBankBalanceInfo = findViewById(R.id.lbBankBalanceInfo);
        MaterialButton btnConfirm = findViewById(R.id.btnConfirm);

        final TextView lbBankAccountNo = findViewById(R.id.lbBankAccountNo);
        final TextView lbMerchantName = findViewById(R.id.lbMerchantName);
        final TextView lbMerchantInfo = findViewById(R.id.lbMerchantInfo);

        bankAccountNumber = object.getBankAccount();
        merchantId = object.getMerchantUserId();
        merchantName = object.getMerchantName();
        bankAccountNumberFormatter = String.valueOf(bankAccountNumber);

        if (bankAccountNumberFormatter.length() > 8) {
            FormattedBankAccount = bankAccountNumberFormatter.substring(0,3)+ "-" + bankAccountNumberFormatter.substring(3,8) + "-" + bankAccountNumberFormatter.substring(8);
        }
        else {
            FormattedBankAccount = bankAccountNumberFormatter;
        }

        initializeData(lbBankAccountNo, lbMerchantName, lbMerchantInfo);

        edMerchantAmt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                roundValue = edMerchantAmt.getText().toString();
                try {
                    totalAmt = DecimalFormat.getNumberInstance().parse(roundValue).doubleValue();
                } catch (ParseException e) {
                    Log.d (TAG , e.toString());
                }
                String totalFormatted = df.format(totalAmt);
                lbBankBalanceInfo.setText(totalFormatted);
                return false;
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                updateDB();
                object.setTransAmt(Double.valueOf(edMerchantAmt.getText().toString()));
                Intent moveToPaymentSuccess = new Intent(PaymentConfirmHide.this, PaymentSuccessHide.class);
                startActivity(moveToPaymentSuccess);
            }
        });
    }

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

    //Update Merchant Balance balance
    public void updateMerchantData() {
        merchantDO.setUserId(object.getMerchantUserId());
        merchantDO.setMerchantBalance(object.getMerchantBalance() + totalAmt);
        merchantDO.setMerchantName(object.getMerchantName());
    }

    public void updateTransactionData() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String sd = formatter.format(date);

        transactionsDO.setUserId(s);
        transactionsDO.setTransAmt(totalAmt);
        transactionsDO.setTransAmtCharity(0.0);
        transactionsDO.setTransCharity("");
        transactionsDO.setTransDate(sd);
        transactionsDO.setTransMerchant(object.getMerchantUserId());
        transactionsDO.setTransUser(object.getUsername());
    }

    //Update AWS database
    public void updateDB() {
        updateUserData();
        updateMerchantData();
        updateTransactionData();
        aws();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(userDO);
                dynamoDBMapper.save(merchantDO);
                dynamoDBMapper.save(transactionsDO);

                Log.d(TAG,"Update Success!!");
                // Item saved
            }
        }).start();
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

    public void initializeData(TextView lbBankAccountNo, TextView lbMerchantName, TextView lbMerchantInfo){
        lbBankAccountNo.setText(FormattedBankAccount);
        lbMerchantName.setText(merchantName);
        lbMerchantInfo.setText(merchantId);
    }

}
