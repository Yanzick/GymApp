package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamView> {
    private Context mContext;
    private List<SanPhamMain> mList;
    private OnItemClickListener mListener;

    public SanPhamAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<SanPhamMain> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SanPhamView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent, false);
        return new SanPhamView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamView holder, int position) {
        SanPhamMain sanpham = mList.get(position);
        if (sanpham == null) {
            return;
        }

        // Tải hình ảnh từ URL bằng Picasso
        Picasso.get().load(sanpham.getImageUrl()).into(holder.imageSanPham);

        holder.tvSanPham.setText(sanpham.getName());
        holder.tvGia.setText(String.valueOf(sanpham.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class SanPhamView extends RecyclerView.ViewHolder {
        private CircleImageView imageSanPham;
        private TextView tvSanPham;
        private TextView tvGia;
        public SanPhamView(@NonNull View itemView) {

            super(itemView);
            imageSanPham = itemView.findViewById(R.id.ImageSanpham);
            tvSanPham = itemView.findViewById(R.id.TextSanPham);
            tvGia = itemView.findViewById(R.id.TextGia);
            itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String name = mList.get(position).getName();
                        mListener.onItemClick(position, name);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String name);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
