package com.pkg.android.grossary.Adapter.Customer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.Category;
import com.pkg.android.grossary.navigation.Customer.CategoryWiseProductListActivity;

import java.util.Random;

/**
 * Created by GAURAV on 19-03-2017.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Category[] mDataSet;
    private Context mContext;
    private Random mRandom = new Random();

    public CategoryAdapter(Context context,Category[] DataSet){
        mDataSet = DataSet;
        mContext = context;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        public TextView category_textview;
        public ImageView category_imageview;
        public CardView mCardView;
        public CategoryViewHolder(View v){
            super(v);
            category_textview = (TextView)v.findViewById(R.id.category_name);
            category_imageview = (ImageView) v.findViewById(R.id.category_thumbnail);
            mCardView = (CardView)v.findViewById(R.id.card_view);
        }
    }

    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.category_card,parent,false);
        CategoryViewHolder vh = new CategoryViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        holder.category_textview.setText(mDataSet[position].getCategory_name());
        Glide.with(mContext).load(mDataSet[position].getCategory_thumbnail()).into(holder.category_imageview);

        holder.category_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CategoryWiseProductListActivity.newIntent(mContext, mDataSet[position].getCategory_id());
                mContext.startActivity(i);
            }
        });

    }



    @Override
    public int getItemCount(){
        return mDataSet.length;
    }


}
