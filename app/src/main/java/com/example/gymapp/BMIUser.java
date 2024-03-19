package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class BMIUser extends AppCompatActivity {
    private EditText HoTen, ChieuCao, Cannang, v1, v2, v3, BMI;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiuser);
        HoTen = findViewById(R.id.Name_BMIUser);
        ChieuCao = findViewById(R.id.ChieuCaoUser);
        Cannang = findViewById(R.id.CanNangUser);
        db = FirebaseFirestore.getInstance();
        v1 = findViewById(R.id.Vong1User);
        v2 = findViewById(R.id.Vong2User);
        v3 = findViewById(R.id.Vong3User);
        BMI = findViewById(R.id.ChisoBMIUser);
        Intent intent = getIntent();
        String name = intent.getStringExtra("tenKH");
        getDataFromFirestore(name);

    }
    private void getDataFromFirestore(String name) {
        db.collection("BMI").document(name)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Lấy dữ liệu từ tài liệu Firestore và hiển thị trong EditText tương ứng
                        HoTen.setText(documentSnapshot.getString("Name"));
                        ChieuCao.setText(documentSnapshot.getString("chieucao"));
                        Cannang.setText(documentSnapshot.getString("cannang"));
                        v1.setText(documentSnapshot.getString("vong1"));
                        v2.setText(documentSnapshot.getString("vong2"));
                        v3.setText(documentSnapshot.getString("vong3"));
                        BMI.setText(documentSnapshot.getString("bmi"));
                    } else {
                        // Xử lý khi tài liệu không tồn tại
                        HoTen.setText("Document does not exist");
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi truy vấn thất bại
                    HoTen.setText("Error getting document: " + e.getMessage());
                });
    }


}