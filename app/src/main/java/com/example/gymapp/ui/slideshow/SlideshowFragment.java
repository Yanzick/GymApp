package com.example.gymapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymapp.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    private SlideshowViewModel slideshowViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        slideshowViewModel = new ViewModelProvider(requireActivity()).get(SlideshowViewModel.class);

        slideshowViewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String userEmail) {
                // Update UI khi dữ liệu thay đổi
                EditText emailEditText = binding.EmailHome;
                emailEditText.setText(userEmail);
            }
        });

        slideshowViewModel.getTenKH().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String userName) {
                // Update UI khi dữ liệu thay đổi
                EditText nameEditText = binding.Namehome;
                nameEditText.setText(userName);
            }
        });

        slideshowViewModel.getSdtKH().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String userPhone) {
                // Update UI khi dữ liệu thay đổi
                EditText stdEditText = binding.STDHome;
                stdEditText.setText(userPhone);
            }
        });
        // Thêm sự kiện click hoặc thực hiện các tác vụ khác tại đây.

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
