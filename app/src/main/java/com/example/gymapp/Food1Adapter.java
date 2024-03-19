package com.example.gymapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Food1Adapter extends ArrayAdapter<String> {

    private List<String> mItems;
    private Context mContext;
    private FirebaseFirestore mFirestore;
    private String mSelectedType;

    public Food1Adapter(Context context, int resource, String selectedType) {
        super(context, resource);
        mContext = context;
        mItems = new ArrayList<>();
        mSelectedType = selectedType;
        mFirestore = FirebaseFirestore.getInstance();
        loadDataFromFirestore();
    }

    private void loadDataFromFirestore() {
        mFirestore.collection("Food")
                .document(mSelectedType)
                .collection("ThucPham")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                String foodName = document.getId();
                                mItems.add(foodName);
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(android.R.layout.simple_spinner_item, null);
        }

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(mItems.get(position));
        return view;
    }
}

