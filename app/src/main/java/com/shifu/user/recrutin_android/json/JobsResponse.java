package com.shifu.user.recrutin_android.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class JobsResponse {
    @SerializedName("number_of_jobs")
    @Expose
    private String number_of_jobs;
    @SerializedName("number_of_pages")
    @Expose
    private String number_of_pages;
    @SerializedName("jobs")
    @Expose
    private List<Job> jobs;

    public List<Job> getJobs (){
        return jobs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("number_of_jobs", number_of_jobs)
                .append("number_of_pages", number_of_pages)
                .append("jobs", jobs)
                .toString();
    }
}
