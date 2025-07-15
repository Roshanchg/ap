package com.example.ap.handlers;

import com.example.ap.classes.User;
import com.example.ap.classes.enums.USERTYPE;

public class SessionHandler {

    private static SessionHandler instance;

    private int userId;
    private String userName;
    private USERTYPE userType;

    private SessionHandler() {
        // Private constructor to prevent instantiation

    }

    public static SessionHandler getInstance() {
        if (instance == null) {
            instance = new SessionHandler();
        }
        return instance;
    }

    public void startSession(int userId, String userName, USERTYPE userType) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
    }

    public void endSession() {
        this.userId = -1;
        this.userName = null;
        this.userType = null;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public USERTYPE getUserType() {
        return userType;
    }

    public boolean isActive() {
        return userId >= 0;
    }
}
