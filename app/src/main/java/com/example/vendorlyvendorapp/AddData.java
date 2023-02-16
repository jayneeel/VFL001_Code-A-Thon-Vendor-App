package com.example.vendorlyvendorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity {
TextInputLayout name,price;
Button btnAdd,url;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String nameTxt,priceTxt,imgUrlTxt;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        getSupportActionBar().hide();
        name = findViewById(R.id.name_add);
        price = findViewById(R.id.price_add);
        url = findViewById(R.id.imgUrl);
        btnAdd = findViewById(R.id.btnAdd);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("VendorsTemp").child(uid).child("Products");
        nameTxt = name.getEditText().getText().toString();
        priceTxt = price.getEditText().getText().toString();

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImg();
            }
        });

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addProduct();
//            }
//        });
    }

    private void selectImg() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT IMAGE FILE"),12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            url.setEnabled(true);
            url.setText(data.getDataString()
                    .substring(data.getDataString().lastIndexOf("/")+1));
            imgUrlTxt = url.getText().toString();

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                uploadPDFFileFirebase(data.getData());
                }
            });
        }
    }

    private void uploadPDFFileFirebase(Uri data) {
        nameTxt = name.getEditText().getText().toString();
        priceTxt = price.getEditText().getText().toString();
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("File is loading");
        progressDialog.show();

        StorageReference reference = storageReference.child(nameTxt+System.currentTimeMillis()+".jpeg");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
//                        Toast.makeText(AddData.this, nameTxt+" "+priceTxt+" "+imgUrlTxt, Toast.LENGTH_SHORT).show();
                        System.out.println(uri.toString());
                        putProductData prodData = new putProductData(nameTxt, priceTxt,uri.toString());
                        databaseReference.child(prodData.name).setValue(prodData);
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String uid = firebaseUser.getUid().toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("VendorsTemp").child(uid).child("Products");

                        Toast.makeText(AddData.this,"File Upload",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(AddData.this,Homepage.class));
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("File Uploaded..."+(int)progress+"%");
                    }
                });
    }
    //    private void addProduct() {
//        Map<String,Object> map = new HashMap<>();
//        map.put("name",name.getText().toString());
//        map.put("price",price.getText().toString());
//        map.put("imgurl",url.getText().toString());
//        FirebaseDatabase.getInstance().getReference().child("Products").push().setValue(map)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(AddData.this, "Product Added", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(AddData.this,Homepage.class);
//                        startActivity(i);
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(AddData.this, "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}