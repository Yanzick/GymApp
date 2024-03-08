package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Login2 extends AppCompatActivity {
    private Button Member;
    private Button Admin;
    private TextView Rec;
    private ImageView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        Member = findViewById(R.id.ButtonMem);
        Admin = findViewById(R.id.ButtonAmind);
        Rec = findViewById(R.id.Rec);
        Back = findViewById(R.id.BackLog);
        Member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2.this,Member.class);
                startActivity(intent);
            }
        });
        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2.this,Manager.class);
                startActivity(intent);
            }
        });
        Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2.this,recruitment.class);
                startActivity(intent);
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}