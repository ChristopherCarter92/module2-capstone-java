package com.techelevator.tenmo.models;

public class ErrorDetails {

    private String message;
    private String timestamp;
    private int status;
    private String error;
    private String path;

    public ErrorDetails(String message, String timestamp, int status, String error, String path){
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
