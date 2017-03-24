package com.pkg.android.grossary.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.Adapter.retailer.RetailChildViewHolder;
import com.pkg.android.grossary.Adapter.retailer.RetailParentViewHolder;

import java.util.List;


/**
 * Created by GAURAV on 06-03-2017.
 */

public class RetailExpandableAdapter extends ExpandableRecyclerAdapter<RetailListParent, RetailList, RetailParentViewHolder, RetailChildViewHolder> {

    LayoutInflater mInflater;
    GrossaryApplication ShoppingCart;
    private String TAG = "sdfsdf";

    public RetailExpandableAdapter(Context context, List parentList, GrossaryApplication ShoppingCart) {
        super(parentList);
        this.ShoppingCart = ShoppingCart;

        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RetailParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_retail_product_parent, parentViewGroup, false);
        return new RetailParentViewHolder(view);
    }

    @NonNull
    @Override
    public RetailChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_retail_product_child, childViewGroup, false);
        return new RetailChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull RetailParentViewHolder parentViewHolder, int parentPosition, @NonNull RetailListParent parent) {
        RetailListParent item = parent;
        parentViewHolder.mProductTitle.setText(item.getCartItem().getProduct_name());
        String price_per_unit = item.getCartItem().getPrice() + "/" + item.getCartItem().getProduct_unit();
        parentViewHolder.mPricePerUnit.setText(price_per_unit);
    }

    @Override
    public void onBindChildViewHolder(@NonNull final RetailChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull RetailList child) {
        final RetailList item = child;
        childViewHolder.mTotalPrice.setText(String.valueOf(parentPosition));//String.valueOf(item.getTotalprice()));
        childViewHolder.mProductQuantity.setText(String.valueOf(item.getQuantity()));
        Log.d(TAG,String.valueOf(item.getQuantity()));
        childViewHolder.AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"plus button clicked");
                childViewHolder.mProductQuantity.setText(String.valueOf(item.incrementqty()));
                if(item.getQuantity()>0){
                    //ShoppingCart.addToCart(item);
                }
            }

        });
        childViewHolder.AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getQuantity()>0) {
                    childViewHolder.mProductQuantity.setText(String.valueOf(item.decrementqty()));
                    //ShoppingCart.removeFromCart(item);
                }
            }
        });
    }



}
