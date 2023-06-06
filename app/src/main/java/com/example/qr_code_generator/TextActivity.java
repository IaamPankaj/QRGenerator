package com.example.qr_code_generator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;

public class TextActivity extends AppCompatActivity {

    ImageView textQR;
    TextInputEditText edText;
    Button btnSubmit;

    DatabaseReference textData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        textQR = findViewById(R.id.textQR);
        edText = findViewById(R.id.edText);
        btnSubmit = findViewById(R.id.btnSubmit);

        textData = FirebaseDatabase.getInstance().getReference().child("Text_Generator");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id=textData.push().getKey();

                HashMap<String,Object> map = new HashMap<>();

                map.put("TextInput",edText.getText().toString());

                textData.child(id)
                        .setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    String text = edText.getText().toString().trim();

                                    if(!text.isEmpty()){

                                        StringBuilder sb = new StringBuilder();
                                        sb.append(text + " | " + edText);

                                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                        try {
                                            BitMatrix bitMatrix = multiFormatWriter.encode(sb.toString(),
                                                    BarcodeFormat.QR_CODE, 600, 600);
                                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                            textQR.setImageBitmap(bitmap);
                                            textQR.setVisibility(View.VISIBLE);

                                        } catch (WriterException e) {
                                            e.printStackTrace();
                                        }
                                    }else {
                                        Toast.makeText(TextActivity.this, "Please fill the fields", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else {
                                    Toast.makeText(TextActivity.this, "Please enter proper text", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

}