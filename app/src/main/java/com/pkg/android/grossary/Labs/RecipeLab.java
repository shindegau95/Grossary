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

    public RecipeLab(Context context, List<Integer> recipeListString) {
        ArrayList<Recipe> allrecipeList;
        allrecipeList = CSVReader.readRecipeList(context);
        recipeList = new ArrayList<>();
        if(recipeListString!=null) {
            for (Recipe r : allrecipeList) {
                for (Integer recipeId : recipeListString) {
                    if (r.getRecipe_id() == recipeId) {
                        recipeList.add(r);
                        break;
                    }
                }
            }
        }
    }

    public static RecipeLab get(Context context, List<Integer> recipeListString) {
        if(sRecipeLab == null){
            sRecipeLab = new RecipeLab(context,recipeListString);
        }
        return sRecipeLab;
    }

    public ArrayList<CartItem> getRecipeProductsList(Context mContext, Recipe recipe) {
        ArrayList<CartItem> mRecipeProductsList = new ArrayList<>();

        for(CartItem ci : recipe.getIngredients()){
            int id = ci.getProduct().getProduct_id();
            switch(ci.getProduct().getCategory_id()){
                case 1: CerealLab c = CerealLab.get(mContext, false);
                    mRecipeProductsList.add(c.getCartItem(id));
                    break;
                case 2: DairyLab dr = DairyLab.get(mContext, false);
                    mRecipeProductsList.add(dr.getCartItem(id));
                    break;
                case 3: FruitsLab f = FruitsLab.get(mContext, false);
                    mRecipeProductsList.add(f.getCartItem(id));
                    break;
                case 4: VegetablesLab v = VegetablesLab.get(mContext, false);
                    mRecipeProductsList.add(v.getCartItem(id));
                    break;
                case 5: DryFruitsLab d = DryFruitsLab.get(mContext, false);
                    mRecipeProductsList.add(d.getCartItem(id));
                    break;
                case 6: OthersLab o = OthersLab.get(mContext, false);
                    mRecipeProductsList.add(o.getCartItem(id));
                    break;
            }
        }
        return mRecipeProductsList;
    }

    public static void makeListNull(){
        sRecipeLab = null;
    }
}
