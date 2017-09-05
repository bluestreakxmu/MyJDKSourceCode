package lxb.java.util;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MyHashMapTest {
    @Test
    public void testMyHashMap() throws Exception {
        Map<String, Object> map = new MyHashMap<String, Object>();
        map.put("test", "Hello World!");
        assertEquals("Hello World!", map.get("test"));
    }

}