package com.shreyanshvit.social;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SJ on 07/09/2017.
 */

public class Result {

    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("product_cat")
    @Expose
    private String product_cat;
    @SerializedName("product_mrp")
    @Expose
    private String product_mrp;




    public String getproduct_id() {
        return product_id;
    }


    public void setproduct_id(String product_id) {
        this.product_id = product_id;
    }


    public String getproduct_name() {
        return product_name;
    }


    public void setproduct_name(String product_name) {
        this.product_name = product_name;
    }


    public String getproduct_cat() {
        return product_cat;
    }


    public void setproduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }

    public String getproduct_mrp() {
        return product_mrp;
    }


    public void setproduct_mrp(String product_mrp) {
        this.product_mrp = product_mrp;
    }


}
