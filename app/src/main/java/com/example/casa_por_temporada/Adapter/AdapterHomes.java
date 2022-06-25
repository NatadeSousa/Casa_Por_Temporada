package com.example.casa_por_temporada.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casa_por_temporada.Model.Home;
import com.example.casa_por_temporada.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterHomes extends RecyclerView.Adapter<AdapterHomes.MyViewHolder>{

    private List<Home> homeList;
    private OnClick onClick;

    public AdapterHomes(List<Home> homeList, OnClick onClick) {
        this.homeList = homeList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Home home = homeList.get(position);

        Picasso.get().load(home.getImageUrl()).into(holder.imgHome);
        holder.textTitle.setText(home.getTitle());
        holder.textDescription.setText(home.getDescription());
        holder.textDate.setText("");

        holder.itemView.setOnClickListener(view -> onClick.onClickListener(home));

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public interface OnClick{
        public void onClickListener(Home home);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView textTitle,textDescription,textDate;
        ImageView imgHome;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHome = itemView.findViewById(R.id.img_home);
            textTitle = itemView.findViewById(R.id.text_title);
            textDescription = itemView.findViewById(R.id.text_description);
            textDate = itemView.findViewById(R.id.text_date);
        }
    }
}
