package io.github.khezyapp.checksum.util;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A utility class providing reflection-based operations to inspect object fields across the inheritance hierarchy.
 * This class is final and cannot be instantiated.
 */
public final class Reflections {

    private Reflections() {
    }

    /**
     * Retrieves a complete list of all declared fields from the provided object's class and all of its superclasses.
     * It ensures each field is made accessible via reflection before returning the collection.
     *
     * @param object The instance to inspect.
     * @return A {@code List<Field>} containing all fields found in the class hierarchy.
     * @throws NullPointerException if the input object is null.
     */
    public static List<Field> getFields(final Object object) {
        Objects.requireNonNull(object, "object must not be null");
        final var fields = new ArrayList<Field>();
        var clz = object.getClass();
        while (Objects.nonNull(clz)) {
            final var accessibleFields = Arrays.stream(clz.getDeclaredFields())
                    .peek(f -> f.setAccessible(true))
                    .toList();
            fields.addAll(accessibleFields);
            clz = clz.getSuperclass();
        }
        return fields;
    }

    /**
     * Retrieves all fields from the object's class hierarchy and sorts them alphabetically by field name.
     * This ensures a deterministic order of fields regardless of the JVM's internal declaration ordering.
     *
     * @param object The instance to inspect.
     * @return A {@code List<Field>} of all fields, sorted by name.
     */
    public static List<Field> getSortedFields(final Object object) {
        return getFields(object).stream()
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());
    }
}
