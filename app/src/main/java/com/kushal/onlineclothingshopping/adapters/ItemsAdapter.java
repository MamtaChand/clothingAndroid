package com.kushal.onlineclothingshopping.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kushal.onlineclothingshopping.DescriptionActivity;
import com.kushal.onlineclothingshopping.R;
import com.kushal.onlineclothingshopping.models.Item;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>{

    Context mContext;
    List<Item> itemsList;

    public ItemsAdapter(Context mContext, List<Item> itemsList) {
        this.mContext = mContext;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int i) {
        final Item item = itemsList.get(i);

        itemsViewHolder.imageName.setImageResource(item.getItemImageName());
        itemsViewHolder.itemName.setText(item.getItemName());
        itemsViewHolder.itemPrice.setText("£"+item.getItemPrice());

        itemsViewHolder.imageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(onItemClick(item));
            }
        });
        itemsViewHolder.itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(onItemClick(item));
            }
        });
        itemsViewHolder.itemPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(onItemClick(item));
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public class ItemsViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageName;
        TextView itemName,itemPrice;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemName = itemView.findViewById(R.id.itemName);
            this.itemPrice = itemView.findViewById(R.id.itemPrice);
            this.imageName = itemView.findViewById(R.id.itemImage);
        }
    }

    private Intent onItemClick(Item item){
        Intent intent = new Intent(mContext, DescriptionActivity.class);

        intent.putExtra("name", item.getItemName());
        intent.putExtra("image", item.getItemImageName());
        intent.putExtra("price", item.getItemPrice());
        intent.putExtra("description", item.getItemDescription());

        return intent;
    }
}
