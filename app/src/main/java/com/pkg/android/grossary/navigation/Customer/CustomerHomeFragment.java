package com.pkg.android.grossary.navigation.Customer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pkg.android.grossary.Adapter.Customer.CategoryAdapter;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.Category;

/**
 * Created by GAURAV on 30-01-2017.
 */
/*
    This is the HomePage kind of Activity
    Has access to all the categories
 */
public class CustomerHomeFragment extends Fragment{
    private ImageView mShoppingListImageView;
    private ImageView mCerealImageView;
    private ImageView mDairyImageView;
    private ImageView mFruitsImageView;
    private ImageView mVegetablesImageView;
    private ImageView mDryFruitsImageView;
    private ImageView mMiscellaneousImageView;

    private static final String TAG = "RetailerHomeFragment";

    //
    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //

    public CustomerHomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_home, container, false);


        // Get the widgets reference from XML layout
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // Initialize a new String array
        Category[] categories = {new Category(getContext(),"Shopping List","grocery_splash_screen",0),
                new Category(getContext(),"Cereals","cover_cereal",1),
                new Category(getContext(),"Dairy","cover_dairy",2),
                new Category(getContext(),"Fruits","cover_fruits",3),
                new Category(getContext(),"Veggies","cover_vegetables",4),
                new Category(getContext(),"Dry Fruits","cover_dryfruits",5),
                new Category(getContext(),"Others","cover_others",6),
        };
        // Define a layout for RecyclerView
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new CategoryAdapter(getContext(),categories);

        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        //



        return rootView;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}