package org.drpl.telefe;

import org.drpl.telefe.domain.User;
import org.drpl.telefe.dto.ChatSessionResponse;
import org.drpl.telefe.dto.UserType;

public class Model {
    private User currentUser;
    private ChatSessionResponse chatSession;
    private UserType userType;

    public Model() {}

    public void clear() {
        currentUser = null;
        chatSession = null;
        userType = null;
    }

    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User newUser) { this.currentUser = newUser; }

    public ChatSessionResponse getChatSession() { return chatSession; }
    public void setChatSession(ChatSessionResponse newChatSession) { this.chatSession = newChatSession; }

    public UserType getUserType() { return userType; }
    public void setUserType(UserType newUserType) { this.userType = newUserType; }
}
