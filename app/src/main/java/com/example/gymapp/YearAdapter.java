package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YearAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mYearsList;

    public YearAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
        mYearsList = new ArrayList<>();
        // Thêm các năm vào danh sách, từ năm 2020 đến năm hiện tại
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear; year <= currentYear + 20; year++) {
            mYearsList.add(String.valueOf(year));
        }

    }

    @Override
    public int getCount() {
        return mYearsList.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mYearsList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        return convertView;
    }
}
