package org.nullhint.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class MyConcurrentHashMapTest {

    // TODO 多线程测试用例

    @Test
    public void testUse() throws Exception {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        map.put("str", "HelloWorld");
        assertEquals("HelloWorld", map.get("str"));
    }

}