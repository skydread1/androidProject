package com.cpe.chat;

import java.util.ArrayList;
import java.util.List;

public enum MessageDAO {
    INSTANCE;


    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("id1", "nickname1", "messageContent1"));
        messages.add(new Message("id2", "nickname2", "messageContent2"));
        messages.add(new Message("id3", "nickname3", "messageContent3"));

        return messages;
    }
}
