package com.shifu.user.recrutin_android.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Job {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("updated")
    @Expose
    private String updated;

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }


    public String getSnippet() {
        return snippet;
    }


    public String getSalary() {
        return salary;
    }

    public String getSource() {
        return source;
    }


    public String getType() {
        return type;
    }


    public String getLink() {
        return link;
    }


    public String getCompany() {
        return company;
    }


    public long getId() {
        return id;
    }


    public String getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
				.append("title", title)
				.append("location", location)
				.append("snippet", snippet)
				.append("salary", salary)
				.append("source", source)
				.append("type", type)
				.append("link", link)
				.append("company", company)
				.append("id", id)
				.append("updated", updated)
                .toString();
    }

}