package com.example.imagesynclib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagesynclib.Database.Local.mGeneric;

import java.util.ArrayList;


public class aSync extends RecyclerView.Adapter<aSync.ViewHolder>  {

    private ArrayList<mGeneric> generics = new ArrayList<>();
    private Context context;

    public aSync(Context context, ArrayList<mGeneric> generics) {
        this.generics = generics;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.generics_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
       // final Gradings commodity = gradings.get(position);

       /* Picasso.with(context)
                .load(context.getString(R.string.base_url)+commodity.getImage_url())
                .error(R.drawable.logo_icon)
                .into(holder.iv_commodity);

        String c_name = commodity.getName();
        c_name = String.valueOf(c_name.charAt(0)).toUpperCase() + c_name.substring(1, c_name.length());
        holder.commodity_name.setText(c_name);*/



    }

    public void updateCommodities(ArrayList<mGeneric> gradings){
        this.generics = generics;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return 10;// gradings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView commodity_name ;
        ImageView iv_commodity;

        public ViewHolder(View itemView) {
            super(itemView);

//            commodity_name = itemView.findViewById(R.id.commodity_name);
//            iv_commodity = itemView.findViewById(R.id.iv_commodity);

        }
    }


}
