package com.example.pass24final;

public class StoringPassDetails {


    String date,passtype,passengertype,source,destination,expiryDate;

    public StoringPassDetails() {
    }
    public StoringPassDetails(String date, String passtype, String passengertype, String source, String destination, String expiryDate) {
        this.date = date;
        this.passtype = passtype;
        this.passengertype = passengertype;
        this.source = source;
        this.destination = destination;
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() { return expiryDate; }

    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPasstype() {
        return passtype;
    }

    public void setPasstype(String passtype) {
        this.passtype = passtype;
    }

    public String getPassengertype() {
        return passengertype;
    }

    public void setPassengertype(String passengertype) {
        this.passengertype = passengertype;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}