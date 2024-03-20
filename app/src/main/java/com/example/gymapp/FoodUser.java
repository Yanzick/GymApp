package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FoodUser extends AppCompatActivity {

    private EditText nameEditText;
    private EditText loaiThucPham1,loaiThucPham2,loaiThucPham3;
    private EditText thucPham1,thucPham2,thucPham3,thucPham4,thucPham5,thucPham6,thucPham7,thucPham8,thucPham9;
    private EditText Ten;
    // Khai báo thêm các EditText cần thiết cho loại thực phẩm và thực phẩm khác

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_user);

        // Ánh xạ các EditText từ layout
        nameEditText = findViewById(R.id.Name_FoodUser);
        loaiThucPham1 = findViewById(R.id.LoaiThucPham1User);
        loaiThucPham2 = findViewById(R.id.LoaiThucPham2User);
        loaiThucPham3 = findViewById(R.id.LoaiThucPham3User);
        thucPham1 = findViewById(R.id.ThucPham1User);
        thucPham2 = findViewById(R.id.ThucPham2User);
        thucPham3 = findViewById(R.id.ThucPham3User);
        thucPham4 = findViewById(R.id.ThucPham4User);
        thucPham5 = findViewById(R.id.ThucPham5User);
        thucPham6 = findViewById(R.id.ThucPham6User);
        thucPham7 = findViewById(R.id.ThucPham7User);
        thucPham8 = findViewById(R.id.ThucPham8User);
        thucPham9 = findViewById(R.id.ThucPham9User);
        Ten = findViewById(R.id.Name_FoodUser);
        // Ánh xạ thêm các EditText khác theo cách tương tự

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String name = intent.getStringExtra("tenKH");
        getDataFromFirestore(name);
        // Load dữ liệu từ Firestore và hiển thị lên các EditText
        getDataFromFirestore(name);
    }

    private void getDataFromFirestore(String name) {
        db.collection("addFood").document(name)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Lấy dữ liệu từ tài liệu Firestore và hiển thị trong EditText tương ứng
                        Ten.setText(name);

                        // Tiếp tục truy vấn collection "Loai Thuc Pham"
                        db.collection("addFood").document(name).collection("Loai Thuc Pham")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            int count = 0;
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                // Lấy dữ liệu từ Firestore
                                                String loaiThucPham = document.getId();

                                                // Hiển thị dữ liệu lên EditText tương ứng
                                                switch (count) {
                                                    case 0:
                                                        loaiThucPham1.setText(loaiThucPham);
                                                        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham).collection("ThucPham")
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            int count = 0;
                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                // Lấy dữ liệu từ Firestore
                                                                                String ThucPham = document.getId();

                                                                                // Hiển thị dữ liệu lên EditText tương ứng
                                                                                switch (count) {
                                                                                    case 0:
                                                                                        thucPham1.setText(ThucPham);

                                                                                        break;
                                                                                    case 1:
                                                                                        thucPham2.setText(ThucPham);
                                                                                        break;
                                                                                    case 2:
                                                                                        thucPham3.setText(ThucPham);
                                                                                        break;
                                                                                    default:
                                                                                        // Xử lý nếu có nhiều hơn 3 loại thực phẩm
                                                                                        break;
                                                                                }
                                                                                count++;
                                                                            }
                                                                        } else {
                                                                            Toast.makeText(FoodUser.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                        break;
                                                    case 1:
                                                        loaiThucPham2.setText(loaiThucPham);
                                                        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham).collection("ThucPham")
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            int count = 0;
                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                // Lấy dữ liệu từ Firestore
                                                                                String ThucPham = document.getId();

                                                                                // Hiển thị dữ liệu lên EditText tương ứng
                                                                                switch (count) {
                                                                                    case 0:
                                                                                        thucPham4.setText(ThucPham);

                                                                                        break;
                                                                                    case 1:
                                                                                        thucPham5.setText(ThucPham);
                                                                                        break;
                                                                                    case 2:
                                                                                        thucPham6.setText(ThucPham);
                                                                                        break;
                                                                                    default:
                                                                                        // Xử lý nếu có nhiều hơn 3 loại thực phẩm
                                                                                        break;
                                                                                }
                                                                                count++;
                                                                            }
                                                                        } else {
                                                                            Toast.makeText(FoodUser.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                        break;
                                                    case 2:
                                                        loaiThucPham3.setText(loaiThucPham);
                                                        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham).collection("ThucPham")
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            int count = 0;
                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                // Lấy dữ liệu từ Firestore
                                                                                String ThucPham = document.getId();

                                                                                // Hiển thị dữ liệu lên EditText tương ứng
                                                                                switch (count) {
                                                                                    case 0:
                                                                                        thucPham7.setText(ThucPham);

                                                                                        break;
                                                                                    case 1:
                                                                                        thucPham8.setText(ThucPham);
                                                                                        break;
                                                                                    case 2:
                                                                                        thucPham9.setText(ThucPham);
                                                                                        break;
                                                                                    default:
                                                                                        // Xử lý nếu có nhiều hơn 3 loại thực phẩm
                                                                                        break;
                                                                                }
                                                                                count++;
                                                                            }
                                                                        } else {
                                                                            Toast.makeText(FoodUser.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                        break;
                                                    default:
                                                        // Xử lý nếu có nhiều hơn 3 loại thực phẩm
                                                        break;
                                                }
                                                count++;
                                            }
                                        } else {
                                            Toast.makeText(FoodUser.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        // Xử lý khi tài liệu không tồn tại
                        Ten.setText("Document does not exist");
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi truy vấn thất bại
                    Ten.setText("Error getting document: " + e.getMessage());
                });
    }

}
