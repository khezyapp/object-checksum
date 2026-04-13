package io.github.khezyapp.checksum.util;

import java.net.URI;
import java.net.URL;
import java.time.temporal.Temporal;

public final class CheckTypes {

    private CheckTypes() {
    }

    public static boolean isSimpleType(final Class<?> type) {
        return type.isPrimitive()
                || type.equals(String.class)
                || Number.class.isAssignableFrom(type)
                || type.equals(Boolean.class)
                || type.equals(Character.class)
                || Temporal.class.isAssignableFrom(type)
                || Enum.class.isAssignableFrom(type)
                || URI.class.isAssignableFrom(type)
                || URL.class.isAssignableFrom(type);
    }
}
