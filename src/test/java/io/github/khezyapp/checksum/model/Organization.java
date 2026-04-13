package io.github.khezyapp.checksum.model;

import java.util.HashMap;
import java.util.Map;

public class Organization {
    private final String name;
    private final Map<String, Department> departments = new HashMap<>();
    private Status status;

    public Organization(final String name) {
        this.name = name;
    }

    public Organization(final String name,
                        final Status status) {
        this.name = name;
        this.status = status;
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

    public enum Status {
        ENABLED("enabled"),
        DISABLED("disabled");

        private final String value;

        Status(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
