package com.pkg.android.grossary.Adapter.Customer;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.Labs.ShoppingListLab;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;

import java.util.List;

/**
 * Created by GAURAV on 30-03-2017.
 */

public class RecipeProductsAdapter extends RecyclerView.Adapter<RecipeProductsAdapter.RecipeProductsViewHolder> {

    private Context mContext;
    private List<CartItem> mRecipeProductList;
    private GrossaryApplication ShoppingCart;
    private RecyclerView.Adapter parentAdapter;
    private String TAG = "RecipeProd";

    public RecipeProductsAdapter(Context context, List<CartItem> recipeProductList, GrossaryApplication shoppingCart, RecyclerView.Adapter parentAdapter) {
        mContext = context;
        mRecipeProductList = recipeProductList;
        ShoppingCart = shoppingCart;
        this.parentAdapter = parentAdapter;
    }

    public class RecipeProductsViewHolder extends RecyclerView.ViewHolder {
        public TextView recipe_product_name, recipe_product_price;
        public ImageView recipe_product_thumbnail;
        public CardView mRecipeProductCardView;
        public AppCompatButton recipe_product_productplus;
        public AppCompatButton recipe_product_productminus;
        public AppCompatButton recipe_product_productquantity;

        public RecipeProductsViewHolder(View view) {
            super(view);
            recipe_product_name = (TextView) view.findViewById(R.id.product_name);
            recipe_product_price = (TextView)view.findViewById(R.id.price);
            recipe_product_thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            mRecipeProductCardView = (CardView)view.findViewById(R.id.card_view);

            recipe_product_productplus = (AppCompatButton)view.findViewById(R.id.product_plus);
            recipe_product_productminus = (AppCompatButton)view.findViewById(R.id.product_minus);
            recipe_product_productquantity = (AppCompatButton)view.findViewById(R.id.product_quantity);
        }
    }

    @Override
    public RecipeProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);

        return new RecipeProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeProductsViewHolder holder, final int position) {
        final CartItem recipeProduct = mRecipeProductList.get(position);

        holder.recipe_product_name.setText(((Product)recipeProduct.getProduct()).getProduct_name());
        holder.recipe_product_price.setText(mContext.getString(R.string.Rs)+recipeProduct.getProduct().getPrice() + "/" + recipeProduct.getProduct().getProduct_unit());
        holder.recipe_product_productquantity.setText(String.valueOf(recipeProduct.getCartquantity()));


        //remember that list starts from 0 and id from 1
        holder.recipe_product_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.recipe_product_productquantity.setText(String.valueOf(recipeProduct.incrementqty()));
                if(recipeProduct.getCartquantity()>0){
                    ShoppingCart.addToCart(recipeProduct);
                }
                disableInShoppingList(position+1);  //vvip step

            }
        });

        holder.recipe_product_productplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.recipe_product_productquantity.setText(String.valueOf(recipeProduct.incrementqty()));
                if(recipeProduct.getCartquantity()>0){
                    ShoppingCart.addToCart(recipeProduct);
                }
                disableInShoppingList(position+1);  //vvip step

            }
        });

        holder.recipe_product_productminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recipeProduct.getCartquantity()>0) {
                    holder.recipe_product_productquantity.setText(String.valueOf(recipeProduct.decrementqty()));
                    ShoppingCart.removeFromCart(recipeProduct);
                }
                disableInShoppingList(position+1);  //vvip step

                parentAdapter.notifyDataSetChanged();
            }
        });
        Glide.with(mContext).load(recipeProduct.getProduct().getThumbnail()).into(holder.recipe_product_thumbnail);
    }

    @Override
    public int getItemCount() {
        return mRecipeProductList.size();
    }

    private void disableInShoppingList(int id) {
        ShoppingListLab s = ShoppingListLab.get(mContext);
        s.DisableItem(id);
        parentAdapter.notifyDataSetChanged();
    }

}
