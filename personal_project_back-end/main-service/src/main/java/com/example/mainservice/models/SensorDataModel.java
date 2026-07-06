package com.example.mainservice.models;

public class SensorDataModel {
    private double turbidity;
    private long timestamp;

    public SensorDataModel(double turbidity, long timestamp) {
        this.turbidity = turbidity;
        this.timestamp = timestamp;
    }

    public SensorDataModel(){}

    public double getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(double turbidity) {
        this.turbidity = turbidity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Turbidity: " + turbidity + ", Time: " + timestamp;
    }
}
