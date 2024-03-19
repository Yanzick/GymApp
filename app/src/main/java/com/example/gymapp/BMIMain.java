package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BMIMain extends AppCompatActivity {
    private RecyclerView recyclerViewSanPham;
    private BMIAdapter BMIAdapter;
    private FirebaseFirestore db;
    private String maNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        recyclerViewSanPham = findViewById(R.id.rcvBMI);
        BMIAdapter = new BMIAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewSanPham.setLayoutManager(linearLayoutManager);
        recyclerViewSanPham.setAdapter(BMIAdapter);
        Intent intent = getIntent();
        maNV = intent.getStringExtra("maNV");
        BMIAdapter.setOnItemClickListener(new BMIAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String name) {
                Log.d("BMI", "Ten:" + name);

                // Chuyển họ và tên sang ChonPT
                Intent intent = new Intent(BMIMain.this, Chose.class);
                intent.putExtra("Ho va Ten",name);
                startActivity(intent);
            }
        });
        // Lấy dữ liệu từ Firestore và cập nhật RecyclerView
        getDataFromFirestore();
    }
    private void getDataFromFirestore() {
        db = FirebaseFirestore.getInstance();
        List<BMI> productList = new ArrayList<>();
        db.collection("NhanVien").document(maNV).collection("KhachHang")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String imageUrl = document.getString("imageUrl");
                                String name = document.getString("Ho va Ten");
                                productList.add(new BMI(imageUrl,name));
                                Log.d("SanPham", "Tên sản phẩm: " + name);
                            }
                            // Cập nhật RecyclerView sau khi lấy dữ liệu
                            BMIAdapter.setData(productList);
                        } else {
                            Log.e("MainActivity_SanPham", "Lỗi khi lấy dữ liệu: ", task.getException());
                        }
                    }
                });
    }

}
