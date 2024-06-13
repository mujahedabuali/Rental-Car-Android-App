package com.example.vortexcar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class carAdapterForAdmin extends RecyclerView.Adapter<carAdapterForAdmin.CarViewHolder> {

    private List<Car> carList;
    private Context context;

    public carAdapterForAdmin(List<Car> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.textViewCarName.setText(car.getCompany());
        holder.textViewCarDetails.setText(car.getModel_year());
        holder.textViewCarColor.setText("Color: "+car.getColor());
        holder.textViewCarMonthlyPrice.setText("Monthly Price: "+String.valueOf(car.getMonthlyPrice())); // Convert int to String
        holder.textViewCarDaily.setText("Daily Price: "+String.valueOf(car.getDailyPrice())); // Convert int to String
        Glide.with(context).load(car.getImage()).into(holder.imageViewCar);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, carInfoForAdmin.class);
            intent.putExtra("id", car.getId());

            intent.putExtra("imageUrl", car.getImage());
            intent.putExtra("carName", car.getCompany());
            intent.putExtra("carModel", car.getModel_year());
            intent.putExtra("carColor", car.getColor());
            intent.putExtra("mileage", car.getMileage());
            intent.putExtra("dailyPrice", car.getDailyPrice());
            intent.putExtra("monthlyPrice", car.getMonthlyPrice());
            intent.putExtra("status", car.getStatus());

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
        TextView textViewCarColor;
        TextView textViewCarMonthlyPrice;
        TextView textViewCarDaily;


        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCar = itemView.findViewById(R.id.imageViewCar);
            textViewCarName = itemView.findViewById(R.id.textViewCarName);
            textViewCarDetails = itemView.findViewById(R.id.textViewCarModel);
            textViewCarColor = itemView.findViewById(R.id.textViewCarColor);
            textViewCarMonthlyPrice = itemView.findViewById(R.id.textViewCarMonthlyPrice);
            textViewCarDaily = itemView.findViewById(R.id.textViewCarDailyPrice);


        }
    }
}