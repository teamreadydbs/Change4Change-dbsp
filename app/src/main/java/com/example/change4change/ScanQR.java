package com.example.change4change;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.zxing.Result;
import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQR extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private static final String TAG = ScanQR.class.getName();

    ZXingScannerView scannerView;
    String Merchant = "";
    View scanQR;
    StoredData object = new StoredData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        scanQR = findViewById(android.R.id.content);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {
        Merchant = result.getText();
        object.setMerchantUserId(Merchant);
        Log.d(TAG, object.getPassword() + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Intent donateMoveToPaymentConfirm= new Intent(ScanQR.this, SpinnerActivity.class);
        startActivity(donateMoveToPaymentConfirm);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}