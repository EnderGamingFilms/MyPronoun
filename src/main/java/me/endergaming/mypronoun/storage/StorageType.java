package me.endergaming.mypronoun.storage;

public enum StorageType {
    MYSQL("MYSQL"),
    SQLITE("SQLITE");

    private final String value;

    private StorageType(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static boolean isValid(final String value) {
        try {
            StorageType.valueOf(value);
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }
}
