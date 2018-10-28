package com.cpe.chat.firebaseInterfaces;

import com.cpe.chat.model.Message;
import com.cpe.chat.model.PrivateConversation;

public interface FirebaseCallbackSaveConversation {
    void onCallbackSaveConversation(PrivateConversation conversation);
}
