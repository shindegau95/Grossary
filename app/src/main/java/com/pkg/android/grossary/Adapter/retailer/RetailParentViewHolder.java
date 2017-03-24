package com.pkg.android.grossary.Adapter.retailer;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.pkg.android.grossary.R;

/**
 * Created by GAURAV on 06-03-2017.
 */

public class RetailParentViewHolder extends ParentViewHolder {

    public TextView mProductTitle;
    public TextView mPricePerUnit;

    public RetailParentViewHolder(View itemView) {
        super(itemView);

        mProductTitle = (TextView) itemView.findViewById(R.id.parent_list_item_product_title_text_view);
        mPricePerUnit = (TextView) itemView.findViewById(R.id.parent_list_item_price_per_unit);
    }
}