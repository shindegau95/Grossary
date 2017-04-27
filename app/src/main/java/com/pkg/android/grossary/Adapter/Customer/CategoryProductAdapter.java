package com.pkg.android.grossary.Adapter.Customer;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.Labs.RecipeLab;
import com.pkg.android.grossary.Labs.ShoppingListLab;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;
import com.pkg.android.grossary.model.Recipe;
import com.pkg.android.grossary.navigation.Customer.CategoryWiseProductListActivity;
import com.pkg.android.grossary.other.CallServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 31-01-2017.
 */

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.ProductViewHolder> {

    private static Context mContext;
    private List<CartItem> mCartItemProductList;
    private static List<Recipe> mRecipeList;
    private GrossaryApplication ShoppingCart;
    private static CategoryWiseProductListActivity mParentActivity;
    private String TAG = "CustomerAct";
    private static RecyclerView mRecipeRecyclerView;
    private static RecipeAdapter mRecipeAdapter;

    //constructor
    public CategoryProductAdapter(Context context, List<CartItem> cartItemList, GrossaryApplication ShoppingCart, CategoryWiseProductListActivity categoryWiseProductListActivity) {
        mContext = context;
        mCartItemProductList = cartItemList;
        this.ShoppingCart = ShoppingCart;
        this.mParentActivity = categoryWiseProductListActivity;
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

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final CartItem item = mCartItemProductList.get(position);
        mRecipeRecyclerView = (RecyclerView)mParentActivity.findViewById(R.id.recipe_recycler_view);
        holder.product_name.setText(((Product)item.getProduct()).getProduct_name());
        holder.price.setText(mContext.getString(R.string.Rs)+item.getProduct().getPrice() + "/" + item.getProduct().getProduct_unit());
        holder.productquantity.setText(String.valueOf(item.getCartquantity()));


        //remember that list starts from 0 and id from 1

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.productquantity.setText(String.valueOf(item.incrementqty()));
                if(item.getCartquantity()>0){
                    ShoppingCart.addToCart(item);
                }
                disableInShoppingList(position+1);  //vvip step

                getRecipies();
                changeBottomSheetState();
                notifyDataSetChanged();
            }
        });

        holder.productplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.productquantity.setText(String.valueOf(item.incrementqty()));
                if(item.getCartquantity()>0){
                    ShoppingCart.addToCart(item);
                }
                disableInShoppingList(position+1);  //vvip step

                new LoadRecipes().execute();
                getRecipies();
                mRecipeAdapter.notifyDataSetChanged();

                changeBottomSheetState();
                notifyDataSetChanged();
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

                new LoadRecipes().execute();
                getRecipies();
                mRecipeAdapter.notifyDataSetChanged();

                changeBottomSheetState();
                notifyDataSetChanged();
            }
        });
        Glide.with(mContext).load(item.getProduct().getThumbnail()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return mCartItemProductList.size();
    }

    private ArrayList<Integer> prepareProdList() {
        GrossaryApplication shoppingCart = (GrossaryApplication)GrossaryApplication.getInstance();

        List<CartItem> cart = shoppingCart.getCart();

        ArrayList<Integer> prodlist=new ArrayList<>();
        for(CartItem c : cart){
            prodlist.add(c.getProduct().getProduct_id());

        }
        Log.d(TAG, "Prod List= "+ prodlist.toString());
        return prodlist;
    }

    public static void getRecipies() {


        mRecipeList = new ArrayList<>();
        prepareRecipies();

        mRecipeAdapter = new RecipeAdapter(mContext, mRecipeList, mParentActivity);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRecipeRecyclerView.setLayoutManager(mLayoutManager);
        mRecipeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
    }

    private static void prepareRecipies() {
        RecipeLab r = RecipeLab.get(mContext, GrossaryApplication.getInstance().getRecipeList());
        mRecipeList = r.getRecipeList();
    }



    private void disableInShoppingList(int id) {
        ShoppingListLab s = ShoppingListLab.get(mContext);
        s.DisableItem(id);

    }

    public void changeBottomSheetState(){
        if(ShoppingCart.getCart().isEmpty()){
            mParentActivity.behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }else{

            mParentActivity.behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }


    public class LoadRecipes extends AsyncTask<Void, Void, Void>{
        private ProgressBar mProgressBar;


        public LoadRecipes(){
            super();
            mProgressBar = (ProgressBar)mParentActivity.findViewById(R.id.recipeprogressBar);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<Integer> prodlist = prepareProdList();
            CallServer.updateRecipeList(mParentActivity, prodlist, mProgressBar, mRecipeRecyclerView);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mRecipeRecyclerView.setVisibility(View.GONE);
        }
    }
}
