package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Manager extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager);

        // Ánh xạ các thành phần giao diện
        mUsernameEditText = findViewById(R.id.MSQL);
        mPasswordEditText = findViewById(R.id.PassQL);
        mLoginButton = findViewById(R.id.LoginQL);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tên người dùng và mật khẩu từ EditText
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                // Đăng nhập bằng tài khoản đã đăng ký trên Firebase Authentication
                FirebaseAuth.getInstance().signInWithEmailAndPassword("admin@gym.com", "admin123_1")
                        .addOnCompleteListener(Manager.this, task -> {
                            if (task.isSuccessful()) {
                                // Đăng nhập thành công, kiểm tra quyền của người dùng trong Firestore
                                checkUserRole();
                            } else {
                                // Đăng nhập thất bại, thông báo cho người dùng
                                Toast.makeText(Manager.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    private void checkUserRole() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Lấy email của người dùng
            String userEmail = currentUser.getEmail();

            if (userEmail != null && userEmail.equals("admin@gym.com")) {
                // Người dùng có quyền admin, thực hiện các thao tác cần thiết
                Toast.makeText(Manager.this, "Đăng nhập thành công với quyền admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Manager.this, ManagerActivity.class);
                startActivity(intent);
            } else {
                // Người dùng không có quyền admin, thông báo cho người dùng
                Toast.makeText(Manager.this, "Tài khoản không có quyền admin", Toast.LENGTH_SHORT).show();
            }
        }
    }
}