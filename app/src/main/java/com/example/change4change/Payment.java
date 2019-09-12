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

import androidx.appcompat.app.AppCompatActivity;

public class Payment extends AppCompatActivity {
    // Tagging for Logcat
    private static final String TAG = Payment.class.getName();

    //For usage of Edit View for math
    double roundValue =0.0;
    double merchantValue = 0.0;
    int upperLimitValue = 0;
    double charityValue = 0;
    double totalValue = 0.0;

    //
    int bankAccountNumber;
    String merchantName ="";
    String merchantId;
    String charityId;
    String charityName;
    String bankAccountNumberFormatter ="";
    String FormattedBankAccount = "";
    boolean autoDonation = false;

    DecimalFormat df = new DecimalFormat("#0.00");
    StoredData object = new StoredData();

    //Aws
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final EditText edMerchantAmt = findViewById(R.id.edMerchantAmt);
        final EditText edCharityAmt = findViewById(R.id.edCharityAmt);
        final TextView lbBankBalanceInfo = findViewById(R.id.lbBankBalanceInfo);

        final TextView lbBankAccountNo = findViewById(R.id.lbBankAccountNo);
        final TextView lbMerchantName = findViewById(R.id.lbMerchantName);
        final TextView lbMerchantInfo = findViewById(R.id.lbMerchantInfo);
        final TextView lbCharityName = findViewById(R.id.lbCharityName);
        final TextView lbCharityInfo = findViewById(R.id.lbCharityInfo);

        final MaterialButton btnMakePayment = findViewById(R.id.btnMakePayment);
        Log.d(TAG, "======================= " + object.getPassword() + " =======================");

        //Initialize views to display dynamic data
//        getData();

        bankAccountNumber = object.getBankAccount();
        merchantId = object.getMerchantUserId();
        merchantName = object.getMerchantName();
        charityId = object.getCharityUserId();
        charityName = object.getCharityDescription();
        bankAccountNumberFormatter = String.valueOf(bankAccountNumber);
        autoDonation = object.getAutoDonation();

        if (autoDonation) {
            edCharityAmt.setEnabled(false);
        }

        if (bankAccountNumberFormatter.length() > 8) {
            FormattedBankAccount = bankAccountNumberFormatter.substring(0,3)+ "-" + bankAccountNumberFormatter.substring(3,8) + "-" + bankAccountNumberFormatter.substring(8);
        }
        else {
            FormattedBankAccount = bankAccountNumberFormatter;
        }

        initializeData(lbBankAccountNo, lbMerchantName, lbMerchantInfo, lbCharityName, lbCharityInfo);


        // Change Charity Amount when price of transaction changes
        edMerchantAmt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String parser = edMerchantAmt.getText().toString();
                try {
                    roundValue = DecimalFormat.getNumberInstance().parse(parser).doubleValue();
                } catch (ParseException e) {
                    Log.d (TAG , e.toString());
                }
                if (roundValue %1 !=0) {
                    upperLimitValue = (int) roundValue +1;
                    charityValue = (double) upperLimitValue - roundValue;
                }
                else {
                    charityValue = 0;
                }
                totalValue = charityValue + roundValue;

                String charityFormatted = df.format(charityValue);
                String totalFormatted = df.format(totalValue);

                edCharityAmt.setText(charityFormatted);
                lbBankBalanceInfo.setText(totalFormatted);

                return false;
            }
        });

        edCharityAmt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                try {
                    String parser = edCharityAmt.getText().toString();
                    roundValue = DecimalFormat.getNumberInstance().parse(parser).doubleValue();

                    String parser1 = edMerchantAmt.getText().toString();
                    merchantValue =DecimalFormat.getNumberInstance().parse(parser1).doubleValue();

                } catch (ParseException e) {
                    Log.d (TAG , e.toString());
                }
                totalValue = roundValue + merchantValue;
                String totalFormatted = df.format(totalValue);
                lbBankBalanceInfo.setText(totalFormatted);

                return false;
            }
        });

        btnMakePayment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                object.setTransAmtCharity(Double.valueOf(edCharityAmt.getText().toString()));
                object.setTransAmt(Double.valueOf(edMerchantAmt.getText().toString()));
                Intent moveToPaymentConfirm = new Intent(Payment.this, PaymentConfirm.class);
                startActivity(moveToPaymentConfirm);
            }
        });

    }

    public void initializeData(TextView lbBankAccountNo, TextView lbMerchantName, TextView lbMerchantInfo, TextView lbCharityName, TextView lbCharityInfo){
        lbBankAccountNo.setText(FormattedBankAccount);
        lbMerchantName.setText(merchantName);
        lbMerchantInfo.setText(merchantId);
        lbCharityName.setText(charityName);
        lbCharityInfo.setText(charityId);
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