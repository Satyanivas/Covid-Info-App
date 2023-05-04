package com.nivas.covidtracker.model;

public class LocationStats {
    private String state;
    private String country;
    private int latest;

    private int diff;

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatest() {
        return latest;
    }

    public void setLatest(int latest) {
        this.latest = latest;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latest=" + latest +
                '}';
    }
}
