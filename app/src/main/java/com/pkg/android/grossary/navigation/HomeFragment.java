package com.pkg.android.grossary.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pkg.android.grossary.CategoryWiseProductListActivity;
import com.pkg.android.grossary.R;

/**
 * Created by GAURAV on 30-01-2017.
 */

public class HomeFragment  extends Fragment{
    private ImageView mShoppingListImageView;
    private ImageView mCerealImageView;
    private ImageView mDairyImageView;
    private ImageView mFruitsImageView;
    private ImageView mVegetablesImageView;
    private ImageView mDryFruitsImageView;
    private ImageView mMiscellaneousImageView;

    private static final String TAG = "HomeFragment";



    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);



        mShoppingListImageView = (ImageView) rootView.findViewById(R.id.thumbnail_shopping_list);
        mShoppingListImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CategoryWiseProductListActivity.newIntent(getActivity(), 1);
                startActivity(i);
            }
        });

        mCerealImageView = (ImageView) rootView.findViewById(R.id.thumbnail_cereal);
        mCerealImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CategoryWiseProductListActivity.newIntent(getActivity(), 1);
                startActivity(i);

            }
        });

        mDairyImageView = (ImageView) rootView.findViewById(R.id.thumbnail_dairy);
        mDairyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CategoryWiseProductListActivity.newIntent(getActivity(), 2);
                startActivity(i);
            }
        });

        mFruitsImageView = (ImageView) rootView.findViewById(R.id.thumbnail_fruits);
        mFruitsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CategoryWiseProductListActivity.newIntent(getActivity(), 3);
                startActivity(i);
                Log.d(TAG,"inside fruits");
            }
        });

        mVegetablesImageView = (ImageView) rootView.findViewById(R.id.thumbnail_vegetables);
        mVegetablesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CategoryWiseProductListActivity.newIntent(getActivity(), 4);
                startActivity(i);
            }
        });

        mDryFruitsImageView = (ImageView) rootView.findViewById(R.id.thumbnail_dryfruits);
        mDryFruitsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CategoryWiseProductListActivity.newIntent(getActivity(), 5);
                startActivity(i);
            }
        });

        mMiscellaneousImageView = (ImageView) rootView.findViewById(R.id.thumbnail_miscellaneous);
        mMiscellaneousImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CategoryWiseProductListActivity.newIntent(getActivity(), 6);
                startActivity(i);
            }
        });


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
