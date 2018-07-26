
package com.shifu.user.recrutin_android.json;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class JoobleJsonResponse {

    @SerializedName("totalCount")
    @Expose
    private int totalCount;
    @SerializedName("jobs")
    @Expose
    private List<Job> jobs = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public JoobleJsonResponse() {
    }

    /**
     *
     * @param jobs
     * @param totalCount
     */
    public JoobleJsonResponse(int totalCount, List<Job> jobs) {
        super();
        this.totalCount = totalCount;
        this.jobs = jobs;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("totalCount", totalCount).append("jobs", jobs).toString();
    }

}