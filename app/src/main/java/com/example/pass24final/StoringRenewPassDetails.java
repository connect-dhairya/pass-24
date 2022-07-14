package com.example.pass24final;

public class StoringRenewPassDetails {
    String renewHome;
    int renew;
    String date;

    public StoringRenewPassDetails() {

    }

    public StoringRenewPassDetails(String renewHome, int renew, String date) {
        this.renewHome = renewHome;
        this.renew = renew;
        this.date = date;
    }

    public String getRenewHome() {
        return renewHome;
    }

    public void setRenewHome(String renewHome) {
        this.renewHome = renewHome;
    }

    public int getRenew() {
        return renew;
    }

    public void setRenew(int renew) {
        this.renew = renew;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
