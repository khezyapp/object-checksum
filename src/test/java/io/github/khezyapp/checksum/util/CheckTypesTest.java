package io.github.khezyapp.checksum.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class CheckTypesTest {

    @Test
    void testSimpleType() {
        assertTrue(CheckTypes.isSimpleType(boolean.class));
        assertTrue(CheckTypes.isSimpleType(int.class));
        assertTrue(CheckTypes.isSimpleType(float.class));
        assertTrue(CheckTypes.isSimpleType(double.class));
        assertTrue(CheckTypes.isSimpleType(byte.class));
        assertTrue(CheckTypes.isSimpleType(char.class));
        assertTrue(CheckTypes.isSimpleType(String.class));

        assertTrue(CheckTypes.isSimpleType(Integer.class));
        assertTrue(CheckTypes.isSimpleType(Float.class));
        assertTrue(CheckTypes.isSimpleType(Double.class));
        assertTrue(CheckTypes.isSimpleType(Character.class));
        assertTrue(CheckTypes.isSimpleType(BigDecimal.class));
        assertTrue(CheckTypes.isSimpleType(BigInteger.class));
    }
}