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

public class ChitietKhachHang extends AppCompatActivity {

    private EditText CCCD, HoVaten, EmalNV, STD, MaNV, MatKhau;
    private Button DangKiNV;
    private FirebaseFirestore firestore;
    private Button chonAnhButton;
    private ImageView anhNhanVien;
    private String maNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_khach_hang);


        HoVaten = findViewById(R.id.Namehome1);
        EmalNV = findViewById(R.id.EmailHome1);
        STD = findViewById(R.id.STDHome1);
        firestore = FirebaseFirestore.getInstance();


        // Lấy mã nhân viên từ Intent
        Intent intent = getIntent();
        maNV = intent.getStringExtra("Email");

        // Kiểm tra xem maNV có khác null trước khi thực hiện truy vấn Firestore
        if (maNV != null) {
            Log.e("InfoActivity", "maNV: " + maNV);
            // Truy vấn Firestore để lấy thông tin nhân viên
            firestore.collection("KhachHang")
                    .whereEqualTo("Email", maNV)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d("InfoActivity", "hoVaten: " + document.getString("TenKH"));
                                    Log.d("InfoActivity", "email: " + document.getString("Email"));
                                    Log.d("InfoActivity", "sdt: " + document.getString("SDT"));

                                    HoVaten.setText(document.getString("Ho va Ten"));
                                    EmalNV.setText(document.getString("Email"));
                                    STD.setText(document.getString("STD"));

                                }
                                Log.e("Success","Success");
                            } else {
                                Log.e("InfoActivity", "Error getting documents: ", task.getException());
                                Toast.makeText(ChitietKhachHang.this, "Lỗi khi truy vấn dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else {
            Toast.makeText(ChitietKhachHang.this, "Mã nhân viên không hợp lệ", Toast.LENGTH_SHORT).show();
        }

    }
}
