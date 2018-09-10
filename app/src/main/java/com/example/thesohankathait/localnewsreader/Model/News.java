package com.example.thesohankathait.localnewsreader.Model;

public class News {
    private String reporter,
                    description,
                    title,
                    dateAndTime,
                    imageURl;

    public News() {
    }

    public String getReporter() {

        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public News(String reporter, String description, String title, String dateAndTime, String imageURl) {

        this.reporter = reporter;
        this.description = description;
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.imageURl = imageURl;
    }
}
