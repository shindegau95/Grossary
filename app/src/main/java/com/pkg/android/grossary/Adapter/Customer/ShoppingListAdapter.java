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
import com.pkg.android.grossary.Labs.CerealLab;
import com.pkg.android.grossary.Labs.DairyLab;
import com.pkg.android.grossary.Labs.DryFruitsLab;
import com.pkg.android.grossary.Labs.FruitsLab;
import com.pkg.android.grossary.Labs.OthersLab;
import com.pkg.android.grossary.Labs.ShoppingListLab;
import com.pkg.android.grossary.Labs.VegetablesLab;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;

import java.util.List;

/**
 * Created by GAURAV on 23-03-2017.
 */

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ProductViewHolder>{
    private Context mContext;
    private List<CartItem> mCartItemProductList;
    private List<Boolean> selectedList;
    private GrossaryApplication ShoppingCart;

    private String TAG = "CustomerAct";
    @Override
    public ShoppingListAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_product_card, parent, false);

        return new ShoppingListAdapter.ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShoppingListAdapter.ProductViewHolder holder, final int position) {
        final CartItem item = mCartItemProductList.get(position);
        final Boolean selected = selectedList.get(position);
        //Log.d(TAG, String.valueOf(item.getCartItem()));
        holder.product_name.setText(((Product)item.getProduct()).getProduct_name());
        holder.price.setText(mContext.getString(R.string.Rs)+item.getProduct().getPrice() + "/" + item.getProduct().getProduct_unit());
        holder.productquantity.setText(String.valueOf(item.getCartquantity()));

        //itialization
        if(selected) {
            toggle(holder,true);
        }else{
            toggle(holder, false);
        }

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected) {
                    //if its already selected, after clicking,
                    toggle(holder, false);
                    deselect(holder, item, position);

                }else{
                    //if its already deselected, after clicking
                    toggle(holder, true);
                    select(holder,item, position);
                }
                notifyDataSetChanged(); //vvip step

            }
        });
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected) {
                    //if its already selected, after clicking,
                    toggle(holder, false);
                    deselect(holder, item, position);

                }else{
                    //if its already deselected, after clicking
                    toggle(holder, true);
                    select(holder,item, position);
                }
                notifyDataSetChanged(); //vvip step

            }
        });
        holder.productplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int prd_id = item.getProduct().getProduct_id();
                int prd_qty = item.incrementqty();
                setLabQuantity(item, prd_id, prd_qty, mContext);
                holder.productquantity.setText(String.valueOf(prd_qty));

            }
        });

        holder.productminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int prd_id = item.getProduct().getProduct_id();
                int prd_qty = item.decrementqty();

                if(prd_qty == 0){
                    toggle(holder, false);
                    deselect(holder, item, position);
                }else {
                    setLabQuantity(item, prd_id, prd_qty, mContext);
                }
                holder.productquantity.setText(String.valueOf(prd_qty));
            }
        });
        Glide.with(mContext).load(item.getProduct().getThumbnail()).into(holder.thumbnail);

    }

    private void toggle(ProductViewHolder holder, boolean selected){
        if(selected){
            holder.productplus.setEnabled(true);
            holder.productminus.setEnabled(true);
            holder.productplus.setTextColor(mContext.getResources().getColor(R.color.amber));
            holder.productminus.setTextColor(mContext.getResources().getColor(R.color.amber));
            holder.productquantity.setTextColor(mContext.getResources().getColor(R.color.amber));
            holder.select.setText("DESELECT");

        }
        else{
            holder.productplus.setEnabled(false);
            holder.productminus.setEnabled(false);
            holder.productplus.setTextColor(mContext.getResources().getColor(R.color.common_google_signin_btn_text_light_disabled));
            holder.productminus.setTextColor(mContext.getResources().getColor(R.color.common_google_signin_btn_text_light_disabled));
            holder.productquantity.setTextColor(mContext.getResources().getColor(R.color.common_google_signin_btn_text_light_disabled));
            holder.select.setText("SELECT");
        }
    }
    private void deselect(ProductViewHolder holder, CartItem item, int position) {

        int prd_id = item.getProduct().getProduct_id();
        int prd_qty = 0;
        setLabQuantity(item, prd_id, prd_qty, mContext);

        ShoppingListLab s = ShoppingListLab.get(mContext);
        s.DisableItemAtPosition(position);

    }
    private void select(ProductViewHolder holder, CartItem item, int position) {

        int prd_id = item.getProduct().getProduct_id();
        int prd_qty = Integer.parseInt(String.valueOf(holder.productquantity.getText()));
        setLabQuantity(item, prd_id, prd_qty, mContext);

        ShoppingListLab s = ShoppingListLab.get(mContext);
        s.EnableItemAtPosition(position);
    }

    public static void setLabQuantity(CartItem item,int prd_id, int prd_qty, Context mContext) {
        switch(item.getProduct().getCategory_id()){
            case 1: CerealLab c = CerealLab.get(mContext, false);
                c.setRecommendedQuantity(prd_id,prd_qty);
                break;
            case 2: DairyLab dr = DairyLab.get(mContext, false);
                dr.setRecommendedQuantity(prd_id,prd_qty);
                break;
            case 3: FruitsLab f = FruitsLab.get(mContext, false);
                f.setRecommendedQuantity(prd_id,prd_qty);
                break;
            case 4: VegetablesLab v = VegetablesLab.get(mContext, false);
                v.setRecommendedQuantity(prd_id,prd_qty);
                break;
            case 5: DryFruitsLab d = DryFruitsLab.get(mContext, false);
                d.setRecommendedQuantity(prd_id,prd_qty);
                break;
            case 6: OthersLab o = OthersLab.get(mContext, false);
                o.setRecommendedQuantity(prd_id,prd_qty);
                break;
        }
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
        public AppCompatButton select;

        public ProductViewHolder(View view) {
            super(view);
            product_name = (TextView) view.findViewById(R.id.product_name);
            price = (TextView)view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            mCardView = (CardView)view.findViewById(R.id.card_view);

            productplus = (AppCompatButton)view.findViewById(R.id.product_plus);
            productminus = (AppCompatButton)view.findViewById(R.id.product_minus);
            productquantity = (AppCompatButton)view.findViewById(R.id.product_quantity);

            select = (AppCompatButton)view.findViewById(R.id.select);

        }
    }

    public ShoppingListAdapter(Context context, List<CartItem> cartItemList,List<Boolean> selectedList, GrossaryApplication ShoppingCart) {
        mContext = context;
        mCartItemProductList = cartItemList;
        this.selectedList = selectedList;
        this.ShoppingCart = ShoppingCart;

    }


}
