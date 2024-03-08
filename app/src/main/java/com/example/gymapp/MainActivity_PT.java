package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_PT extends AppCompatActivity {
    private RecyclerView rcv;
    private PTAdapter PTAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pt);
        rcv = findViewById(R.id.rcv);
        PTAd = new PTAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        PTAd.setData(getListPT());
        rcv.setAdapter(PTAd);
    }
    private List<PT> getListPT(){
        List<PT> list = new ArrayList<>();
        list.add(new PT(R.drawable.coach, "HLV 1"));
        return list;
    }
}