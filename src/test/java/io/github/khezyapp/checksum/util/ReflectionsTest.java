package io.github.khezyapp.checksum.util;

import io.github.khezyapp.checksum.model.UserAccount;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionsTest {

    @Test
    void testGetAllFields() {
        final var user = new UserAccount();
        final var userFields = Reflections.getFields(user);
        final var expectedSize = 5;
        assertEquals(expectedSize, userFields.size());
        assertTrue(userFields.parallelStream().anyMatch(f -> "id".equals(f.getName())));
        assertTrue(userFields.parallelStream().anyMatch(f -> "status".equals(f.getName())));
    }

    @Test
    void testGetSortedFields() {
        final var user = new UserAccount();
        final var userFields = Reflections.getSortedFields(user)
                .stream()
                .map(Field::getName)
                .toList();
        final var expected = List.of("id", "internalToken", "profile", "status", "username");
        assertEquals(expected, userFields);
    }
}
