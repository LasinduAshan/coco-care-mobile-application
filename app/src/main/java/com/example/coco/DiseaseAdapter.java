package com.example.coco;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coco.model.DiseaseData;

import java.util.ArrayList;
import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseViewHolder> {

    private Context context;
    private List<DiseaseData> dataList;

    public DiseaseAdapter(Context context, List<DiseaseData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyle_item, parent, false);
        return new DiseaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDiseaseImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getDiseaseName()); // To view title on recycle view

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Diseases_view.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDiseaseImage());
                intent.putExtra("Name", dataList.get(holder.getAdapterPosition()).getDiseaseName());
                intent.putExtra("Treatments", dataList.get(holder.getAdapterPosition()).getDiseaseTreatement());
                intent.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class DiseaseViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTitle;
    CardView recCard;
    public DiseaseViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recTitle = itemView.findViewById(R.id.recTitle);

    }
}
