package io.github.khezyapp.checksum.util;

import java.util.HexFormat;

public final class Formats {

    private Formats() {
    }

    public static String bytesToHex(final byte[] hash) {
        final var hexFormat = HexFormat.of();
        return hexFormat.formatHex(hash);
    }
}
