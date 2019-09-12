package com.example.change4change;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    // Get From DB
    String LoginUsername ="";
    String LoginPassword ="";
    Double Balance = 0.0;
    int BankAccount = 0;
    String Charity = "";
    String Name = "";
    List<String> LastTransaction;
    boolean AutoDonation = false;
    boolean DoNotShowAgain = false;
    boolean SetDefaultCharityDNSA = false;
    UserDO userItem = new UserDO();

    TextInputLayout usernameTextInput;
    TextInputEditText usernameEditText;
    TextInputLayout passwordTextInput;
    TextInputEditText passwordEditText;
    MaterialButton nextButton;

    private DynamoDBMapper dynamoDBMapper;
    Thread thread = new Thread();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameTextInput = findViewById(R.id.username_text_input);
        usernameEditText = findViewById(R.id.username_edit_input);
        passwordTextInput = findViewById(R.id.password_text_input);
        passwordEditText = findViewById(R.id.password_edit_text);
        nextButton = findViewById(R.id.next_button);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                usernameEditText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (isPasswordValid(usernameEditText.getText())) {
                            usernameTextInput.setError(null); //Clear the error
                        }
                        return true;
                    }
                });

                // Clear the error once more than 8 characters are typed.
                passwordEditText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (isPasswordValid(passwordEditText.getText())) {
                            passwordTextInput.setError(null); //Clear the error
                        }
                        return true;
                    }
                });
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showToast(String s, Boolean type) {
        View contextView = findViewById(R.id.next_button);
        if (type) {
            Snackbar.make(contextView, s, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.color_error))
                    .show();
        }
        else  {
            Snackbar.make(contextView, s, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.color_primary))
                    .show();
        }

    }

    public void validate() {
        showToast("Logging In...", false);
        hideKeyboard(this);
        LoginUsername = usernameEditText.getText().toString();
        LoginPassword = passwordEditText.getText().toString();
        aws();
        readUser(LoginUsername,LoginPassword);
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

    public void readUser(final String LoginUsername, final String LoginPassword) {
        if (LoginUsername.equals("")||LoginPassword.equals("")) {
            showToast("A field is blank. Please enter your username and password", true);
        }
        else {
            thread = new Thread() {
                @Override
                public void run() {
                    UserDO userItem = dynamoDBMapper.load(
                            UserDO.class,
                            LoginUsername,
                            LoginPassword);
                    if (userItem == null) {
                        showToast("Incorrect username or password", true);
                        return;
                    }
                    else {
                        Balance = userItem.getBalance();
                        BankAccount = userItem.getBankAccount();
                        Charity = userItem.getCharity();
                        Name =userItem.getName();
                        LastTransaction = userItem.getLastTransaction();
                        AutoDonation = userItem.getAutoDonation();
                        DoNotShowAgain = userItem.getDoNotShowAgain();
                        SetDefaultCharityDNSA = userItem.getSetDefaultCharityDNSA();

                        StoredData test = new StoredData();
                        test.setUsername(LoginUsername);
                        test.setPassword(LoginPassword);
                        test.setBalance(Balance);
                        test.setBankAccount(BankAccount);
                        test.setCharity(Charity);
                        test.setLastTransaction(LastTransaction);
                        test.setName(Name);
                        test.setAutoDonation(AutoDonation);
                        test.setDoNotShowAgain(DoNotShowAgain);
                        test.setSetDefaultCharityDNSA(SetDefaultCharityDNSA);
                        loginSuccess();
                    }
                }

            };
            thread.start();
        }
    }

    public void loginSuccess() {
        // Navigate to the next Activity
        Intent intent= new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 3;
    }
}
