package io.proximax.model;

public enum PrivacyType {

    PLAIN(1001),
    NEMKEYS(1002),
    SHAMIR(1003),
    PASSWORD(1004);

    private final int value;

    private PrivacyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
