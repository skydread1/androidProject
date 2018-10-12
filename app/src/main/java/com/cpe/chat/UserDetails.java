package com.cpe.chat;

import android.text.Editable;

import java.util.Objects;

public class UserDetails {
    private String uid;
    private String nickname;

    public UserDetails(String uid, String nickname) {
        this.uid = uid;
        this.nickname = nickname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return Objects.equals(uid, that.uid) &&
                Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, nickname);
    }
}
