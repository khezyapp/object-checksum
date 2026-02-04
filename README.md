# Object Checksum Library

A lightweight, reflection-based Java library designed to generate deterministic cryptographic checksums 
for complex object graphs. It handles deep nesting, inheritance, circular references, 
and common collections like `List`, `Set`, and `Map`.

---

## Features
* **Deep Hashing:** Recursively inspects object fields.
* **Inheritance Support:** Captures fields from the entire class hierarchy.
* **Circular Reference Protection:** Uses identity tracking to prevent `StackOverflowError`.
* **Determinism:** Sorts fields and map keys to ensure consistent hashes across different JVM instances.
* **Annotation Support:** Exclude specific fields (like timestamps) using `@ExcludeFromChecksum`.

---

## Installation

### Maven
Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.khezyapp</groupId>
    <artifactId>object-checksum</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

Add this to your build.gradle:
```groovy
implementation 'io.github.khezyapp:object-checksum:1.0.0'
```

---

## Usage

### 1. Basic Checksum

Generate a SHA-256 hash for any POJO:
```java
User user = new User("Alice", "admin");
String hash = Checksums.sha256(user);
```

### 2. Using Different Algorithms

The library supports SHA-256, SHA-512, and MD5 via the `ChecksumAlgorithm` enum:
```java
String hash = Checksums.hash(user, ChecksumAlgorithm.SHA_512);
```

### 3. Excluding Fields

Use the `@ExcludeFromChecksum` annotation to ignore volatile data like IDs or timestamps:
```java
public class Product {
    private String name;
    private double price;

    @ExcludeFromChecksum
    private Instant lastUpdated; // This field will not affect the hash
}
```

### 4. Handling Complex Structures

The library automatically handles nested objects and collections:
```java
Map<String, List<Order>> data = new HashMap<>();
data.put("recent", Arrays.asList(new Order(1), new Order(2)));

String hash = Checksums.sha256(data);
```

---

## Library Architecture

The following flow illustrates how the library processes an object into a hash:

**Requirements**

- Java 17 or higher.
- No external dependencies (uses standard `java.security` and `java.lang.reflect`).

---

## License

This project is licensed under the MIT License - see the LICENSE file for details.
