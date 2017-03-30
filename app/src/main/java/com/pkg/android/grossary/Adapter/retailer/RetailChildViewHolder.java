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
    public ImageButton Plus;
    public ImageButton Minus;
    public TextView mTotalPrice;

    public RetailChildViewHolder(View itemView) {
        super(itemView);

        mProductQuantity = (EditText)itemView.findViewById(R.id.product_quantity);
        Plus = (ImageButton)itemView.findViewById(R.id.product_plus);
        Minus = (ImageButton)itemView.findViewById(R.id.product_minus);
        mTotalPrice = (TextView)itemView.findViewById(R.id.total_price);
    }
}
