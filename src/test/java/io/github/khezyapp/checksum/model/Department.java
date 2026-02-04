package io.github.khezyapp.checksum.model;

/**
 * @param parentOrg Potential circular reference
 */
public record Department(String deptName, Organization parentOrg) {
}
