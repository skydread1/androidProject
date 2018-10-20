package com.cpe.chat;

import java.util.List;

public interface FirebaseCallback {
    void onCallbackGetMessages(List<Message> messages);
}
