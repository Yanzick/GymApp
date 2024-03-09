package com.example.gymapp.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymapp.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String userEmail = bundle.getString("userEmail");
            String tenKH = bundle.getString("tenKH");
            String sdtKH = bundle.getString("sdtKH");
            Log.d("Email","EmailF: " + userEmail);
            Log.d("Ten","TenF: " + tenKH);
            Log.d("STD","STDF: " + sdtKH);
            // Sử dụng dữ liệu nhận được ở đây
            EditText emailEditText = binding.EmailHome;
            emailEditText.setText(userEmail);

            EditText nameEditText = binding.Namehome;
            nameEditText.setText(tenKH);

            EditText stdEditText = binding.STDHome;
            stdEditText.setText(sdtKH);
        }

        // Thêm sự kiện click hoặc thực hiện các tác vụ khác tại đây.

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
