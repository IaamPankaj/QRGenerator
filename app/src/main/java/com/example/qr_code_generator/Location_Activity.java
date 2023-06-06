package com.example.qr_code_generator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Location_Activity extends AppCompatActivity {

    ImageView locationQR;
    TextInputEditText edLat,edLong;
    Button btnLocation;

    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationQR = findViewById(R.id.locationQR);
        edLat = findViewById(R.id.edLat);
        edLong = findViewById(R.id.edLong);
        btnLocation = findViewById(R.id.btnLocation);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                generateQRCode();
            }
        });
    }
    private void generateQRCode() {
        // Get user input latitude and longitude from the edit texts
        String latitudeInput = edLat.getText().toString().trim();
        String longitudeInput = edLong.getText().toString().trim();

        // Validate user input latitude and longitude using regular expressions
        Pattern pattern = Pattern.compile("^-?\\d{1,2}(\\.\\d{1,6})?$");
        Matcher latitudeMatcher = pattern.matcher(latitudeInput);
        Matcher longitudeMatcher = pattern.matcher(longitudeInput);
        if (!latitudeMatcher.matches() || !longitudeMatcher.matches()) {
            // Show error message if the latitude or longitude is invalid
            Toast.makeText(this, "Invalid location. Please enter a latitude and longitude in decimal degrees, separated by a comma", Toast.LENGTH_LONG).show();
            return;
        }

        // Generate QR code bitmap using ZXing library
        String location = latitudeInput + "," + longitudeInput;
        int qrCodeSize = 500;
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(location, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);
            Bitmap qrCodeBitmap = Bitmap.createBitmap(qrCodeSize, qrCodeSize, Bitmap.Config.RGB_565);
            for (int x = 0; x < qrCodeSize; x++) {
                for (int y = 0; y < qrCodeSize; y++) {
                    qrCodeBitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
                }
            }
            locationQR.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating QR code", Toast.LENGTH_LONG).show();
        }
    }
}