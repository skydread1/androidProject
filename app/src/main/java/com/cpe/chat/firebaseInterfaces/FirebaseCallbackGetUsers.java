package com.cpe.chat.firebaseInterfaces;

import com.cpe.chat.model.UserDetails;

import java.util.List;

public interface FirebaseCallbackGetUsers {
    void onCallbackGetUsers(List<UserDetails> users);
}
