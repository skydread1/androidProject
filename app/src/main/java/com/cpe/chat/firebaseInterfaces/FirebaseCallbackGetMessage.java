package com.cpe.chat.firebaseInterfaces;

import com.cpe.chat.model.Message;

import java.util.List;

public interface FirebaseCallbackGetMessage {
    void onCallbackGetMessages(List<Message> messages);
}
