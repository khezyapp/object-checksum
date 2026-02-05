package io.github.khezyapp.checksum.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated field should be skipped during checksum generation.
 *
 * <p>Use this on fields that are volatile, transient, or otherwise not relevant
 * to the object's identity (e.g., caches, loggers, or metadata).</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcludeFromChecksum {
}
