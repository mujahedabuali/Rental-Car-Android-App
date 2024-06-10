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

    public class renrCarAdapter extends RecyclerView.Adapter<com.example.vortexcar.renrCarAdapter.CarViewHolder> {

        private List<Car> carList;
        private Context context;

        public renrCarAdapter(List<Car> carList, Context context) {
            this.carList = carList;
            this.context = context;
        }

        @NonNull
        @Override
        public com.example.vortexcar.renrCarAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
            return new com.example.vortexcar.renrCarAdapter.CarViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.vortexcar.renrCarAdapter.CarViewHolder holder, int position) {
            Car car = carList.get(position);
            holder.textViewCarName.setText(car.getCompany());
            holder.textViewCarDetails.setText(car.getModel_year());
            holder.textViewCarColor.setText("Color: "+car.getColor());
            Glide.with(context).load(car.getImage()).into(holder.imageViewCar);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, theRentCarInfo.class);
                intent.putExtra("id", car.getId());

                intent.putExtra("carName", car.getCompany());
                intent.putExtra("carModel", car.getModel_year());
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