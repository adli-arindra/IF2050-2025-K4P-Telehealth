package org.drpl.telefe.dto;

import org.drpl.telefe.domain.User;

public class LoginResponse {
    private User user;
    private UserType userType;

    public LoginResponse(User user, UserType userType) {
        this.user = user;
        this.userType = userType;
    }

    public User getUser() {
        return user;
    }

    public UserType getUserType() {
        return userType;
    }
}
