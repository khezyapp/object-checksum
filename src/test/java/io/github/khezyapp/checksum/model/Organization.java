package io.github.khezyapp.checksum.model;

import java.util.HashMap;
import java.util.Map;

public class Organization {
    private final String name;
    private final Map<String, Department> departments = new HashMap<>();

    public Organization(final String name) {
        this.name = name;
    }

    public void addDept(final String key,
                        final Department dept) {
        departments.put(key, dept);
    }

    public String getName() {
        return name;
    }

    public Map<String, Department> getDepartments() {
        return departments;
    }
}
