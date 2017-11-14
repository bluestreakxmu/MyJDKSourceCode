package org.nullhint.java.util;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MyHashMapTest {
    @Test
    public void testMyHashMap() throws Exception {
        Map<String, Object> map = new MyHashMap<String, Object>();
        String value = "null key";
        map.put(null, value);
        assertEquals(value, map.get(null));
        value = "Hello World!";
        map.put("str", value);
        assertEquals(value, map.get("str"));
    }

}