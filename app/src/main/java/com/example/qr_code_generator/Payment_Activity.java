package com.example.qr_code_generator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

public class Payment_Activity extends AppCompatActivity {

    Button generate_button;
    ImageView qrr;
    EditText network_name_edittext,password_edittext,encryption_edittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button generateButton = findViewById(R.id.generate_button);


        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQRCode();
            }
        });
    }

    private void generateQRCode() {
        EditText networkNameEditText = findViewById(R.id.network_name_edittext);
        EditText passwordEditText = findViewById(R.id.password_edittext);
        EditText encryptionEditText = findViewById(R.id.encryption_edittext);

        String networkName = networkNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String encryptionType = encryptionEditText.getText().toString();

        WifiUtil wifiUtil = new WifiUtil();
        String wifiDetails = wifiUtil.createWiFiConfigString(networkName, password, encryptionType);
        Bitmap qrCodeBitmap = wifiUtil.encodeAsBitmap(wifiDetails);
        ImageView qrCodeImageView = findViewById(R.id.qrr);
        qrCodeImageView.setImageBitmap(qrCodeBitmap);
    }

    private class WifiUtil {
        public String createWiFiConfigString(String networkName, String password, String encryptionType) {
            return networkName;
        }

        @NonNull
        @Contract(pure = true)
        public Bitmap encodeAsBitmap(String wifiDetails) {
            return encodeAsBitmap(wifiDetails);
        }
    }
}