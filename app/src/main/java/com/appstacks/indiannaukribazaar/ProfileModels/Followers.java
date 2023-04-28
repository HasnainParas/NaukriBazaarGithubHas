package com.appstacks.indiannaukribazaar.ProfileModels;

public class Followers {

    private String followedById;

    public Followers() {
    }

    public Followers(String followedById) {
        this.followedById = followedById;
    }

    public String getFollowedById() {
        return followedById;
    }

    public void setFollowedById(String followedById) {
        this.followedById = followedById;
    }
}
