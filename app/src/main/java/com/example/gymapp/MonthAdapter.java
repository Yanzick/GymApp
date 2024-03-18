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
import java.util.List;

public class MonthAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mMonthsList;

    public MonthAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
        mMonthsList = new ArrayList<>();
        // Thêm các tháng vào danh sách
        mMonthsList.add("Tháng 1");
        mMonthsList.add("Tháng 2");
        mMonthsList.add("Tháng 3");
        mMonthsList.add("Tháng 4");
        mMonthsList.add("Tháng 5");
        mMonthsList.add("Tháng 6");
        mMonthsList.add("Tháng 7");
        mMonthsList.add("Tháng 8");
        mMonthsList.add("Tháng 9");
        mMonthsList.add("Tháng 10");
        mMonthsList.add("Tháng 11");
        mMonthsList.add("Tháng 12");
    }

    @Override
    public int getCount() {
        return mMonthsList.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mMonthsList.get(position);
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
