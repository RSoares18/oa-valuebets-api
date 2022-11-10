package com.oa.api.model;

import java.io.Serializable;
import java.util.List;

public class MultipleTestRequest implements Serializable {

    private String bookie;
    private String startDate;
    private String endDate;
    private List<TestRequest> requestList;

    public MultipleTestRequest(String bookie, String startDate, String endDate, List<TestRequest> requestList) {
        this.bookie = bookie;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestList = requestList;
    }

    public MultipleTestRequest() {
    }

    public String getBookie() {
        return bookie;
    }

    public void setBookie(String bookie) {
        this.bookie = bookie;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<TestRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<TestRequest> requestList) {
        this.requestList = requestList;
    }

    @Override
    public String toString() {
        return "MultipleTestRequest{" +
                "bookie='" + bookie + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", requestList=" + requestList +
                '}';
    }
}
