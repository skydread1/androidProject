package com.cpe.chat.model;

import java.util.Objects;

public class UserDetails {
    private final String email;
    private String uid;
    private String nickname;
    private String color;

    public UserDetails(String uid, String email, String nickname, String color) {
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
        this.color = color;
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

    public String getEmail() { return email; }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

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
