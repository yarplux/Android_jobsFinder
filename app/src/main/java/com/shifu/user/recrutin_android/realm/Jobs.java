package com.shifu.user.recrutin_android.realm;

import org.apache.commons.lang3.builder.ToStringBuilder;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Jobs extends RealmObject {

    public static final String FIELD_ID = "uid";
    public static final String FIELD_FILTER = "search";

    @PrimaryKey
    private String uid;

    private String search;

    private String title;
    private String description;
    private String salary;
    private String company;
    private String updated;
    private String url;

    public String getUid() {
        return uid;
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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("search", search)
                .append("title", title)
                .append("description", description)
                .append("salary", salary)
                .append("company", company)
                .append("updated", updated)
                .append("url", url)
                .toString();
    }
}

