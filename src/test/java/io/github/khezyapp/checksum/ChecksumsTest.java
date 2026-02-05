package io.github.khezyapp.checksum;

import io.github.khezyapp.checksum.model.Department;
import io.github.khezyapp.checksum.model.Organization;
import org.junit.jupiter.api.Test;

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
        final var org = new Organization("TechCorp");
        final var engineering = new Department("Engineering", org);
        org.addDept("ENG_01", engineering);

        assertNotNull(Checksums.md5(org));
        assertNotNull(Checksums.sha512(org));
    }
}
