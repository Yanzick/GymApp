package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private List<Integer> days;
    static List<Boolean> selectedDays;

    public DayAdapter() {
        selectedDays = new ArrayList<>();
    }

    public void setDays(List<Integer> days) {
        this.days = days;
        // Khởi tạo trạng thái chọn cho tất cả các ngày
        selectedDays.clear();
        for (int i = 0; i < days.size(); i++) {
            selectedDays.add(false);
        }
        notifyDataSetChanged();
    }

    public boolean isDaySelected(int position) {
        return selectedDays.get(position);
    }


    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        int day = days.get(position);
        holder.txtDay.setText(String.valueOf(day));

        // Cập nhật trạng thái của ô chọn
        holder.checkBoxDay.setChecked(selectedDays.get(position));
    }

    @Override
    public int getItemCount() {
        return days != null ? days.size() : 0;
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDay;
        private CheckBox checkBoxDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txtDay);
            checkBoxDay = itemView.findViewById(R.id.checkBoxDay);

            // Xử lý sự kiện khi người dùng chọn hoặc bỏ chọn ô chọn
            checkBoxDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Cập nhật trạng thái của ngày khi ô chọn được thay đổi
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectedDays.set(position, isChecked);
                    }
                }
            });
        }
    }
    // Trong lớp DayAdapter
    public void setSelected(int position, boolean isSelected) {
        selectedDays.set(position, isSelected);
        notifyDataSetChanged(); // Cập nhật RecyclerView khi trạng thái thay đổi
    }

}



