package com.example.qr_code_generator;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class URL_Activity extends AppCompatActivity {

    ImageView urlQR;
    TextInputEditText edUrl;
    Button btnURL;

    private static final String QR_CODE_FILE_NAME = "qr_code.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        urlQR = findViewById(R.id.urlQR);
        edUrl = findViewById(R.id.edUrl);
        btnURL = findViewById(R.id.btnURL);

        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = ((EditText) findViewById(R.id.edUrl)).getText().toString();
                if (!url.isEmpty()) {
                    Bitmap qrBitmap = generateQrCodeBitmap(url);
                    ImageView qrImageView = findViewById(R.id.urlQR);
                    qrImageView.setImageBitmap(qrBitmap);
                    saveQrCode(qrBitmap);
                } else {
                    Toast.makeText(URL_Activity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black)
                            : getResources().getColor(R.color.white));
                }
            }

            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveQrCode(Bitmap qrBitmap) {
        File qrCodeFile = new File(getExternalCacheDir(), QR_CODE_FILE_NAME);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(qrCodeFile);
            qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            Toast.makeText(URL_Activity.this, "QR code saved to " + qrCodeFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(URL_Activity.this, "Failed to save QR code", Toast.LENGTH_SHORT).show();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}