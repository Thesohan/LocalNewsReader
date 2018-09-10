package com.example.thesohankathait.localnewsreader.Model;

public class User {
    public String name,email,photoUrl;
    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setUser(String name, String email, String imageUrl)
    {

        currentUser= new User(name,email,imageUrl);

    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User() {

    }

    public User(String name, String email, String photoUrl) {

        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
