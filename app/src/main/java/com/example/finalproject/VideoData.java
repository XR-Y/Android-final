package com.example.finalproject;

import com.google.gson.annotations.SerializedName;

public class VideoData{
    @SerializedName("_id")
    private String id;

    @SerializedName("feedurl")
    private String feedUrl;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("description")
    private String description;

    @SerializedName("likecount")
    private int likeCount;

    @SerializedName("avatar")
    private String avatar;

    private boolean isLiked = false;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @Override
    public String toString(){
        return "video:" +
                "id = " + id +
                "url = " + feedUrl +
                "nickname = " + nickname +
                "\n";
    }
}
