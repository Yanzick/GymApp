package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemberActivity extends AppCompatActivity {

    private String maNv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        // Nhận mã nhân viên từ Intent
        Intent intent = getIntent();
        if (intent.hasExtra("MaNV")) {
            maNv = intent.getStringExtra("MaNV");
        }

        // Thêm mã nhân viên vào Intent mới để chuyển sang Info
        Button nextButton = findViewById(R.id.infoMem);
        Button Lich = findViewById(R.id.BtnLichPT);
        Button LichDaDK = findViewById(R.id.LichDaDK);
        Button BMI = findViewById(R.id.BMI);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoIntent = new Intent(MemberActivity.this, Info.class);
                infoIntent.putExtra("maNV", maNv);  // Sửa chữ hoa thành chữ thường
                startActivity(infoIntent);
            }
        });
        Lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoIntent = new Intent(MemberActivity.this, AddLich.class);
                infoIntent.putExtra("maNV", maNv);  // Sửa chữ hoa thành chữ thường
                startActivity(infoIntent);
            }
        });
        LichDaDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoIntent = new Intent(MemberActivity.this, LichPTDaDK.class);
                infoIntent.putExtra("maNV", maNv);  // Sửa chữ hoa thành chữ thường
                startActivity(infoIntent);
            }
        });
        BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoIntent = new Intent(MemberActivity.this, BMIMain.class);
                infoIntent.putExtra("maNV", maNv);  // Sửa chữ hoa thành chữ thường
                startActivity(infoIntent);
            }
        });
    }
}

