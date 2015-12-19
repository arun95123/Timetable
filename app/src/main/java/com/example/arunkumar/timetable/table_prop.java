package com.example.arunkumar.timetable;

public class table_prop {

    private String storetime;
    private String storedesc;
    private String _id;
    private String storeday;

    public table_prop(String storetime,String storedesc,String storeday){

        this.storedesc=storedesc;
        this.storetime=storetime;
        this.storeday=storeday;
    }

    public String getStoreday() {
        return storeday;
    }

    public String get_id() {
        return _id;
    }

    public String getStoretime() {
        return storetime;
    }

    public String getStoredesc() {
        return storedesc;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setStoreday(String storeday) {
        this.storeday = storeday;
    }

    public void setStoretime(String storetime) {
        this.storetime = storetime;
    }

    public void setStoredesc(String storedesc) {
        this.storedesc = storedesc;
    }
}
