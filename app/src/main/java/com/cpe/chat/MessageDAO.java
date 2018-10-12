package com.cpe.chat;

import java.util.ArrayList;
import java.util.List;

public enum MessageDAO {
    INSTANCE;

    public List<String> getAll() {
        List<String> messages = new ArrayList<>();
        messages.add("message 1");
        messages.add("message 2");
        messages.add("message 3");

        return messages;
    }
}
