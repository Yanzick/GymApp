package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Chose extends AppCompatActivity {
    private Button BMI;
    private Button Food;
    private FirebaseFirestore db;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose);
        BMI = findViewById(R.id.ChoseBMI);
        Food = findViewById(R.id.ChoseFood);
        Intent intent = getIntent();
        name = intent.getStringExtra("Ho va Ten");
        Log.d("Ten", "Tên : " + name);
        BMI.setOnClickListener(view -> addEmployeeToFirestore1());
        Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Chose.this, Food.class);
                i.putExtra("Ho va Ten",name);
                startActivity(i);
            }
        });
    }
    private void addEmployeeToFirestore1() {
        db = FirebaseFirestore.getInstance();

        // Lấy thông tin từ EditText

        // Tạo một đối tượng Map để lưu thông tin nhân viên
        Map<String, Object> NhanVien = new HashMap<>();
        NhanVien.put("Name", name);

        // Thêm dữ liệu vào Firestore

        db.collection("BMI").document(name)
                .set(NhanVien)
                .addOnSuccessListener(documentReference -> {
                    // Thêm dữ liệu thành công
                    String result = "Đã thêm: " + name;
                    Intent i = new Intent(Chose.this, IfBMI.class);
                    i.putExtra("Ho va Ten",name);
                    startActivity(i);
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi thêm dữ liệu thất bại
                    String result = "Lỗi khi thêm : " + e.getMessage();
                    Log.e("FirestoreError", result, e);
                });
    }

}