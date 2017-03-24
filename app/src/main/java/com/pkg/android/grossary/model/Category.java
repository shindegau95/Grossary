package com.pkg.android.grossary.model;

import android.content.Context;

import static java.security.AccessController.getContext;

/**
 * Created by GAURAV on 19-03-2017.
 */

public class Category {
    private int category_thumbnail;
    private String category_name;
    private int category_id;

    public Category(Context context, String category_name, String category_thumbnail_name, int category_id) {
        this.category_name = category_name;
        this.category_thumbnail = context.getResources().getIdentifier(category_thumbnail_name,"drawable",context.getPackageName());
        this.category_id = category_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getCategory_thumbnail() {
        return category_thumbnail;
    }

    public void setCategory_thumbnail(int category_thumbnail) {
        this.category_thumbnail = category_thumbnail;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
