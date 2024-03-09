package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_PT extends AppCompatActivity {
    private RecyclerView rcv;
    private PTAdapter PTAd;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pt);

        rcv = findViewById(R.id.rcv);
        PTAd = new PTAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);
        PTAd.setOnItemClickListener(new PTAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String name) {
                // Xử lý sự kiện nhấp vào mục trong hoạt động
                Log.d("MainActivity_PT", "Ten:" + name);

                // Chuyển họ và tên sang ChonPT
                Intent intent = new Intent(MainActivity_PT.this, ChonPT.class);
                intent.putExtra("hoTen",name);
                startActivity(intent);
            }
        });
        // Lấy dữ liệu từ Firestore và cập nhật RecyclerView
        getDataFromFirestore();

        rcv.setAdapter(PTAd);
    }

    private void getDataFromFirestore() {
        db = FirebaseFirestore.getInstance();
        List<PT> list = new ArrayList<>();
        db.collection("NhanVien")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String imageUrl = document.getString("imageUrl");
                                String name = document.getString("Ho va Ten");
                                list.add(new PT(imageUrl, name));
                                Log.d("InfoActivity", "hoVaten: " + document.getString("Ho va Ten"));
                            }
                            Log.d("InfoActivity", "Số lượng dữ liệu trong list: " + list.size());
                            PTAd.setData(list);
                        } else {
                            Log.e("MainActivity_PT", "Lỗi khi lấy dữ liệu: ", task.getException());
                        }
                    }
                });
    }

    // Hàm để cập nhật số lượng trong Firestore
    private void updateItemCount() {
        db.collection("NhanVien")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int itemCount = task.getResult().size();
                            // Lưu trữ số lượng trong Firestore hoặc sử dụng nó theo ý muốn của bạn
                            // Ở đây, tôi giả sử bạn đã thêm một trường "itemCount" vào tài liệu
                            updateFirestoreItemCount(itemCount);
                        } else {
                            Log.e("MainActivity_PT", "Lỗi khi lấy số lượng: ", task.getException());
                        }
                    }
                });
    }

    // Hàm để cập nhật số lượng trong Firestore
    private void updateFirestoreItemCount(int count) {
        Map<String, Object> itemCountMap = new HashMap<>();
        itemCountMap.put("itemCount", count);
        db.collection("Metadata")
                .document("ItemCountDocument")
                .set(itemCountMap)
                .addOnSuccessListener(aVoid -> Log.d("MainActivity_PT", "Số lượng đã được cập nhật thành công"))
                .addOnFailureListener(e -> Log.e("MainActivity_PT", "Lỗi khi cập nhật số lượng: ", e));
    }

}