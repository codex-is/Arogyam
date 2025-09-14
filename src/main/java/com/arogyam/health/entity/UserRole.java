package com.arogyam.health.entity;

public enum UserRole {
    ASHA_WORKER("ASHA Worker"),
    CHW("Community Health Worker"),
    VOLUNTEER("Volunteer"),
    HEALTH_OFFICIAL("Health Official"),
    ADMIN("Administrator");

    private final String displayName;

    UserRole(String displayName){
        this.displayName = displayName;
    }
    public String getDisplayName(){
        return displayName;
    }
}
