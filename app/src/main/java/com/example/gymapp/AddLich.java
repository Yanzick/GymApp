package com.example.gymapp;

import static com.example.gymapp.DayAdapter.selectedDays;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.DayAdapter;
import com.example.gymapp.MonthAdapter;
import com.example.gymapp.R;
import com.example.gymapp.YearAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddLich extends AppCompatActivity {

    private Spinner spinnerMonth, spinnerYear;
    private Button btnRegisterSchedule;
    private RecyclerView recyclerViewDays;
    private DayAdapter dayAdapter;
    private List<Integer> daysOfMonth;
    private String maNV;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lich);

        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
        MonthAdapter monthAdapter = new MonthAdapter(this, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(monthAdapter);

        // Khởi tạo adapter cho Spinner năm
        YearAdapter yearAdapter = new YearAdapter(this, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        btnRegisterSchedule = findViewById(R.id.btnRegisterSchedule);
        recyclerViewDays = findViewById(R.id.recyclerViewDays);

        // Khởi tạo RecyclerView
        recyclerViewDays.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDays.setLayoutManager(new GridLayoutManager(this, 3));
        dayAdapter = new DayAdapter();
        recyclerViewDays.setAdapter(dayAdapter);
        // Khởi tạo danh sách các ngày mẫu
        daysOfMonth = new ArrayList<>();
        // Khởi tạo số ngày mặc định cho tháng đầu tiên
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int daysInMonth = getDaysInMonth(1, currentYear);
        for (int i = 1; i <= daysInMonth; i++) {
            daysOfMonth.add(i);
        }

        // Gán dữ liệu vào RecyclerView
        dayAdapter.setDays(daysOfMonth);

        // Xử lý sự kiện khi thay đổi tháng
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedMonth = position + 1; // Vị trí bắt đầu từ 0, cần cộng thêm 1
                int selectedYear = Integer.parseInt(spinnerYear.getSelectedItem().toString());
                int daysInSelectedMonth = getDaysInMonth(selectedMonth, selectedYear);
                updateRecyclerView(daysInSelectedMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không cần xử lý khi không chọn gì cả
            }
        });

        btnRegisterSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin tháng và năm từ Spinner
                String selectedMonth = spinnerMonth.getSelectedItem().toString();
                String selectedYear = spinnerYear.getSelectedItem().toString();
                Intent intent = getIntent();
                maNV = intent.getStringExtra("maNV");
                // Lặp qua tất cả các mục trong RecyclerView để lấy ngày đã tick
                for (int i = 0; i < dayAdapter.getItemCount(); i++) {
                    // Kiểm tra xem ngày thứ i đã được tick chưa
                    if (dayAdapter.isDaySelected(i)) {
                        // Nếu đã được tick, lấy số ngày
                        int day = daysOfMonth.get(i);

                        // Tạo một đối tượng chứa thông tin ngày, tháng và năm
                        Map<String, Object> scheduleData = new HashMap<>();

                        scheduleData.put("maNV", maNV);
                        // Thêm đối tượng này vào Firestore
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        String documentPath = String.format("LichPT/%s/years/%s/months/%s/days/%02d", maNV, selectedYear, selectedMonth, day);

                        db.document(documentPath).set(scheduleData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Xử lý thành công
                                        Toast.makeText(AddLich.this, "Đã thêm lịch tập vào Firestore", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Xử lý khi thất bại
                                        Toast.makeText(AddLich.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
        });

    }

    // Phương thức để lấy số ngày trong tháng
    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case 2: // Tháng 2
                if (isLeapYear(year)) {
                    return 29;
                } else {
                    return 28;
                }
            case 4: // Tháng 4
            case 6: // Tháng 6
            case 9: // Tháng 9
            case 11: // Tháng 11
                return 30;
            default: // Các tháng còn lại
                return 31;
        }
    }

    // Phương thức để kiểm tra năm nhuận
    private boolean isLeapYear(int year) {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.isLeapYear(year);
    }

    // Phương thức để cập nhật RecyclerView khi thay đổi tháng
    private void updateRecyclerView(int daysInMonth) {
        daysOfMonth.clear(); // Xóa danh sách ngày cũ
        for (int i = 1; i <= daysInMonth; i++) {
            daysOfMonth.add(i); // Thêm ngày mới vào danh sách
        }
        dayAdapter.setDays(daysOfMonth); // Cập nhật dữ liệu cho RecyclerView
    }


    private void restoreDaySelectionFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        for (int i = 0; i < dayAdapter.getItemCount(); i++) {
            boolean isDaySelected = sharedPreferences.getBoolean("day_" + i, false);
            dayAdapter.setSelected(i, isDaySelected);
        }
    }

    // Trong phương thức DayAdapter, thêm phương thức này để cập nhật trạng thái của ngày đã được chọn
    public void setSelected(int position, boolean isSelected) {
        selectedDays.set(position, isSelected);
        // Lưu trạng thái của ngày vào SharedPreferences
        saveDaySelectionToSharedPreferences(position, isSelected);
    }

    // Phương thức để lưu trạng thái của ngày vào SharedPreferences
    private void saveDaySelectionToSharedPreferences(int position, boolean isSelected) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("day_" + position, isSelected);
        editor.apply();
    }
}