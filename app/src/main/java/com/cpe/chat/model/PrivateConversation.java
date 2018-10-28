package com.cpe.chat.model;

import java.util.List;

public class PrivateConversation {
    private String user1;
    private String user2;
    private List<Message> messages;

    public PrivateConversation(String user1, String user2, List<Message> messages){
        this.user1 = user1;
        this.user2 = user2;
        this.messages = messages;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
