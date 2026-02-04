package io.github.khezyapp.checksum.util;

public final class CheckTypes {

    private CheckTypes() {
    }

    public static boolean isSimpleType(final Class<?> type) {
        return type.isPrimitive()
                || type.equals(String.class)
                || Number.class.isAssignableFrom(type)
                || type.equals(Boolean.class)
                || type.equals(Character.class);
    }
}
