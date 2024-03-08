package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.content.Context;

import de.hdodenhof.circleimageview.CircleImageView;

public class PTAdapter extends RecyclerView.Adapter<PTAdapter.PTView>{
    private Context mContext;
    private List<PT> mList;

    public PTAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<PT> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PTView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pt, parent, false);
        return new PTView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PTView holder, int position) {
        PT pt = mList.get(position);
        if (pt == null){
            return;
        }
        holder.imagePT.setImageResource(pt.getResourceID());
        holder.tvPT.setText(pt.getName());
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    public class PTView extends RecyclerView.ViewHolder {
        private CircleImageView imagePT; // Sử dụng CircleImageView thay vì ImageView
        private TextView tvPT;

        public PTView(@NonNull View itemView) {
            super(itemView);
            imagePT = itemView.findViewById(R.id.Image1);
            tvPT = itemView.findViewById(R.id.Text1);
        }
    }
}

