package com.pkg.android.grossary.navigation.Retailer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pkg.android.grossary.Adapter.RetailExpandableAdapter;
import com.pkg.android.grossary.Adapter.RetailList;
import com.pkg.android.grossary.Adapter.RetailListParent;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.Labs.RetailLab;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.other.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by GAURAV on 30-01-2017.
 */
/*
    This is the HomePage kind of Activity
    Has access to all the categories
 */
public class RetailerHomeFragment extends Fragment{
    private RecyclerView mRecyclerView;

    public RetailerHomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_retailer_home, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GrossaryApplication g = (GrossaryApplication)getActivity().getApplicationContext();
        RetailExpandableAdapter retailExpandableAdapter = new RetailExpandableAdapter(getActivity(), generateRetailList(),g);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(retailExpandableAdapter);
        return rootView;
    }

    private ArrayList<RetailListParent> generateRetailList() {
        RetailLab retailLab = RetailLab.get(getContext());
        List<RetailListParent> retail = retailLab.getCartItemList();
        ArrayList<RetailListParent> parentObjects = new ArrayList<>();
        for (RetailListParent retailListParent : retail) {
            ArrayList<RetailList> childList = new ArrayList<>();
            //child list initialization
            childList.add(new RetailList(retailListParent.getActual_stock(), retailListParent.getProduct().getPrice()));
            retailListParent.setChildList(childList);
            parentObjects.add(retailListParent);
        }
        Collections.sort(parentObjects);
        return parentObjects;

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
