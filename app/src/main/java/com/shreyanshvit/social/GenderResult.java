package com.shreyanshvit.social;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SJ on 07/09/2017.
 */

public class GenderResult {

    @SerializedName("male")
    @Expose
    private String male;
    @SerializedName("female")
    @Expose
    private String female;



    public String getmale() {
        return male;
    }


    public void setmale(String male) {
        this.male = male;
    }

    /**
     *
     * @return
     * The level
     */
    public String getfemale() {
        return female;
    }


    public void setfemale(String female) {
        this.female = female;
    }



}
