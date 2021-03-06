package com.example.andrew.project.Model;

import java.io.Serializable;

public class Service implements Serializable {

    private String type;
    private String name;
    private double rate;

    public Service() {
        type = null;
        name = null;
        rate = 0.0;
    }

    public Service(String type, String name, double rate) {
        this.type = type;
        this.name = name;
        this.rate = rate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public boolean equals(Service other) {
        return getType() == other.getType() &&
                getName() == other.getName() &&
                getRate() == other.getRate();
    }
}
