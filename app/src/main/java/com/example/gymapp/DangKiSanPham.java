package com.example.gymapp;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DangKiSanPham extends AppCompatActivity {

    private EditText IDSanPham, TenSanPham, GiaSanPham;
    private Button DangKiNV, chonAnhButton;
    private ImageView anhSanPham;
    private FirebaseFirestore firestore;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki_san_pham);

        IDSanPham = findViewById(R.id.IDSanPham);
        TenSanPham = findViewById(R.id.TenSanPham);
        GiaSanPham = findViewById(R.id.GiaSanPham);
        chonAnhButton = findViewById(R.id.ChonAnhSanPham);
        anhSanPham = findViewById(R.id.AnhSanPham);
        DangKiNV = findViewById(R.id.DangKiSanPham);
        firestore = FirebaseFirestore.getInstance();

        chonAnhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở activity để chọn hình ảnh từ thư viện
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });

        DangKiNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProductToFirestore();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            anhSanPham.setImageURI(selectedImageUri);
            anhSanPham.setVisibility(View.VISIBLE);
        }
    }

    private void uploadProductToFirestore() {
        String idSanPham = IDSanPham.getText().toString().trim();
        String tenSanPham = TenSanPham.getText().toString().trim();
        String giaSanPham = GiaSanPham.getText().toString().trim();

        if (idSanPham.isEmpty() || tenSanPham.isEmpty() || giaSanPham.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> sanPhamData = new HashMap<>();
        sanPhamData.put("ID", idSanPham);
        sanPhamData.put("name", tenSanPham);
        sanPhamData.put("price", giaSanPham);
        if (selectedImageUri != null) {
            sanPhamData.put("imageUrl", selectedImageUri.toString());
        }
        firestore.collection("SanPham").document(idSanPham)
                .set(sanPhamData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DangKiSanPham.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        if (selectedImageUri != null) {
                            uploadImageToStorage(idSanPham);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DangKiSanPham.this, "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                        Log.e("DangKiSanPham", "Lỗi khi thêm sản phẩm", e);
                    }
                });
    }

    private void uploadImageToStorage(String idSanPham) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/" + idSanPham);
        storageReference.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Lấy URL của hình ảnh từ Firebase Storage
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Lấy URL thành công, lưu vào Firestore
                                String imageUrl = uri.toString();
                                Log.d("DangKiSanPham", "Image URL: " + imageUrl);

                                // Tạo một HashMap chỉ chứa URL hình ảnh
                                Map<String, Object> imageData = new HashMap<>();
                                imageData.put("imageUrl", imageUrl);

                                // Cập nhật dữ liệu hình ảnh vào Firestore
                                firestore.collection("SanPham").document(idSanPham)
                                        .update(imageData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(DangKiSanPham.this, "URL hình ảnh đã được thêm vào Firestore", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(DangKiSanPham.this, "Lỗi khi cập nhật URL hình ảnh vào Firestore", Toast.LENGTH_SHORT).show();
                                                Log.e("DangKiSanPham", "Lỗi khi cập nhật URL hình ảnh vào Firestore", e);
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DangKiSanPham.this, "Lỗi khi tải hình ảnh lên", Toast.LENGTH_SHORT).show();
                        Log.e("DangKiSanPham", "Lỗi khi tải hình ảnh lên", e);
                    }
                });
    }

}
