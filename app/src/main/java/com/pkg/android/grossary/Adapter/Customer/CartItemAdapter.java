package com.pkg.android.grossary.Adapter.Customer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.CartItem;

import java.util.List;

/**
 * Created by GAURAV on 10-02-2017.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder> {
    private static final String TAG = "CartItemAdapter";
    private List<CartItem> mCartItemList;
    private Context mContext;

    public CartItemAdapter(List<CartItem> cartItemList, Context context) {
        mCartItemList = cartItemList;
        mContext = context;
    }

    @Override
    public CartItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_list_row, parent,false);
        return new CartItemHolder(view);
    }

    @Override
    public void onBindViewHolder(CartItemHolder holder, int position) {
        final CartItem cartItem = mCartItemList.get(position);
        holder.title.setText(cartItem.getProduct().getProduct_name());
        holder.quantity.setText(String.valueOf(cartItem.getCartquantity()));
        int totalprice_peritem = cartItem.getCartquantity() * cartItem.getProduct().getPrice();
        holder.price.setText(mContext.getString(R.string.Rs) + String.valueOf(totalprice_peritem));
    }

    @Override
    public int getItemCount() {
        return mCartItemList.size();
    }

    public class CartItemHolder extends RecyclerView.ViewHolder{
        public TextView title, price, quantity;

        public CartItemHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.cart_item_title);
            price = (TextView)view.findViewById(R.id.cart_item_price);
            quantity = (TextView)view.findViewById(R.id.cart_item_quantity);
        }
    }
}
