package io.github.khezyapp.checksum.model;

import io.github.khezyapp.checksum.annotation.ExcludeFromChecksum;

/**
 * Child class to test combined field detection
 */
public class UserAccount extends BaseEntity {
    private String username = "khezy_user";

    @ExcludeFromChecksum
    private String internalToken = "secret_123";

    // Complex nested object
    private Profile profile = new Profile("Developer");

    public String getUsername() {
        return username;
    }

    public String getInternalToken() {
        return internalToken;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setInternalToken(final String internalToken) {
        this.internalToken = internalToken;
    }

    public void setProfile(final Profile profile) {
        this.profile = profile;
    }
}
