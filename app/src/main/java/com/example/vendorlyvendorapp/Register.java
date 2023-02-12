package com.example.vendorlyvendorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    TextInputLayout shopname,propname,email,pass,phno,cpass,addr;
    Button btnSignup;
    AutoCompleteTextView autoCompleteTextView;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        autoCompleteTextView = findViewById(R.id.AutoCompleteTextview);
        String[] areas = new String[]{"Sector 1","Sector 2","Sector 3","Sector 4","Sector 5","Sector 6","Sector 7","Sector 8","Sector 9"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, areas);
        autoCompleteTextView.setAdapter(adapter);
        shopname = findViewById(R.id.shopname);
        propname = findViewById(R.id.propname);
        addr = findViewById(R.id.addr);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        phno = findViewById(R.id.phno);
        cpass = findViewById(R.id.cpass);
        btnSignup = findViewById(R.id.btnRegR);
        firebaseAuth = FirebaseAuth.getInstance();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getShopname = shopname.getEditText().getText().toString();
                String getPropname = propname.getEditText().getText().toString();
                String getphno = phno.getEditText().getText().toString();
                String getEmail = email.getEditText().getText().toString();
                String getAddr = addr.getEditText().getText().toString();
                String getPass = pass.getEditText().getText().toString();
                String getArea = autoCompleteTextView.getText().toString();
                Map<String,Object> map = new HashMap<>();
                map.put("shop_name",getShopname);
                map.put("prop_name",getPropname);
                map.put("email",getEmail);
                map.put("phno",getphno);
                map.put("area",getArea);
                map.put("addr",getAddr);
                FirebaseDatabase.getInstance().getReference().child("Vendors").push().setValue(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Register.this, "Business Account Created", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Register.this, "Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                firebaseAuth.createUserWithEmailAndPassword(getEmail, getPass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Register.this, "User Account Created", Toast.LENGTH_SHORT).show();
                                Intent it = new Intent(Register.this, Login.class);
                                startActivity(it);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}