package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerActivity extends AppCompatActivity {
    private Button ResMem, SanPham, QLNV, QLKH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager2);
        ResMem = findViewById(R.id.RegisMem);
        SanPham = findViewById(R.id.SanPham);
        QLNV = findViewById(R.id.ButtonNV);
        QLKH = findViewById(R.id.ButtonKH);
        ResMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this,ResMem.class);
                startActivity(intent);
            }
        });
        SanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this,SanPham.class);
                startActivity(intent);
            }
        });
        QLNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this,QLNV.class);
                startActivity(intent);
            }
        });
        QLKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this,QLKHMain.class);
                startActivity(intent);
            }
        });
    }
}