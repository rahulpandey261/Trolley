package com.trolley.trolley.model;

/**
 * Created by mihir.shah on 9/29/2015.
 */
public class Category {

    String mName;

    String[] mProductList;

    public Category(String name, String[] productList) {
        mName = name;
        mProductList = productList;
    }

    public String getName() {
        return mName;
    }

    public String[] getProductList() {
        return mProductList;
    }
}
