package com.pkg.android.grossary.Adapter.Customer;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.Labs.RecipeLab;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Recipe;
import com.pkg.android.grossary.navigation.Customer.CategoryWiseProductListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 29-03-2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {

    private Context mContext;
    private List<Recipe> mRecipeList;
    private CategoryWiseProductListActivity mParentActivity;
    private RecyclerView mRecipeProductsRecyclerView;
    private ArrayList<CartItem> mRecipeProductsList;
    private RecipeProductsAdapter mRecipeProductsAdapter;

    public RecipeAdapter(Context context, List<Recipe> cartItemProductList, CategoryWiseProductListActivity mParentActivity) {
        mContext = context;
        mRecipeList = cartItemProductList;
        this.mParentActivity = mParentActivity;
    }

    public class RecipeViewHolder  extends RecyclerView.ViewHolder {
        public TextView recipeName;
        public ImageView recipeThumbnail;


        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeName = (TextView)itemView.findViewById(R.id.recipe_name);
            recipeThumbnail = (ImageView)itemView.findViewById(R.id.recipe_thumbnail);
        }
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = mRecipeList.get(position);

        holder.recipeName.setText(recipe.getRecipe_name());
        Glide.with(mContext).load(recipe.getThumbnail()).into(holder.recipeThumbnail);
        //holder.recipeThumbnail.setImageBitmap(CircleTransform.getRoundedShape(CircleTransform.decodeFile(mContext,recipe.getThumbnail()),200));
        holder.recipeThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecipeProducts(recipe);
                mParentActivity.behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    private void getRecipeProducts(Recipe recipe) {
        mRecipeProductsRecyclerView = (RecyclerView)mParentActivity.findViewById(R.id.recipe_products_recycler_view);

        mRecipeProductsList = new ArrayList<>();
        prepareRecipies(recipe);

        mRecipeProductsAdapter = new RecipeProductsAdapter(mContext, mRecipeProductsList, GrossaryApplication.getInstance(),mParentActivity.getAdapter());

        RecyclerView.LayoutManager mRecipeProductLayoutManager = new GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false);
        mRecipeProductsRecyclerView.setLayoutManager(mRecipeProductLayoutManager);
        //mRecipeProductsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(mContext, 10), true));
        mRecipeProductsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecipeProductsRecyclerView.setAdapter(mRecipeProductsAdapter);
    }

    private void prepareRecipies(Recipe recipe) {
        RecipeLab r = RecipeLab.get(mContext, GrossaryApplication.getInstance().getRecipeList());
        mRecipeProductsList = r.getRecipeProductsList(mContext, recipe);
    }


}
