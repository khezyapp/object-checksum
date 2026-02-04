package io.github.khezyapp.checksum;

import io.github.khezyapp.checksum.annotation.ExcludeFromChecksum;
import io.github.khezyapp.checksum.util.CheckTypes;
import io.github.khezyapp.checksum.util.Formats;
import io.github.khezyapp.checksum.util.Reflections;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

/**
 * A utility class that serves as the primary entry point for generating cryptographic hashes of Java objects.
 * It provides a high-level API to compute consistent digital fingerprints for complex data structures.
 */
public final class Checksums {

    private Checksums() {
    }

    /**
     * Calculates a SHA-256 checksum for the provided object by recursively hashing its field values and structure.
     *
     * @param obj The object instance to be hashed.
     * @return A hexadecimal string representing the SHA-256 message digest.
     * @throws RuntimeException if the checksum calculation fails due to algorithm unavailability or reflection errors.
     */
    public static String sha256(final Object obj) {
        return hash(obj, ChecksumAlgorithm.SHA_256);
    }

    /**
     * Calculates a high-security SHA-512 checksum for the provided object, suitable for use cases requiring
     * a larger hash bit-length.
     *
     * @param obj The object instance to be hashed.
     * @return A hexadecimal string representing the SHA-512 message digest.
     * @throws RuntimeException if the checksum calculation fails due to algorithm unavailability or reflection errors.
     */
    public static String sha512(final Object obj) {
        return hash(obj, ChecksumAlgorithm.SHA_512);
    }

    /**
     * Calculates an MD5 checksum for the provided object. This is faster than SHA algorithms but
     * should be used primarily for non-cryptographic integrity checks.
     *
     * @param obj The object instance to be hashed.
     * @return A hexadecimal string representing the MD5 message digest.
     * @throws RuntimeException if the checksum calculation fails.
     */
    public static String md5(final Object obj) {
        return hash(obj, ChecksumAlgorithm.MD5);
    }

    /**
     * An internal dispatcher that initializes the specified MessageDigest algorithm and manages
     * the object graph traversal state.
     *
     * @param obj       The root object to begin hashing.
     * @param algorithm The standard name of the hash algorithm (e.g., "SHA-256", "MD5").
     * @return The resulting hash encoded as a hexadecimal string.
     * @throws RuntimeException for any internal errors during the hashing process.
     */
    public static String hash(final Object obj,
                              final ChecksumAlgorithm algorithm) {
        try {
            final var digest = MessageDigest.getInstance(algorithm.getValue());
            // Use a Set backed by IdentityHashMap to track visited instances
            final var visited = Collections.newSetFromMap(new IdentityHashMap<>());

            updateDigest(digest, obj, visited);

            return Formats.bytesToHex(digest.digest());
        } catch (final Exception e) {
            throw new RuntimeException("Checksum calculation failed", e);
        }
    }

    private static void updateDigest(final MessageDigest digest,
                                     final Object obj,
                                     final Set<Object> visited) throws IllegalAccessException {
        if (obj == null) {
            return;
        }

        // 1. Check for Circular References
        if (!CheckTypes.isSimpleType(obj.getClass())) {
            if (visited.contains(obj)) {
                // Already processed this instance
                return;
            }
            visited.add(obj);
        }

        // 2. Handle Primitives/Strings
        if (CheckTypes.isSimpleType(obj.getClass())) {
            digest.update(getByte(obj));
            return;
        }

        // 3. Handle Iterables (Lists/Sets)
        if (obj instanceof Iterable<?> iter) {
            for (final var item : iter) {
                updateDigest(digest, item, visited);
            }
            return;
        }

        if (obj instanceof Map<?, ?> map) {
            for (final var entry : map.entrySet()) {
                final var key = entry.getKey();
                final var value = entry.getValue();
                if (Objects.nonNull(value)) {
                    if (Objects.nonNull(key)) {
                        digest.update(getByte(key));
                    }
                    updateDigest(digest, value, visited);
                }
            }
            return;
        }

        // 4. Handle Complex Objects via Reflection
        final var fields = Reflections.getSortedFields(obj);
        for (final var field : fields) {
            // Skip fields with the @ExcludeFromChecksum annotation
            if (field.isAnnotationPresent(ExcludeFromChecksum.class)) {
                continue;
            }

            final var value = field.get(obj);

            digest.update(getByte(field.getName()));
            updateDigest(digest, value, visited);
        }
    }

    private static byte[] getByte(final Object value) {
        return value.toString().getBytes(StandardCharsets.UTF_8);
    }
}
