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

public class IfBMI extends AppCompatActivity {
    private EditText HoTen, ChieuCao, Cannang, v1, v2, v3, BMI;
    private Button Capnhat;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_if_bmi);
        HoTen = findViewById(R.id.Name_BMI);
        ChieuCao = findViewById(R.id.ChieuCao);
        Cannang = findViewById(R.id.CanNang);
        db = FirebaseFirestore.getInstance();
        v1 = findViewById(R.id.Vong1);
        v2 = findViewById(R.id.Vong2);
        v3 = findViewById(R.id.Vong3);
        BMI = findViewById(R.id.ChisoBMI);
        Capnhat = findViewById(R.id.CapNhat);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Ho va Ten");
        getDataFromFirestore(name);
        Capnhat.setOnClickListener(view -> {
            // Thu thập dữ liệu từ các trường EditText
            String hoTen = HoTen.getText().toString();
            String chieuCao = ChieuCao.getText().toString();
            String canNang = Cannang.getText().toString();
            String vong1 = v1.getText().toString();
            String vong2 = v2.getText().toString();
            String vong3 = v3.getText().toString();
            String chiSoBMI = BMI.getText().toString();

            // Tạo một bản ghi mới trong Firestore
            addDataToFirestore(hoTen, chieuCao, canNang, vong1, vong2, vong3, chiSoBMI);
        });
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
    private void addDataToFirestore(String hoTen, String chieuCao, String canNang, String vong1, String vong2, String vong3, String chiSoBMI) {
        // Tạo một đối tượng Map để lưu thông tin BMI
        Map<String, Object> data = new HashMap<>();
        data.put("Name", hoTen);
        data.put("chieucao", chieuCao);
        data.put("cannang", canNang);
        data.put("vong1", vong1);
        data.put("vong2", vong2);
        data.put("vong3", vong3);
        data.put("bmi", chiSoBMI);

        // Thêm dữ liệu vào Firestore
        db.collection("BMI").document(hoTen)
                .set(data)
                .addOnSuccessListener(aVoid -> {
                    // Xử lý khi thêm dữ liệu thành công
                    Log.d("IfBMI", "Thêm dữ liệu thành công");
                    Intent intent = getIntent();
                    finish(); // Đóng Activity hiện tại
                    startActivity(intent);
                    // Hiển thị thông báo hoặc thực hiện các hành động khác tùy theo yêu cầu của bạn
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi thêm dữ liệu thất bại
                    Log.e("IfBMI", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                    // Hiển thị thông báo hoặc thực hiện các hành động khác tùy theo yêu cầu của bạn
                });
    }


}