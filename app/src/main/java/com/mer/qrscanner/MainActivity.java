package com.mer.qrscanner;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import androidx.annotation.NonNull;

public class MainActivity extends Activity {
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scannerView = findViewById(R.id.scannerview);
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) ;
        {
            mCodeScanner = new CodeScanner(this, scannerView);
            startQRScnning();
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) ;
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) ;
        {
            mCodeScanner.startPreview();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == -1) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            startQRScnning();
        }
    }


    @Override
    protected void onPause() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) ;
        {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }

    protected void startQRScnning() {
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}