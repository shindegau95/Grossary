package com.pkg.android.grossary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAURAV on 28-03-2017.
 */

public class Recipe {
    private int recipe_id;
    private String recipe_name;
    private int thumbnail;
    private List<CartItem> ingredients;

    public Recipe() {
        ingredients = new ArrayList<>();
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<CartItem> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<CartItem> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return getRecipe_name();
    }
}
