package com.example.qr_code_generator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Wifi_Activity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        Button generateQrButton = findViewById(R.id.generate_qr_button);

        generateQrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String networkName = ((EditText) findViewById(R.id.edNetwork)).getText().toString();
                String password = ((EditText) findViewById(R.id.edPassword)).getText().toString();
                String encryptionType = "";
                int encryptionRadioButtonId = ((RadioGroup) findViewById(R.id.encryption_radio_group)).getCheckedRadioButtonId();
                switch (encryptionRadioButtonId) {
                    case R.id.wpa2_psk_radio_button:
                        encryptionType = "WPA2";
                        break;
                    case R.id.wpa_psk_radio_button:
                        encryptionType = "WPA";
                        break;
                    case R.id.wep_radio_button:
                        encryptionType = "WEP";
                        break;
                }
                if (!networkName.isEmpty() && !password.isEmpty() && !encryptionType.isEmpty()) {
                    String wifiConfigString = generateWifiConfigString(networkName, password, encryptionType);
                    Log.d(TAG, "WiFi configuration string: " + wifiConfigString);
                    Bitmap qrBitmap = generateQrCodeBitmap(wifiConfigString);
                    ImageView qrImageView = findViewById(R.id.wifiQR);
                    qrImageView.setImageBitmap(qrBitmap);
                } else {
                    Toast.makeText(Wifi_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private String generateWifiConfigString(String ssid, String password, String encryptionType) {
        String wifiConfig = "WIFI:T:" + encryptionType + ";";
        wifiConfig += "S:" + ssid + ";";
        wifiConfig += "P:" + password + ";;";
        return wifiConfig;
    }

    private Bitmap generateQrCodeBitmap(String data) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}