package com.pkg.android.grossary.navigation.Customer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.pkg.android.grossary.Adapter.CustomerCategoryProductAdapter;
import com.pkg.android.grossary.Adapter.ShoppingListAdapter;
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
import com.pkg.android.grossary.other.CallServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 31-01-2017.
 */

/*
    Provides interface to add or remove products into/from cart
 */
public class CategoryWiseProductListActivity extends AppCompatActivity {

    private static final String TAG = "cwpla";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<CartItem> mCartItemList;
    private FloatingActionButton cartfab;
    private ButtonBarLayout bottomButton;
    private ToggleButton selectButton;
    boolean noShoppingListItems = false;
    private static final String EXTRA_CHOICE = "com.pkg.android.grossary.choice";
    private List<Boolean> selectedList;

    //makes independent of caller activity
    public static Intent newIntent(Context packageContext, int category) {
        Intent i = new Intent(packageContext, CategoryWiseProductListActivity.class);
        i.putExtra(EXTRA_CHOICE, category);
        return i;
    }

    private int category;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //first try and get it in shared pref
        //so first get it into GrossaryApplication
        ((GrossaryApplication)getApplicationContext()).setShoppingListQuantities();
        if(((GrossaryApplication)getApplicationContext()).getShoppingListQuantities() == null){
            //if not there in pref, try to update the shoppinglist as it is
            CallServer.updateShoppingList(this);

            //write code here for showing async task
        }
        if(((GrossaryApplication)getApplicationContext()).getShoppingListQuantities() == null){
            //if there is no such entry available
            noShoppingListItems = true;
        }

        category = getIntent().getIntExtra(EXTRA_CHOICE, 1);
        if(category == 0 && noShoppingListItems){
            setContentView(R.layout.activity_no_items_in_shopping_list);

        }
        else
        {
            setContentView(R.layout.activity_categorywise_productlist);

            //wiring

            bottomButton = (ButtonBarLayout)findViewById(R.id.bottom_button);
            selectButton = (ToggleButton) findViewById(R.id.selectButton);
            cartfab = (FloatingActionButton)findViewById(R.id.fab);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            initCollapsingToolbar();

            bottomButton.setVisibility(View.GONE);
            selectButton.setVisibility(View.GONE);
            //hides the select Button by default

            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            mCartItemList = new ArrayList<>();
            prepareProducts();
            selectedList = new ArrayList<>();
            prepareSelectedList();

            final GrossaryApplication shoppingCart = (GrossaryApplication) getApplicationContext();
            if(category == 0) {
                mAdapter = new ShoppingListAdapter(this, mCartItemList, selectedList, shoppingCart);
                bottomButton.setVisibility(View.VISIBLE);
                selectButton.setVisibility(View.VISIBLE);
                ShoppingListLab s = ShoppingListLab.get(getApplicationContext());
                selectButton.setChecked(s.isAllSelected());
                selectButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        ShoppingListLab s = ShoppingListLab.get(getApplicationContext());
                        if(checked){
                            s.EnableAllItems();
                            for(CartItem ci: s.getCartItemList()){
                                ShoppingListAdapter.setLabQuantity(ci, ci.getCartItem().getProduct_id(), ci.getCartquantity(), getApplicationContext());
                            }
                            mAdapter.notifyDataSetChanged();
                        }else{
                            s.DisableAllItems();
                            for(CartItem ci: s.getCartItemList()){
                                ShoppingListAdapter.setLabQuantity(ci, ci.getCartItem().getProduct_id(), 0, getApplicationContext());
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
            else
                mAdapter = new CustomerCategoryProductAdapter(this, mCartItemList, shoppingCart);


            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);


            cartfab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(CategoryWiseProductListActivity.this, ViewCartActivity.class);
                    startActivity(i);
                }
            });



            try {
                changecoverimage();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void prepareSelectedList() {
        ShoppingListLab s = ShoppingListLab.get(this);
        selectedList = s.getSelectedList();
    }

    private void prepareProducts() {
        //get category wise product list
        boolean noItems = false;

        GrossaryApplication grossaryApplication = (GrossaryApplication)getApplicationContext();
        if(grossaryApplication.getCart().size() == 0){
            noItems = true;
        }


        if(category==1) {
            CerealLab c = CerealLab.get(this,noItems);
            mCartItemList = c.getCartItemList();
        }
        else if(category==2) {
            DairyLab d = DairyLab.get(this,noItems);
            mCartItemList = d.getCartItemList();
        }
        else if(category==3) {
            FruitsLab f = FruitsLab.get(this,noItems);
            mCartItemList = f.getCartItemList();
        }
        else if(category==4) {
            VegetablesLab v = VegetablesLab.get(this,noItems);
            mCartItemList = v.getCartItemList();
        }
        else if(category==5) {
            DryFruitsLab d = DryFruitsLab.get(this,noItems);
            mCartItemList = d.getCartItemList();
        }
        else if(category==6) {
            OthersLab o = OthersLab.get(this,noItems);
            mCartItemList = o.getCartItemList();
        }
        else if(category==0) {

            if (!noShoppingListItems){
                ShoppingListLab s = ShoppingListLab.get(this);
                mCartItemList = s.getCartItemList();
            }

        }
        /*else {
            CategoryWiseProductLab c = CategoryWiseProductLab.get(this, category);

            for (int i = 0; i < c.getProductList().size(); i++) {
                Product p = c.getProductList().get(i);
                CartItem ci = new CartItem(p);
                mCartItemList.add(ci);
            }


            Log.d(TAG, String.valueOf("22" + mCartItemList.get(0).getCartItem()));

        p = new Product();
        p.setProduct_name("Chana Dal");
        p.setPrice(128);
        p.setThumbnail(covers[1]);
        mProductList.add(p);

        p = new Product();
        p.setProduct_name("Rice");
        p.setPrice(80);
        p.setThumbnail(covers[2]);
        mProductList.add(p);


        Log.d(TAG, "Category = " + category + mProductList.get(0).getThumbnail());*/
    }


    /************************APPEARENCE SETTINGS***********************************/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        changetoolbarcolor(category,collapsingToolbar);

        TextView backdroptitle = (TextView)findViewById(R.id.category_product_title);
        backdroptitle.setText("GROSSARY");
        final TextView backdropsubtitle = (TextView)findViewById(R.id.category_product_subtitle);


        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, final int verticalOffset) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if (scrollRange + verticalOffset == 0) {
                            changetoolbartitle(category,collapsingToolbar,backdropsubtitle);
                            isShow = true;
                        } else if (isShow) {
                            collapsingToolbar.setTitle(" ");
                            isShow = false;
                        }
                    }
                });

            }
        });
    }


    private void changecoverimage() {
        //sets coverimage for collapsible toolbar
        switch (category){
            case 0:Glide.with(this).load(R.drawable.grocery_splash_screen).into((ImageView) findViewById(R.id.backdrop));
                break;
            case 1:Glide.with(this).load(R.drawable.cover_cereal).into((ImageView) findViewById(R.id.backdrop));
                break;
            case 2:Glide.with(this).load(R.drawable.cover_dairy).into((ImageView) findViewById(R.id.backdrop));
                break;
            case 3:Glide.with(this).load(R.drawable.cover_fruits).into((ImageView) findViewById(R.id.backdrop));
                break;
            case 4:Glide.with(this).load(R.drawable.cover_vegetables).into((ImageView) findViewById(R.id.backdrop));
                break;
            case 5:Glide.with(this).load(R.drawable.cover_dryfruits).into((ImageView) findViewById(R.id.backdrop));
                break;
            case 6:Glide.with(this).load(R.drawable.cover_others).into((ImageView) findViewById(R.id.backdrop));
                break;
        }
    }
    private void changetoolbartitle(int category, CollapsingToolbarLayout collapsingToolbar, TextView backdropsubtitle) {
        //changing toolbar title
        switch (category){
            case 0:collapsingToolbar.setTitle("Shopping List");
                backdropsubtitle.setText("Shopping List");
                break;
            case 1:collapsingToolbar.setTitle("Cereals");
                backdropsubtitle.setText("Cereals");
                break;
            case 2:collapsingToolbar.setTitle("Dairy");
                backdropsubtitle.setText("Dairy");
                break;
            case 3:collapsingToolbar.setTitle("Fruits");
                backdropsubtitle.setText("Fruits");
                break;
            case 4:collapsingToolbar.setTitle("Vegetables");
                backdropsubtitle.setText("Vegetables");
                break;
            case 5:collapsingToolbar.setTitle("Dry Fruits");
                backdropsubtitle.setText("Dry Fruits");
                break;
            case 6:collapsingToolbar.setTitle("Miscellaneous");
                backdropsubtitle.setText("Miscellaneous");
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changetoolbarcolor(int category, CollapsingToolbarLayout collapsingToolbar) {
        //change toolbar color
        switch (category){
            case 1:collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.amber));
                collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.amber));
                getWindow().setStatusBarColor(getResources().getColor(R.color.dark_amber));
                break;
            case 2:collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.orange));
                collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.orange));
                getWindow().setStatusBarColor(getResources().getColor(R.color.dark_orange));
                break;
            case 3:collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.violet));
                collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.violet));
                getWindow().setStatusBarColor(getResources().getColor(R.color.dark_violet));
                break;
            case 4:collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.green));
                collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.green));
                getWindow().setStatusBarColor(getResources().getColor(R.color.dark_green));
                break;
            case 5:collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.brown));
                collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.brown));
                getWindow().setStatusBarColor(getResources().getColor(R.color.dark_brown));
                break;
            case 6:collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.red));
                collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.red));
                getWindow().setStatusBarColor(getResources().getColor(R.color.dark_red));
                break;
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        //adjusting the spacing between cards

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        //converting dp to pixel
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
