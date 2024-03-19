package com.example.gymapp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import com.example.gymapp.FoodItem;


public class Food extends AppCompatActivity {

    private static final String TAG = "FoodActivity";
    private FirebaseFirestore db;
    private Spinner spinner1, spinner2, spinner3, sipnner4, spinner5, spinner6, spinner7, spinner8, spinner9, spinner10, spinner11, spinner12;
    private List<String> loaiThucPhamList, loaiThucPhamList1, loaiThucPhamList2;
    private TextView calo1, calo2, calo3, calo4, calo5, calo6, calo7, calo8, calo9;
    private Button Dangki;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        name = intent.getStringExtra("Ho va Ten");
        // Ánh xạ Spinner
        spinner1 = findViewById(R.id.LoaiThucPham1);
        spinner2 = findViewById(R.id.ThucPham1Sang);
        spinner3 = findViewById(R.id.ThucPham2Sang);
        sipnner4 = findViewById(R.id.ThucPham3Sang);
        spinner5 = findViewById(R.id.LoaiThucPham2);
        spinner6 = findViewById(R.id.ThucPham1Trua);
        spinner7 = findViewById(R.id.ThucPham2Trua);
        spinner8 = findViewById(R.id.ThucPham3Trua);
        spinner9 = findViewById(R.id.LoaiThucPham3);
        spinner10 = findViewById(R.id.ThucPham1Toi);
        spinner11 =findViewById(R.id.ThucPham2Toi);
        spinner12 = findViewById(R.id.ThucPham3Toi);
        Dangki = findViewById(R.id.ChoseFood);
        // Khởi tạo danh sách loại thực phẩm
        loaiThucPhamList = new ArrayList<>();
        loaiThucPhamList1 = new ArrayList<>();
        loaiThucPhamList2 = new ArrayList<>();
        // Truy vấn dữ liệu từ Firestore
        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Lấy loại thực phẩm từ mỗi tài liệu và thêm vào danh sách
                                String loaiThucPham = document.getId();
                                loaiThucPhamList.add(loaiThucPham);
                                loaiThucPhamList1.add(loaiThucPham);
                                loaiThucPhamList2.add(loaiThucPham);
                            }
                            // Sau khi lấy dữ liệu xong, cập nhật Spinner
                            updateSpinners();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        // Sự kiện lắng nghe khi chọn loại thực phẩm trong Spinner 1
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy loại thực phẩm đã chọn
                String selectedType1 = loaiThucPhamList.get(position);
                // Cập nhật Spinner 2 và Spinner 3 dựa trên loại thực phẩm đã chọn
                updateFoodSpinners(selectedType1, spinner2, spinner3, sipnner4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Để trống vì không xử lý sự kiện này
            }
        });

        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy loại thực phẩm đã chọn
                String selectedType2 = loaiThucPhamList1.get(position);
                // Cập nhật Spinner 6 và Spinner 7 dựa trên loại thực phẩm đã chọn
                updateFoodSpinners(selectedType2, spinner6, spinner7, spinner8);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Để trống vì không xử lý sự kiện này
            }
        });

        spinner9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy loại thực phẩm đã chọn
                String selectedType3 = loaiThucPhamList2.get(position);
                // Cập nhật Spinner 10 và Spinner 11 dựa trên loại thực phẩm đã chọn
                updateFoodSpinners(selectedType3, spinner10, spinner11, spinner12);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Để trống vì không xử lý sự kiện này
            }
        });
        Dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy loại thực phẩm và thực phẩm đã chọn từ các Spinner
                String loaiThucPham1 = (String) spinner1.getSelectedItem();
                String loaiThucPham2 = (String) spinner5.getSelectedItem();
                String loaiThucPham3 = (String) spinner9.getSelectedItem();

                String thucPham1 = (String) spinner2.getSelectedItem();
                String thucPham2 = (String) spinner3.getSelectedItem();
                String thucPham3 = (String) sipnner4.getSelectedItem();

                String thucPham4 = (String) spinner6.getSelectedItem();
                String thucPham5 = (String) spinner7.getSelectedItem();
                String thucPham6 = (String) spinner8.getSelectedItem();

                String thucPham7 = (String) spinner10.getSelectedItem();
                String thucPham8 = (String) spinner11.getSelectedItem();
                String thucPham9 = (String) spinner12.getSelectedItem();

                // Thêm dữ liệu vào Firebase Firestore
                addToFirestore(loaiThucPham1, loaiThucPham2, loaiThucPham3, thucPham1, thucPham2, thucPham3, thucPham4, thucPham5, thucPham6,thucPham7, thucPham8, thucPham9);
            }
        });
    }

    // Phương thức cập nhật Spinner
    private void updateSpinners() {
        // Tạo adapter từ danh sách loại thực phẩm
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loaiThucPhamList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Gán adapter cho các Spinner
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        sipnner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);
        spinner6.setAdapter(adapter);
        spinner7.setAdapter(adapter);
        spinner8.setAdapter(adapter);
        spinner9.setAdapter(adapter);
        spinner10.setAdapter(adapter);
        spinner11.setAdapter(adapter);
        spinner12.setAdapter(adapter);
    }

    // Phương thức cập nhật Spinner thực phẩm dựa trên loại thực phẩm đã chọn
    private void updateFoodSpinners(String selectedType, Spinner spinner1, Spinner spinner2, Spinner spinner3) {
        // Tạo adapter cho Food1Adapter và cập nhật cho các Spinner
        Food1Adapter food1Adapter = new Food1Adapter(this, android.R.layout.simple_spinner_item, selectedType);
        food1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(food1Adapter);
        spinner2.setAdapter(food1Adapter);
        spinner3.setAdapter(food1Adapter);
    }
    // Phương thức để thêm dữ liệu vào Firestore
    private void addToFirestore(String loaiThucPham1, String loaiThucPham2, String loaiThucPham3, String thucPham1, String thucPham2, String thucPham3, String thucPham4, String thucPham5, String thucPham6,String thucPham7,String thucPham8,String thucPham9) {
        // Thêm dữ liệu vào Firebase Firestore
        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham1).collection("ThucPham").document(thucPham1).set(new FoodItem(thucPham1))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Thêm thất bại
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham1).collection("ThucPham").document(thucPham2).set(new FoodItem(thucPham2))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Thêm thất bại
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham1).collection("ThucPham").document(thucPham3).set(new FoodItem(thucPham3))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Thêm thất bại
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham2).collection("ThucPham").document(thucPham4).set(new FoodItem(thucPham4))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Thêm thất bại
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham2).collection("ThucPham").document(thucPham5).set(new FoodItem(thucPham5))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Thêm thất bại
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham2).collection("ThucPham").document(thucPham6).set(new FoodItem(thucPham6))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Thêm thất bại
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham3).collection("ThucPham").document(thucPham7).set(new FoodItem(thucPham7))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Thêm thất bại
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham3).collection("ThucPham").document(thucPham8).set(new FoodItem(thucPham8))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Thêm thất bại
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        db.collection("addFood").document(name).collection("Loai Thuc Pham").document(loaiThucPham3).collection("ThucPham").document(thucPham9).set(new FoodItem(thucPham9))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Thêm thành công
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Thêm thất bại
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}

