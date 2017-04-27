package com.pkg.android.grossary.navigation.Retailer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pkg.android.grossary.Adapter.retailer.RetailExpandableAdapter;
import com.pkg.android.grossary.model.RetailList;
import com.pkg.android.grossary.model.RetailListParent;
import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.Labs.RetailLab;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.other.CallServer;
import com.pkg.android.grossary.other.DividerItemDecoration;
import com.pkg.android.grossary.other.Session;

import java.util.ArrayList;
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
    private ProgressDialog progressDialog;
    public RetailExpandableAdapter retailExpandableAdapter;
    SwipeRefreshLayout swipeRefreshLayout;


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

        //new CallServer().updateStock(getActivity(), progressDialog,mRecyclerView);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        /*swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        refresh();
                                    }
                                }
        );
*/
        new LoadStock().execute();

        return rootView;
    }



    private void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        Session.removeCurrStockList(getContext());
        new LoadStock().execute();
    }

    public static ArrayList<RetailListParent> generateRetailList(Context context) {
        RetailLab retailLab = RetailLab.get(context);
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

    private class LoadStock extends AsyncTask<Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),
                    "Loading Retail Stock",
                    "Please Wait");

            if(Session.getCurrStockListString(getContext())==null || Session.getExpStockListString(getContext())==null){
                GrossaryApplication.getInstance().setLoading(true);
                CallServer.updateStock(getActivity());
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(GrossaryApplication.getInstance().isLoading()) {
                        Log.d("RetailHome","loading");
                    }
                    GrossaryApplication.getInstance().setCurrstocklist();
                    GrossaryApplication.getInstance().setExpstocklist();
                }
            }).start();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loadData(getContext(), mRecyclerView, progressDialog);
        }
    }

    public void loadData(Context context, final RecyclerView mRecyclerView, ProgressDialog progressDialog){
        if(retailExpandableAdapter==null){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        }
        retailExpandableAdapter = new RetailExpandableAdapter(context, RetailerHomeFragment.generateRetailList(context),GrossaryApplication.getInstance());

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setAdapter(retailExpandableAdapter);
            }
        });
        progressDialog.dismiss();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

}