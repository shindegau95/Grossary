package com.pkg.android.grossary.other;

import android.content.Context;
import android.util.Log;

import com.pkg.android.grossary.Adapter.RetailList;
import com.pkg.android.grossary.Adapter.RetailListParent;
import com.pkg.android.grossary.R;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 11-02-2017.
 */

public class CSVReader {

    private static final String TAG = "CSVReader";

    public static ArrayList<RetailListParent> readProductQuantity(Context context, ArrayList<Product> productList){
        InputStream inputStream = context.getResources().openRawResource(R.raw.retailquantity);

        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String[] quantityList = reader.readLine().split(",");
            int index = 1;
            for(Product product : productList){
                RetailListParent retailItem =  new RetailListParent(product, Integer.parseInt(quantityList[index++]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (ArrayList<RetailListParent>) resultList;

    }

    public static ArrayList<RetailListParent> setProductQuantities(Context context, ArrayList<RetailListParent> resultList){
        InputStream inputStream = context.getResources().openRawResource(R.raw.priceindex);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int count;

        try {
            //read the first line and get the count of products
            //initially 51
            count = resultList.size();

            String line;
            int linecount = 0;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, String.valueOf(linecount));
                if(linecount==0){
                    String[] rowelement = line.split(",");
                    for (int i=1;i<count;i++){
                        Log.d(TAG, rowelement[i]);
                        ((RetailListParent)resultList.get(i-1)).setCartquantity(Integer.parseInt(rowelement[i]));

                    }
                }
                linecount++;
            }

        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }


        return (ArrayList<RetailListParent>) resultList;
    }

    public static ArrayList<Product> getAllProducts(Context context){
        InputStream inputStream = context.getResources().openRawResource(R.raw.priceindex);

        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int count;
        try {
            count = 0 ;
            String[] csvLine = reader.readLine().split(",");
            for (String s : csvLine) {
                count++;
            }
            for(int i=1;i<count;i++){
                resultList.add(new Product());
            }
            for(int i=1;i<count;i++){
                ((Product)resultList.get(i-1)).setCategory_id(Integer.parseInt(csvLine[i]));
            }
            String line;
            int linecount = 1;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, String.valueOf(linecount));
                if(linecount==1){
                    String[] rowelement = line.split(",");
                    for (int i=1;i<count;i++){
                        Log.d(TAG, rowelement[i]);
                        ((Product)resultList.get(i-1)).setProduct_id(Integer.parseInt(rowelement[i]));

                    }
                }
                if(linecount==2){
                    String[] rowelement = line.split(",");
                    for (int i=1;i<count;i++){

                        ((Product)resultList.get(i-1)).setProduct_name(rowelement[i]);
                        ((Product)resultList.get(i-1)).setThumbnail(context.getResources().getIdentifier(rowelement[i].toLowerCase().replace(" ",""),"raw",context.getPackageName()));

                    }
                }
                if(linecount==3){
                    String[] rowelement = line.split(",");
                    for (int i=1;i<count;i++){

                        ((Product)resultList.get(i-1)).setProduct_unit(rowelement[i]);

                    }
                }
                if(linecount==4){
                    String[] rowelement = line.split(",");
                    for (int i=1;i<count;i++){

                        ((Product)resultList.get(i-1)).setPrice(Integer.parseInt(rowelement[i]));

                    }

                }
                linecount++;
            }

        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }


        return (ArrayList<Product>) resultList;
    }

    public static ArrayList<Product> readProductList(Context context, int category){
        InputStream inputStream = context.getResources().openRawResource(R.raw.priceindex);

        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<Integer> index = new ArrayList<>();

        int count = 0;
        try {
            //string array for first line
            String[] csvLine = reader.readLine().split(",");

            //count the number of elements in that category
            for (String s : csvLine) {
                //read elements of first line
                //skip the title "category"
                if(count==0){
                    count++;
                    continue;
                }
                int i = Integer.parseInt(s);
                if (i == category) {

                    index.add(count);
                    resultList.add(new Product());
                }
                count++;
            }


            String line;
            int linecount = 1;
            while ((line = reader.readLine()) != null) {
                if(linecount==1){ //product id
                    count = 0;
                    String[] rowelement = line.split(",");
                    for (int i:index){

                        ((Product)resultList.get(count)).setProduct_id(Integer.parseInt(rowelement[i]));
                        ((Product)resultList.get(count)).setCategory_id(category);
                        count++;
                    }
                }
                if(linecount==2){//product name and image
                    count = 0;
                    String[] rowelement = line.split(",");
                    for (int i:index){

                        ((Product)resultList.get(count)).setProduct_name(rowelement[i]);
                        ((Product)resultList.get(count)).setThumbnail(context.getResources().getIdentifier(rowelement[i].toLowerCase().replace(" ",""),"raw",context.getPackageName()));
                        count++;
                    }
                }
                if(linecount==3){ //product unit
                    count = 0;
                    String[] rowelement = line.split(",");
                    for (int i:index){

                        ((Product)resultList.get(count)).setProduct_unit(rowelement[i]);
                        count++;
                    }
                }
                if(linecount==4){ //product price
                    count = 0;
                    String[] rowelement = line.split(",");
                    for (int i:index){

                        ((Product)resultList.get(count)).setPrice(Integer.parseInt(rowelement[i]));
                        count++;
                    }

                }
                linecount++;
            }

        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }


        return (ArrayList<Product>) resultList;

    }



}
