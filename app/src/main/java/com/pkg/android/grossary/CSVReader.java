package com.pkg.android.grossary;

import android.content.Context;

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
    public static ArrayList<Product> readCSV(Context context, int category){
        InputStream inputStream = context.getResources().openRawResource(R.raw.priceindex);

        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<Integer> index = new ArrayList<>();

        int count = 0;
        try {
            String[] csvLine = reader.readLine().split(",");

            for (String s : csvLine) {
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
            int linecount = 0;
            while ((line = reader.readLine()) != null) {
                if(linecount==0){
                    count = 0;
                    String[] rowelement = line.split(",");
                    for (int i:index){

                        ((Product)resultList.get(count)).setProduct_id(Integer.parseInt(rowelement[i]));
                        count++;
                    }
                }
                if(linecount==1){
                    count = 0;
                    String[] rowelement = line.split(",");
                    for (int i:index){

                        ((Product)resultList.get(count)).setProduct_name(rowelement[i]);
                        ((Product)resultList.get(count)).setThumbnail(context.getResources().getIdentifier(rowelement[i].toLowerCase().replace(" ",""),"raw",context.getPackageName()));
                        count++;
                    }
                }
                if(linecount==2){
                    count = 0;
                    String[] rowelement = line.split(",");
                    for (int i:index){

                        ((Product)resultList.get(count)).setProduct_unit(rowelement[i]);
                        count++;
                    }
                }
                if(linecount==3){
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
