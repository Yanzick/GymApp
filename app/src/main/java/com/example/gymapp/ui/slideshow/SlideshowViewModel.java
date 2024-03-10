package com.example.gymapp.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> email;
    private final MutableLiveData<String> tenKH;
    private final MutableLiveData<String> sdtKH;

    public SlideshowViewModel() {
        email = new MutableLiveData<>();
        tenKH = new MutableLiveData<>();
        sdtKH = new MutableLiveData<>();
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getTenKH() {
        return tenKH;
    }

    public LiveData<String> getSdtKH() {
        return sdtKH;
    }

    public void setEmail(String userEmail) {
        email.setValue(userEmail);
    }

    public void setTenKH(String userName) {
        tenKH.setValue(userName);
    }

    public void setSdtKH(String userPhone) {
        sdtKH.setValue(userPhone);
    }
}

