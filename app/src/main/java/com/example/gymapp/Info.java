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

public class Info extends AppCompatActivity {

    private EditText CCCD, HoVaten, EmalNV, STD, MaNV, MatKhau;
    private Button DangKiNV;
    private FirebaseFirestore firestore;
    private Button chonAnhButton;
    private ImageView anhNhanVien;
    private String maNV;
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
        chonAnhButton = findViewById(R.id.ChonAnhButton);
        anhNhanVien = findViewById(R.id.AnhNhanVien);
        firestore = FirebaseFirestore.getInstance();


        // Lấy mã nhân viên từ Intent
        Intent intent = getIntent();
        maNV = intent.getStringExtra("maNV");

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
                                    if (document.contains("imageUrl")) {
                                        String imageUrl = document.getString("imageUrl");
                                        Picasso.get().load(imageUrl).into(anhNhanVien);
                                        anhNhanVien.setVisibility(View.VISIBLE);
                                    } else {
                                        // Nếu không có URL hình ảnh, ẩn ImageView
                                        anhNhanVien.setVisibility(View.GONE);
                                    }
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
        chonAnhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở activity để chọn hình ảnh từ thư viện
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            anhNhanVien.setImageURI(imageUri);
            anhNhanVien.setVisibility(View.VISIBLE);
            uploadFile(imageUri);
        }
    }
    private void uploadFile(Uri imageUri) {
        if (imageUri != null) {
            // Lấy đường dẫn đến thư mục trên Firebase Storage mà bạn muốn lưu trữ hình ảnh
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("your_storage_path_here");

            // Tạo một đối tượng FileReference mới trong thư mục images với tên duy nhất
            StorageReference fileReference = storageReference.child("images/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));

            // Tiến hành tải lên Firebase Storage
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Lấy URL của ảnh từ Firebase Storage
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Lấy URL thành công, lưu vào Firestore
                                    String imageUrl = uri.toString();
                                    Log.d("InfoActivity", "Image URL: " + imageUrl);
                                    Map<String, Object> newData = new HashMap<>();
                                    newData.put("imageUrl", imageUrl);
                                    // Lưu imageUrl vào Firestore theo maNV hoặc một trường khác tùy vào yêu cầu của bạn
                                    firestore.collection("NhanVien").document(maNV)
                                            .update(newData)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Xử lý khi thêm thành công
                                                        Toast.makeText(Info.this, "Dữ liệu và URL hình ảnh đã được thêm mới thành công", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        // Xử lý khi có lỗi xảy ra trong quá trình thêm mới
                                                        Toast.makeText(Info.this, "Lỗi khi thêm dữ liệu và URL hình ảnh mới", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Info.this, "Lỗi khi tải ảnh lên Firebase Storage", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
