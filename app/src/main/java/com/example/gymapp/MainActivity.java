package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText Email, Pass;
    private Button Login;
    private TextView Register;
    private FirebaseAuth mAuth;
    private ImageView Manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        mAuth=FirebaseAuth.getInstance();

        Email = findViewById(R.id.Email);
        Login = findViewById(R.id.buttonLogin);
        Register = findViewById(R.id.register);
        Pass = findViewById(R.id.Pass);
        Manager = findViewById(R.id.manager);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        Manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login2.class);
                startActivity(intent);
            }
        });
    }

    private void register() {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }

    private void login() {
        String emailedit, passedit;
        emailedit = Email.getText().toString();
        passedit = Pass.getText().toString();

        if(TextUtils.isEmpty(emailedit)){
            Toast.makeText(this, "Vui lòng nhập địa chỉ email!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passedit)){
            Toast.makeText(this, "Vui lòng nhập mật khẩu!",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(emailedit,passedit).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập không thành công",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}