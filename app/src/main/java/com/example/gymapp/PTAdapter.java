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

public class PTAdapter extends RecyclerView.Adapter<PTAdapter.PTView> {
    private Context mContext;
    private List<PT> mList;
    private OnItemClickListener mListener;

    public PTAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<PT> list) {
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
        if (pt == null) {
            return;
        }

        // Tải hình ảnh từ URL bằng Picasso
        Picasso.get().load(pt.getImageUrl()).into(holder.imagePT);

        holder.tvPT.setText(pt.getName());
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class PTView extends RecyclerView.ViewHolder {
        private CircleImageView imagePT;
        private TextView tvPT;

        public PTView(@NonNull View itemView) {
            super(itemView);
            imagePT = itemView.findViewById(R.id.Image1);
            tvPT = itemView.findViewById(R.id.Text1);

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
