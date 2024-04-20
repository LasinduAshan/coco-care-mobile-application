package com.example.coco;

import android.content.Context;
import android.content.Intent;
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
import com.example.coco.model.UserData;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder>{
    private Context context;
    private List<UserData> userdataList;

    public UsersAdapter(Context context, List<UserData> userdataList) {
        this.context = context;
        this.userdataList = userdataList;
    }


    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycle_item, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        Glide.with(context).load(userdataList.get(position).getUserImage()).into(holder.recUserImage);
        holder.recUserName.setText(userdataList.get(position).getUsername()); // To view title on recycle view

        holder.recUserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, User_Account.class);
                intent.putExtra("Image", userdataList.get(holder.getAdapterPosition()).getUserImage());
                intent.putExtra("Name", userdataList.get(holder.getAdapterPosition()).getUsername());
                intent.putExtra("Email", userdataList.get(holder.getAdapterPosition()).getUseremail());
                intent.putExtra("Phone", userdataList.get(holder.getAdapterPosition()).getUserphone());
                intent.putExtra("Key",userdataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userdataList.size();
    }
}


class UsersViewHolder extends RecyclerView.ViewHolder{

    ImageView recUserImage;
    TextView recUserName;
    CardView recUserCard;
    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        recUserImage = itemView.findViewById(R.id.recUserImage);
        recUserName = itemView.findViewById(R.id.recUserName);
        recUserCard = itemView.findViewById(R.id.recUserCard);

    }
}
