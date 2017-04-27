package com.pkg.android.grossary.Adapter.retailer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.pkg.android.grossary.model.RetailList;
import com.pkg.android.grossary.model.RetailListParent;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.other.CircleTransform;

import java.util.List;


/**
 * Created by GAURAV on 06-03-2017.
 */

public class RetailExpandableAdapter extends ExpandableRecyclerAdapter<RetailListParent, RetailList, RetailParentViewHolder, RetailChildViewHolder> {

    LayoutInflater mInflater;
    GrossaryApplication ShoppingCart;
    private List<RetailListParent> mCartItemProductList;
    private Context mContext;

    public RetailExpandableAdapter(Context context, List<RetailListParent> parentList, GrossaryApplication ShoppingCart) {
        super(parentList);
        this.ShoppingCart = ShoppingCart;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCartItemProductList = parentList;
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
    public void onBindParentViewHolder(@NonNull final RetailParentViewHolder parentViewHolder, final int parentPosition, @NonNull RetailListParent parent) {
        final RetailListParent item = getParentList().get(parentPosition);

        if(item.getColorval()>=0 && item.getColorval()<0.33){
            parentViewHolder.mPricePerUnit.setTextColor(mContext.getResources().getColor(R.color.green));
        }else if(item.getColorval()>=0.33 && item.getColorval()<0.67){
            parentViewHolder.mPricePerUnit.setTextColor(mContext.getResources().getColor(R.color.amber));
        }else if(item.getColorval()>=0.67 && item.getColorval()<=1){
            parentViewHolder.mPricePerUnit.setTextColor(mContext.getResources().getColor(R.color.dark_orange));
        }else{
            //error
            parentViewHolder.mPricePerUnit.setTextColor(mContext.getResources().getColor(R.color.violet));
        }

        parentViewHolder.mProductTitle.setText(item.getProduct().getProduct_name());

        String price_per_unit = item.getProduct().getPrice() + "/" + item.getProduct().getProduct_unit();
        parentViewHolder.mPricePerUnit.setText(mContext.getResources().getText(R.string.Rs)+price_per_unit);

        //Glide.with(mContext).load(item.getProduct().getThumbnail()).into(parentViewHolder.thumbnail);
        parentViewHolder.thumbnail.setImageBitmap(CircleTransform.getRoundedShape(CircleTransform.decodeFile(mContext,item.getProduct().getThumbnail()),200));

        //initialization
        //parentViewHolder.selectCheckBox.setOnCheckedChangeListener(null);
        if(item.getCartItem().getCartquantity()>0){
            parentViewHolder.selectCheckBox.setEnabled(true);
        }else{
            parentViewHolder.selectCheckBox.setEnabled(false);
        }
        parentViewHolder.selectCheckBox.setChecked(item.isSelected());
        parentViewHolder.selectCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                    item.setSelected(checked);
                    if(checked){
                        if(item.getCartItem().getCartquantity()>0)
                            ShoppingCart.addToCart(item.getCartItem());
                    }else{
                        ShoppingCart.removeFromCart(item.getCartItem());
                    }

            }
        });





    }

    @Override
    public void onBindChildViewHolder(@NonNull final RetailChildViewHolder childViewHolder, final int parentPosition, final int childPosition, @NonNull final RetailList child) {
        final RetailList item = (RetailList)getParentList().get(parentPosition).getChildList().get(0);
        final RetailListParent itemParent = getParentList().get(parentPosition);

        childViewHolder.setIsRecyclable(false);

        childViewHolder.mTotalPrice.setText(mContext.getString(R.string.Rs)+String.valueOf(item.getTotalprice()));
        childViewHolder.mProductQuantity.setText(String.valueOf(item.getQuantity()));
        childViewHolder.mProductQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int qty;
                if(!charSequence.toString().equals("")){
                    qty = Integer.parseInt(charSequence.toString());
                }
                else
                    qty=0;

                itemParent.setActual_stock(qty);
                item.setQuantity(qty);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        childViewHolder.mProductQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    notifyDataSetChanged();

                    return true;
                }
                return false;
            }
        });

        childViewHolder.Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int new_qty = itemParent.incrementqty();
                childViewHolder.mProductQuantity.setText(String.valueOf(new_qty));
                notifyDataSetChanged();
            }

        });
        childViewHolder.Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getQuantity()>0) {
                    int new_qty = itemParent.decrementqty();
                    childViewHolder.mProductQuantity.setText(String.valueOf(new_qty));
                    notifyDataSetChanged();
                }
            }
        });
    }




}
