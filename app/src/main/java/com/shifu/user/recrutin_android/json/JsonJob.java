package com.shifu.user.recrutin_android.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class JsonJob {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("salary")
    @Expose
    private String salary;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("url")
    @Expose
    private String url;

    public JsonJob(String title, String description, String salary, String company, String url) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.company = company;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("description", description)
                .append("salary", salary)
                .append("company", company)
                .append("url", url)
                .toString();
    }
}
