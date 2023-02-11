package com.appstacks.indiannaukribazaar.profile;

public class Feedback {

    private String rating;
    private String date;
    private String jobtitle;
    private String feedback;
    private String totalprice;
    private String perhourprice;
    private String totalhours;

    public Feedback() {
    }

    public Feedback(String rating, String date, String jobtitle, String feedback, String totalprice, String perhourprice, String totalhours) {
        this.rating = rating;
        this.date = date;
        this.jobtitle = jobtitle;
        this.feedback = feedback;
        this.totalprice = totalprice;
        this.perhourprice = perhourprice;
        this.totalhours = totalhours;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPerhourprice() {
        return perhourprice;
    }

    public void setPerhourprice(String perhourprice) {
        this.perhourprice = perhourprice;
    }

    public String getTotalhours() {
        return totalhours;
    }

    public void setTotalhours(String totalhours) {
        this.totalhours = totalhours;
    }
}
