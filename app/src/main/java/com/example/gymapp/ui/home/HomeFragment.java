package com.example.gymapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymapp.BMIUser;
import com.example.gymapp.FoodUser;
import com.example.gymapp.LichPT;
import com.example.gymapp.MainActivity_PT;
import com.example.gymapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        // Đây là nơi bạn có thể truy cập các phần tử trong layout sử dụng binding.
        ImageView button1 = binding.Btn1;
        ImageView button2 = binding.Btn2;
        ImageView button3 = binding.Btn3;
        ImageView button4 = binding.Btn4;
        Button button5 = binding.Btn5;
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), MainActivity_PT.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), LichPT.class);
                startActivity(intent);
            }
        });

        // Thêm sự kiện click hoặc thực hiện các tác vụ khác tại đây.

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nhận dữ liệu từ MainActivity
                String tenKH = getActivity().getIntent().getStringExtra("tenKH");
                Log.d("SanPham", "Tên sản phẩm: " + tenKH);
                // Xử lý dữ liệu và thực hiện các hành động mong muốn
                if (tenKH != null) {
                    // Ví dụ: Hiển thị tên người dùng trong một Toast
                    Toast.makeText(getContext(), "Xin chào, " + tenKH, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), BMIUser.class);
                    intent.putExtra("tenKH", tenKH);
                    startActivity(intent);

                } else {
                    Toast.makeText(getContext(), "Không có thông tin tên người dùng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nhận dữ liệu từ MainActivity
                String tenKH = getActivity().getIntent().getStringExtra("tenKH");
                Log.d("SanPham", "Tên sản phẩm: " + tenKH);
                // Xử lý dữ liệu và thực hiện các hành động mong muốn
                if (tenKH != null) {
                    // Ví dụ: Hiển thị tên người dùng trong một Toast

                    Intent intent = new Intent(getContext(), FoodUser.class);
                    intent.putExtra("tenKH", tenKH);
                    startActivity(intent);

                } else {
                    Toast.makeText(getContext(), "Không có thông tin tên người dùng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}