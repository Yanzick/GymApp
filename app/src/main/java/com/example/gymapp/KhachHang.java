package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gymapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.K;

import java.util.HashMap;
import java.util.Map;

public class KhachHang extends AppCompatActivity {

    private EditText cccdEditText, fullnameEditText, emailEditText, stdEditText, maNvEditText, matKhauEditText;
    private TextView resultTextView;
    private Button dangKiButton;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        // Ánh xạ các thành phần từ layout
        fullnameEditText = findViewById(R.id.HoVaten3);

        emailEditText = findViewById(R.id.EmalNV3);
        stdEditText = findViewById(R.id.STD3);
        dangKiButton = findViewById(R.id.DangKiNV3);
        db = FirebaseFirestore.getInstance();
        // Xử lý sự kiện khi người dùng nhấn nút Đăng Ký Nhân Viên
        dangKiButton.setOnClickListener(view -> addEmployeeToFirestore());
    }

    private void addEmployeeToFirestore() {
        // Lấy thông tin từ EditText
        String fullname = fullnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String std = stdEditText.getText().toString();
        if (fullname.isEmpty() || email.isEmpty() || std.isEmpty()) {
            resultTextView.setText("Vui lòng nhập đầy đủ thông tin");
            return;
        }
        // Tạo một đối tượng Map để lưu thông tin nhân viên
        Map<String, Object> KhachHang = new HashMap<>();
        KhachHang.put("Ho va Ten", fullname);
        KhachHang.put("Email", email);
        KhachHang.put("STD", std);

        // Thêm dữ liệu vào Firestore

        db.collection("KhachHang").document(fullname)
                .set(KhachHang)
                .addOnSuccessListener(documentReference -> {
                    // Thêm dữ liệu thành công
                    String result = "Đã thêm Khách hàng: " + fullname;
                    resultTextView.setText(result);
                    reloadActivity();
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi thêm dữ liệu thất bại
                    String result = "Lỗi khi thêm nhân viên: " + e.getMessage();
                    Log.e("FirestoreError", result, e);
                    resultTextView.setText(result);
                });
        Map<String, Object> info = new HashMap<>();
    }
    private void reloadActivity() {
        Intent intent = new Intent(KhachHang.this,Home.class);
        startActivity(intent);
        finish();
    }
}
