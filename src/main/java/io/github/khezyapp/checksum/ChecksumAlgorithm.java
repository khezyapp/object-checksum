package io.github.khezyapp.checksum;

/**
 * Defines the supported cryptographic hash functions for the library.
 * This enumeration ensures type-safe algorithm selection and maps each constant
 * to its standard Java Security provider name.
 */
public enum ChecksumAlgorithm {
    /**
     * Represents the Secure Hash Algorithm 2, providing a 256-bit digest.
     * It offers a balance between security and performance for most object integrity checks.
     */
    SHA_256("SHA-256"),

    /**
     * Represents the 512-bit variant of the SHA-2 family.
     * Used for high-collision resistance and maximum security requirements.
     */
    SHA_512("SHA-512"),

    /**
     * Represents the Message-Digest Algorithm 5.
     * Suitable for fast, non-cryptographic data verification where performance is prioritized over security.
     */
    MD5("MD5");

    private final String value;

    ChecksumAlgorithm(final String value) {
        this.value = value;
    }

    /**
     * Retrieves the standard string name of the algorithm as recognized by the MessageDigest API.
     * * @return The string name (e.g., "SHA-256").
     */
    public String getValue() {
        return value;
    }
}
