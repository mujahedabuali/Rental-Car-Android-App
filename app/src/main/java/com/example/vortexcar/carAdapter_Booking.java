package com.example.vortexcar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class carAdapter_Booking extends RecyclerView.Adapter<carAdapter_Booking.CarViewHolder> {

    private List<RentedCar> carList;
    private List<RentedCar> carListFull;
    private Context context;

    public carAdapter_Booking(List<RentedCar> carList, Context context) {
        this.carList = carList;
        this.context = context;
        this.carListFull = new ArrayList<>(carList);
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        RentedCar car = carList.get(position);
        holder.textViewCarName.setText(car.getCar().getCompany());
        holder.textViewCarDetails.setText(car.getCar().getModel_year());
        holder.textViewCarMonthlyPrice.setText(car.getStartDate());
        holder.textViewCarDaily.setText(car.getEndDate());
        Glide.with(context).load(car.getCar().getImage()).into(holder.imageViewCar);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CarInfo_Booking.class);
            intent.putExtra("imageUrl", car.getCar().getImage());
            intent.putExtra("carName", car.getCar().getCompany());
            intent.putExtra("carStrartDate", car.getStartDate());
            intent.putExtra("carEndDate", car.getEndDate());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCar;
        TextView textViewCarName;
        TextView textViewCarDetails;
        TextView textViewCarMonthlyPrice;
        TextView textViewCarDaily;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCar = itemView.findViewById(R.id.imageViewCar);
            textViewCarName = itemView.findViewById(R.id.textViewCarName);
            textViewCarDetails = itemView.findViewById(R.id.textViewCarModel);
            textViewCarMonthlyPrice = itemView.findViewById(R.id.textViewCarMonthlyPrice);
            textViewCarDaily = itemView.findViewById(R.id.textViewCarDailyPrice);
        }
    }



}
