package com.xf.psychology.entity;


public class User {

    private int id;
    private String userAccount;
    private String userPassword;
    private String userName;
    private int userType;
    private int userState;
    private int userDel;


    public User() {
    }

    public User(int id, String userAccount, String userPassword, String userName, int userType, int userState, int userDel) {
        this.id = id;
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userType = userType;
        this.userState = userState;
        this.userDel = userDel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public int getUserDel() {
        return userDel;
    }

    public void setUserDel(int userDel) {
        this.userDel = userDel;
    }
}



