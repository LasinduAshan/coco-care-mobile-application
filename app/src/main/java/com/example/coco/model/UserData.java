package com.example.coco.model;

public class UserData {


    private String username;



    private String userphone;
    private String useremail;
    private String userPassword;
    private String role;
    private String userImage;
    private String key;


    public UserData() {

    }

    public UserData(String username, String userphone, String useremail, String userPassword, String role, String userImage, String key) {
        this.username = username;
        this.userphone = userphone;
        this.useremail = useremail;
        this.userPassword = userPassword;
        this.role = role;
        this.userImage = userImage;
        this.key = key;
    }

    public UserData(String username, String userphone, String useremail,String role, String userImage) {
        this.username = username;
        this.userphone = userphone;
        this.useremail = useremail;
        this.role = role;
        this.userImage = userImage;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
