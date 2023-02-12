package com.example.vendorlyvendorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity {
EditText name,url,price;
Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        url = findViewById(R.id.imgurl);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });
    }

    private void addProduct() {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("price",price.getText().toString());
        map.put("imgurl",url.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Products").push().setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddData.this, "Product Added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddData.this,Homepage.class);
                        startActivity(i);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddData.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}