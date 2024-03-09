package com.example.gymapp;

import static com.google.common.io.Files.getFileExtension;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ChonPT extends AppCompatActivity {

    private EditText CCCD, HoVaten, EmalNV, STD, MaNV, MatKhau;
    private Button DangKiNV;
    private FirebaseFirestore firestore;
    private ImageView anhNhanVien;
    private String hoTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_pt);

        HoVaten = findViewById(R.id.HoVaten2);
        EmalNV = findViewById(R.id.EmalNV2);
        STD = findViewById(R.id.STD2);
        DangKiNV = findViewById(R.id.DangKiNV2);
        anhNhanVien = findViewById(R.id.AnhNhanVien2);
        firestore = FirebaseFirestore.getInstance();


        // Lấy mã nhân viên từ Intent
        Intent intent = getIntent();
        String hoTen = intent.getStringExtra("hoTen");
        Log.d("MainActivity_PT", "Ten PT:" + hoTen);
        // Kiểm tra xem maNV có khác null trước khi thực hiện truy vấn Firestore
        if (hoTen != null) {
            Log.e("InfoActivity", "maNV: " + hoTen);
            // Truy vấn Firestore để lấy thông tin nhân viên
            firestore.collection("NhanVien")
                    .whereEqualTo("Ho va Ten", hoTen)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("InfoActivity", "hoVaten: " + document.getString("Ho va Ten"));
                                    Log.d("InfoActivity", "email: " + document.getString("Email"));
                                    Log.d("InfoActivity", "sdt: " + document.getString("STD"));

                                    HoVaten.setText(document.getString("Ho va Ten"));
                                    EmalNV.setText(document.getString("Email"));
                                    STD.setText(document.getString("STD"));
                                    if (document.contains("imageUrl")) {
                                        String imageUrl = document.getString("imageUrl");
                                        Picasso.get().load(imageUrl).into(anhNhanVien);
                                        anhNhanVien.setVisibility(View.VISIBLE);
                                    } else {
                                        // Nếu không có URL hình ảnh, ẩn ImageView
                                        anhNhanVien.setVisibility(View.GONE);
                                    }
                                }
                                Log.e("Success", "Success");
                            } else {
                                Log.e("InfoActivity", "Error getting documents: ", task.getException());
                                Toast.makeText(ChonPT.this, "Lỗi khi truy vấn dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(ChonPT.this, "Mã nhân viên không hợp lệ", Toast.LENGTH_SHORT).show();
        }

    }
}