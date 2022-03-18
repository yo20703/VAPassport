package com.example.vapassport.Register;

public class RegisterData {
    long id;
    String date = "";
    String time = "";
    String placeCode = "";
    boolean isCheck = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public RegisterData(long id, String date, String time, String placeCode){
        this.id = id;
        this.date = date;
        this.time = time;
        this.placeCode = placeCode;
    }
}
