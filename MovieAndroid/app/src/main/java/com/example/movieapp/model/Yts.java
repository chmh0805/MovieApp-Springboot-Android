package com.example.movieapp.model;

public class Yts {
    private String status;
    private String status_message;
    private Data data;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus_message() {
        return status_message;
    }
    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Yts [status=" + status + ", status_message=" + status_message + ", data=" + data + "]";
    }
}
