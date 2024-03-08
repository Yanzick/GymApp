package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText Email, Pass;
    private Button Register;
    private TextView Login;
    private EditText RePassR; // Thêm EditText cho xác nhận mật khẩu
    private TextInputLayout passwordLayout; // Thêm TextInputLayout cho mật khẩu
    private TextInputLayout confirmPasswordLayout; // Thêm TextInputLayout cho xác nhận mật khẩu
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.EmailR);
        Login = findViewById(R.id.loginR);
        Register = findViewById(R.id.registerR);
        Pass = findViewById(R.id.PassR);
        RePassR = findViewById(R.id.RePassR); // Khởi tạo EditText cho xác nhận mật khẩu
        passwordLayout = findViewById(R.id.PassR1); // Khởi tạo TextInputLayout cho mật khẩu
        confirmPasswordLayout = findViewById(R.id.RePassR1); // Khởi tạo TextInputLayout cho xác nhận mật khẩu

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPasswordComplexity(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        RePassR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPasswordMatch();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void register() {
        String emailedit, passedit;
        emailedit = Email.getText().toString();
        passedit = Pass.getText().toString();
        String confirmPass = RePassR.getText().toString();

        if (TextUtils.isEmpty(emailedit)) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passedit)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passedit.equals(confirmPass)) {
            Pass.setError(null); // Xóa thông báo lỗi của EditText trước đó (nếu có)
            passwordLayout.setError("Mật khẩu không khớp"); // Thông báo lỗi nếu mật khẩu không khớp
            return;
        } else {
            passwordLayout.setError(null); // Xóa thông báo lỗi nếu mật khẩu khớp
        }

        mAuth.createUserWithEmailAndPassword(emailedit, passedit).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkPasswordComplexity(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (password.length() < 6) {
            passwordLayout.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return;
        }
        if (!matcher.matches()) {
            passwordLayout.setError("Mật khẩu phải có ít nhất một chữ hoa, một chữ số và một kí tự đặc biệt");
        } else {
            passwordLayout.setError(null);
        }
    }

    private void checkPasswordMatch() {
        String pass = Pass.getText().toString();
        String confirmPass = RePassR.getText().toString();

        if (!pass.equals(confirmPass)) {
            confirmPasswordLayout.setError("Mật khẩu không khớp");
        } else {
            confirmPasswordLayout.setError(null);
        }
    }
}
