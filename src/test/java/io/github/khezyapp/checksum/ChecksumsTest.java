package io.github.khezyapp.checksum;

import io.github.khezyapp.checksum.model.Department;
import io.github.khezyapp.checksum.model.Organization;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ChecksumsTest {

    @Test
    void shouldHandleComplexMapWithCircularReference() {
        // Setup circular relationship: Org -> Map -> Dept -> Org
        final var org = new Organization("TechCorp");
        final var engineering = new Department("Engineering", org);
        org.addDept("ENG_01", engineering);

        // Execute
        final var hash1 = Checksums.sha256(org);

        // Verify consistency
        assertNotNull(hash1);
        assertEquals(hash1, Checksums.sha256(org), "Hash should be deterministic");
    }

    @Test
    void shouldProduceDifferentHashWhenMapContentChanges() {
        final var org = new Organization("TechCorp");
        final var initialHash = Checksums.sha256(org);

        // Modify Map content
        org.addDept("HR_01", new Department("HR", org));
        final var updatedHash = Checksums.sha256(org);

        assertNotEquals(initialHash, updatedHash, "Hash must change when Map entry is added");
    }

    @Test
    void shouldHandleMapWithNullKeysOrValuesGracefully() {
        final var org = new Organization("NullTest");
        org.addDept(null, null); // Should be skipped by Objects.nonNull logic

        assertDoesNotThrow(() -> Checksums.sha256(org));
    }

    @Test
    void shouldRunSuccessWithOtherAlgorithm() {
        final var org = new Organization("TechCorp", Organization.Status.ENABLED);
        final var engineering = new Department("Engineering", org);
        org.addDept("ENG_01", engineering);

        assertNotNull(Checksums.md5(org));
        assertNotNull(Checksums.sha512(org));
    }

    @Test
    void testMap() {
        final var map1 = new HashMap<String, Object>();
        map1.put("id", 1234);
        map1.put("name", "TechCorp");

        final var map2 = new LinkedHashMap<String, Object>();
        map2.put("name", "TechCorp");
        map2.put("id", 1234);

        assertEquals(Checksums.md5(map1), Checksums.md5(map2));
    }
}
