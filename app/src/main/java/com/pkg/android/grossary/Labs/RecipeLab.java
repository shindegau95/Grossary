package com.pkg.android.grossary.Labs;

import android.content.Context;

import com.pkg.android.grossary.Applications.GrossaryApplication;
import com.pkg.android.grossary.model.CartItem;
import com.pkg.android.grossary.model.Product;
import com.pkg.android.grossary.model.Recipe;
import com.pkg.android.grossary.other.CSVReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 28-03-2017.
 */

public class RecipeLab {

    private static RecipeLab sRecipeLab;
    private List<Recipe> recipeList;

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public RecipeLab(Context context) {
        recipeList = CSVReader.readRecipeList(context);
    }

    public static RecipeLab get(Context context) {
        if(sRecipeLab == null){
            sRecipeLab = new RecipeLab(context);
        }
        return sRecipeLab;
    }

}
