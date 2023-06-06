package com.example.qr_code_generator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class Details_Activity extends AppCompatActivity {

    TextInputEditText edName,edOrg,edEmail,edPhone,edWebsite;

    ImageView imgDetailQR;
    Button detailSubmit;
    DatabaseReference detailRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        edName = findViewById(R.id.edName);
        edOrg = findViewById(R.id.edOrg);
        edEmail = findViewById(R.id.edEmail);
        edPhone = findViewById(R.id.edPhone);
        edWebsite = findViewById(R.id.edWebsite);

        detailSubmit = findViewById(R.id.detailSubmit);
        imgDetailQR = findViewById(R.id.imgDetailQR);

        detailRef = FirebaseDatabase.getInstance().getReference().child("Details_QR_Generators");

        detailSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id=detailRef.push().getKey();

                HashMap<String,Object> map = new HashMap<>();

                map.put("NameInput",edName.getText().toString());
                map.put("OrganizationInput",edOrg.getText().toString());
                map.put("EmailInput",edEmail.getText().toString());
                map.put("PhoneInput",edPhone.getText().toString());
                map.put("WebsiteInput",edWebsite.getText().toString());

                detailRef.child(id)
                        .setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    String name = edName.getText().toString().trim();
                                    String organization = edOrg.getText().toString().trim();
                                    String email = edEmail.getText().toString().trim();
                                    String phonenumber = edPhone.getText().toString().trim();
                                    String website = edWebsite.getText().toString().trim();

                                    StringBuilder sb = new StringBuilder();
                                    sb.append(name + " | " + organization + " |" + email + " | " +
                                            phonenumber + " | " + website);

                                    if(!name.isEmpty() && !organization.isEmpty() && !email.isEmpty() &&
                                            !phonenumber.isEmpty() && !website.isEmpty()){

                                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                        try {
                                            BitMatrix bitMatrix = multiFormatWriter.encode(sb.toString(),
                                            BarcodeFormat.QR_CODE, 600, 600);
                                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                            imgDetailQR.setImageBitmap(bitmap);
                                            imgDetailQR.setVisibility(View.VISIBLE);

                                        } catch (WriterException e) {
                                            e.printStackTrace();
                                        }

                                    }else {
                                        Toast.makeText(Details_Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

                                    }                                }
                                else {
                                    Toast.makeText(Details_Activity.this, "Unsuccessful Store", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

}




