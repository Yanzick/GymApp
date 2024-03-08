package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Info extends AppCompatActivity {

    private EditText CCCD, HoVaten, EmalNV, STD, MaNV, MatKhau;
    private Button DangKiNV;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        CCCD = findViewById(R.id.CCCD1);
        HoVaten = findViewById(R.id.HoVaten1);
        EmalNV = findViewById(R.id.EmalNV1);
        STD = findViewById(R.id.STD1);
        MaNV = findViewById(R.id.MaNV1);
        DangKiNV = findViewById(R.id.DangKiNV1);

        firestore = FirebaseFirestore.getInstance();

        // Lấy mã nhân viên từ Intent
        Intent intent = getIntent();
        String maNV = intent.getStringExtra("maNV");

        // Kiểm tra xem maNV có khác null trước khi thực hiện truy vấn Firestore
        if (maNV != null) {
            Log.e("InfoActivity", "maNV: " + maNV);
            // Truy vấn Firestore để lấy thông tin nhân viên
            firestore.collection("NhanVien")
                    .whereEqualTo("MaNV", maNV)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("InfoActivity", "cccd: " + document.getString("CCCD"));
                                    Log.d("InfoActivity", "hoVaten: " + document.getString("Ho va Ten"));
                                    Log.d("InfoActivity", "email: " + document.getString("Email"));
                                    Log.d("InfoActivity", "sdt: " + document.getString("STD"));
                                    Log.d("InfoActivity", "maNV: " + document.getString("MaNV"));

                                    CCCD.setText(document.getString("CCCD"));
                                    HoVaten.setText(document.getString("Ho va Ten"));
                                    EmalNV.setText(document.getString("Email"));
                                    STD.setText(document.getString("STD"));
                                    MaNV.setText(document.getString("MaNV"));
                                }
                                Log.e("Success","Success");
                            } else {
                                Log.e("InfoActivity", "Error getting documents: ", task.getException());
                                Toast.makeText(Info.this, "Lỗi khi truy vấn dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(Info.this, "Mã nhân viên không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}
