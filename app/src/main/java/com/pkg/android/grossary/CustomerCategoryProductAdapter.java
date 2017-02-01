package com.pkg.android.grossary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.*;

/**
 * Created by GAURAV on 31-01-2017.
 */

public class CustomerCategoryProductAdapter extends RecyclerView.Adapter<CustomerCategoryProductAdapter.ProductViewHolder> {

    private Context mContext;
    private List<Product> mProductList;


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = mProductList.get(position);
        holder.product_name.setText(product.getProduct_name());
        holder.price.setText(product.getPrice() + "/kg");


        Glide.with(mContext).load(product.getThumbnail()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView product_name, price;
        public ImageView thumbnail;
        public CardView mCardView;

        public ProductViewHolder(View view) {
            super(view);
            product_name = (TextView) view.findViewById(R.id.product_name);
            price = (TextView)view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            mCardView = (CardView)view.findViewById(R.id.card_view);
        }
    }

    public CustomerCategoryProductAdapter(Context context, List<Product> productList) {
        mContext = context;
        mProductList = productList;
    }

}
