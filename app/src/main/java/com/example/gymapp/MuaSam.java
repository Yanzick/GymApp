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

public class MuaSam extends AppCompatActivity {
    private RecyclerView recyclerViewSanPham;
    private SanPhamAdapter sanPhamAdapter;
    private FirebaseFirestore db;
    private Button DangKiSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_sam);
        recyclerViewSanPham = findViewById(R.id.rcvMuaSam);
        sanPhamAdapter = new SanPhamAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewSanPham.setLayoutManager(linearLayoutManager);
        recyclerViewSanPham.setAdapter(sanPhamAdapter);

        getDataFromFirestore();
    }

    private void getDataFromFirestore() {
        db = FirebaseFirestore.getInstance();
        List<SanPhamMain> productList = new ArrayList<>();
        db.collection("SanPham")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String imageUrl = document.getString("imageUrl");
                                String name = document.getString("name");
                                String price = document.getString("price");
                                productList.add(new SanPhamMain(imageUrl,name, price));
                                Log.d("SanPham", "Tên sản phẩm: " + name);
                            }
                            // Cập nhật RecyclerView sau khi lấy dữ liệu
                            sanPhamAdapter.setData(productList);
                        } else {
                            Log.e("MainActivity_SanPham", "Lỗi khi lấy dữ liệu: ", task.getException());
                        }
                    }
                });
    }

}
