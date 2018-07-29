package com.shifu.user.recrutin_android.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Job {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("data_from")
    @Expose
    private String data_from;

    public String getTitle() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getAbout() {
        return about;
    }

    public String getSalary() {
        return salary;
    }

    public String getUrl() {
        return url;
    }

    public String getCompany() {
        return company;
    }


    public String getData_from() {
        return data_from;
    }


    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
				.append("title", name)
				.append("location", location)
				.append("about", about)
				.append("salary", salary)
				.append("url", url)
				.append("company", company)
				.append("data_from", data_from)
				.append("date", date)
                .toString();
    }

}