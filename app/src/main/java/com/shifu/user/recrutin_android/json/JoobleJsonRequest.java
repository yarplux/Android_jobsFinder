package com.shifu.user.recrutin_android.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class JoobleJsonRequest {

    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("radius")
    @Expose
    private String radius;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("page")
    @Expose
    private String page;

    public JoobleJsonRequest(String keywords, String location, String radius, String salary, String page) {
        super();
        this.keywords = keywords;
        this.location = location;
        this.radius = radius;
        this.salary = salary;
        this.page = page;
    }

    public JoobleJsonRequest(String keywords, String page) {
        super();
        this.keywords = keywords;
        this.page = page;
    }

    public JoobleJsonRequest(String keywords) {
        super();
        this.keywords = keywords;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("keywords", keywords)
                .append("location", location)
                .append("radius", radius)
                .append("salary", salary)
                .append("page", page)
                .toString();
    }

}