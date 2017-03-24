package com.pkg.android.grossary.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.Labs.ShoppingListLab;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;
import com.pkg.android.grossary.other.Parser;
import com.pkg.android.grossary.other.Session;

import java.util.List;

/**
 * Created by GAURAV on 31-01-2017.
 */

public class CustomerCategoryProductAdapter extends RecyclerView.Adapter<CustomerCategoryProductAdapter.ProductViewHolder> {

    private Context mContext;
    private List<CartItem> mCartItemProductList;
    private GrossaryApplication ShoppingCart;

    private String TAG = "CustomerAct";
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final CartItem item = mCartItemProductList.get(position);
        Log.d(TAG, String.valueOf(item.getCartItem()));
        holder.product_name.setText(((Product)item.getCartItem()).getProduct_name());
        holder.price.setText(item.getCartItem().getPrice() + "/" + item.getCartItem().getProduct_unit());
        holder.productquantity.setText(String.valueOf(item.getCartquantity()));

        //remember that list starts from 0 and id from 1
        holder.productplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.productquantity.setText(String.valueOf(item.incrementqty()));
                if(item.getCartquantity()>0){
                    ShoppingCart.addToCart(item);
                }
                disableInShoppingList(position+1);  //vvip step

            }
        });

        holder.productminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getCartquantity()>0) {
                    holder.productquantity.setText(String.valueOf(item.decrementqty()));
                    ShoppingCart.removeFromCart(item);
                }
                disableInShoppingList(position+1);  //vvip step
            }
        });
        Glide.with(mContext).load(item.getCartItem().getThumbnail()).into(holder.thumbnail);

    }

    private void disableInShoppingList(int id) {
        ShoppingListLab s = ShoppingListLab.get(mContext);


        s.DisableItem(id);

    }

    @Override
    public int getItemCount() {
        return mCartItemProductList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView product_name, price;
        public ImageView thumbnail;
        public CardView mCardView;
        public AppCompatButton productplus;
        public AppCompatButton productminus;
        public AppCompatButton productquantity;

        public ProductViewHolder(View view) {
            super(view);
            product_name = (TextView) view.findViewById(R.id.product_name);
            price = (TextView)view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            mCardView = (CardView)view.findViewById(R.id.card_view);

            productplus = (AppCompatButton)view.findViewById(R.id.product_plus);
            productminus = (AppCompatButton)view.findViewById(R.id.product_minus);
            productquantity = (AppCompatButton)view.findViewById(R.id.product_quantity);



        }
    }

    public CustomerCategoryProductAdapter(Context context, List<CartItem> cartItemList, GrossaryApplication ShoppingCart) {
        mContext = context;
        mCartItemProductList = cartItemList;
        this.ShoppingCart = ShoppingCart;

    }

}
