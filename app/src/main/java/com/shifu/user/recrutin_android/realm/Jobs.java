package com.shifu.user.recrutin_android.realm;

import org.apache.commons.lang3.builder.ToStringBuilder;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Jobs extends RealmObject {

    public static final String FIELD_ID = "uid";

    @PrimaryKey
    private String uid;

    private String title;
    private String description;
    private Long salary;
    private String company;
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

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
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

