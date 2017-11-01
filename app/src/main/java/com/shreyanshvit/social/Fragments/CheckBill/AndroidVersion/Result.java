package com.shreyanshvit.social.Fragments.CheckBill.AndroidVersion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SJ on 07/09/2017.
 */

public class Result {

    @SerializedName("bill_no")
    @Expose
    private String bill_no;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("mobile_no")
    @Expose
    private String mobile;

    /**
     *
     * @return
     * The id
     */
    public String getbill_no() {
        return bill_no;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setbill_n0(String id) {
        this.bill_no = bill_no;
    }

    /**
     *
     * @return
     * The username
     */
    public String getname() {
        return name;
    }


    public void setname(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The level
     */
    public String getemail() {
        return email;
    }


    public void setmail(String email) {
        this.email = email;
    }

    public String getaddress() {
        return address;
    }


    public void seteaddress(String address) {
        this.address = address;
    }

    public String getmobile() {
        return mobile;
    }


    public void setmobile(String mobile) {
        this.mobile = mobile;
    }

}
