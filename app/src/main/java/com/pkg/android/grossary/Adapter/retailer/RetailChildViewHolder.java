package com.pkg.android.grossary.Adapter.retailer;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.pkg.android.grossary.R;

/**
 * Created by GAURAV on 06-03-2017.
 */

public class RetailChildViewHolder extends ChildViewHolder {

    public EditText mProductQuantity;
    public ImageButton AddProduct;
    public ImageButton RemoveProduct;
    public TextView mTotalPrice;

    public RetailChildViewHolder(View itemView) {
        super(itemView);

        mProductQuantity = (EditText)itemView.findViewById(R.id.product_quantity);
        AddProduct = (ImageButton)itemView.findViewById(R.id.product_plus);
        RemoveProduct = (ImageButton)itemView.findViewById(R.id.product_minus);
        mTotalPrice = (TextView)itemView.findViewById(R.id.total_price);
    }
}
