package com.example.chattingapp2.model;

public class Users {

    //We can save these three variables
    private String id;
    private String username;
    private String imageURL;

    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    //Constructors
    public Users(){
    }

    public Users(String id, String username, String imageURL){
          this.id = id;
          this.username = username;
          this.imageURL = imageURL;
    }


}
