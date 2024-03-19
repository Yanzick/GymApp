package com.example.gymapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {


    private final MutableLiveData<String> tenKH;


    public HomeViewModel() {

        tenKH = new MutableLiveData<>();

    }



    public LiveData<String> getTenKH() {
        return tenKH;
    }



    public void setTenKH(String userName) {
        tenKH.setValue(userName);
    }


}

