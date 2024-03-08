package com.example.gymapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gymapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class ResMem extends AppCompatActivity {

    private EditText cccdEditText, fullnameEditText, emailEditText, stdEditText, maNvEditText, matKhauEditText;
    private TextView resultTextView;
    private Button dangKiButton;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_mem);

        // Ánh xạ các thành phần từ layout
        cccdEditText = findViewById(R.id.CCCD);
        fullnameEditText = findViewById(R.id.HovaTen);

        emailEditText = findViewById(R.id.EmalNV);
        stdEditText = findViewById(R.id.STD);
        maNvEditText = findViewById(R.id.MaNV);
        matKhauEditText = findViewById(R.id.MatKhau);
        resultTextView = findViewById(R.id.resultTextView);
        dangKiButton = findViewById(R.id.DangKiNV);
        db = FirebaseFirestore.getInstance();
        // Xử lý sự kiện khi người dùng nhấn nút Đăng Ký Nhân Viên
        dangKiButton.setOnClickListener(view -> addEmployeeToFirestore());
    }

    private void addEmployeeToFirestore() {
        // Lấy thông tin từ EditText
        String cccd = cccdEditText.getText().toString();
        String fullname = fullnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String std = stdEditText.getText().toString();
        String maNv = maNvEditText.getText().toString();
        String matKhau = matKhauEditText.getText().toString();
        if (cccd.isEmpty() || fullname.isEmpty() || email.isEmpty() || std.isEmpty() || maNv.isEmpty() || matKhau.isEmpty()) {
            resultTextView.setText("Vui lòng nhập đầy đủ thông tin");
            return;
        }
        // Tạo một đối tượng Map để lưu thông tin nhân viên
        Map<String, Object> NhanVien = new HashMap<>();
        NhanVien.put("CCCD", cccd);
        NhanVien.put("Ho va Ten", fullname);
        NhanVien.put("Email", email);
        NhanVien.put("STD", std);
        NhanVien.put("MaNV", maNv);
        NhanVien.put("MatKhau", matKhau);

        // Thêm dữ liệu vào Firestore

        db.collection("NhanVien")
                .add(NhanVien)
                .addOnSuccessListener(documentReference -> {
                    // Thêm dữ liệu thành công
                    String result = "Đã thêm nhân viên với CCCD: " + cccd;
                    resultTextView.setText(result);
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi thêm dữ liệu thất bại
                    String result = "Lỗi khi thêm nhân viên: " + e.getMessage();
                    Log.e("FirestoreError", result, e);
                    resultTextView.setText(result);
                });
    }
}
