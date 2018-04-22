package com.example.prashant_admin.raspnotifier;

/**
 * Created by prashant-admin on 20/12/17.
 */

public class TemperatureData {

    private String id;
    private String temperature;
    private String message;
    private String date;
    private String key;

    public TemperatureData() {
    }

    public TemperatureData(String temperature, String message, String date, String key) {
        this.temperature = temperature;
        this.message = message;
        this.date = date;
        this.key = key;

    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
