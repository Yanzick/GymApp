package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Member extends AppCompatActivity {

    private EditText mMSNVEditText;
    private EditText mPasswordEditText;
    private Button LoginNV;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member);

        // Ánh xạ các thành phần từ layout
        mMSNVEditText = findViewById(R.id.MSNV);
        mPasswordEditText = findViewById(R.id.PassNV);
        LoginNV = findViewById(R.id.LoginNV);
        db = FirebaseFirestore.getInstance();
        LoginNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String maNv = mMSNVEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        // Kiểm tra xem MSNV và mật khẩu có hợp lệ hay không
        if (maNv.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Truy vấn Firestore để kiểm tra MSNV và mật khẩu
        db.collection("NhanVien")
                .whereEqualTo("MaNV", maNv)  // Thay đổi thành CCCD
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            // Nếu CCCD tồn tại, kiểm tra mật khẩu
                            String storedPassword = document.getString("MatKhau");
                            if (password.equals(storedPassword)) {
                                // Đăng nhập thành công, thực hiện các thao tác cần thiết
                                Toast.makeText(Member.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Member.this, MemberActivity.class);
                                startActivity(intent);

                                return;  // Kết thúc vòng lặp khi tìm thấy CCCD hợp lệ
                            } else {
                                // Mật khẩu không đúng
                                Toast.makeText(Member.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // CCCD không tồn tại
                        Toast.makeText(Member.this, "Mã nhân viên không tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        // Xử lý khi truy vấn Firestore thất bại
                        Toast.makeText(Member.this, "Lỗi khi truy vấn dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}