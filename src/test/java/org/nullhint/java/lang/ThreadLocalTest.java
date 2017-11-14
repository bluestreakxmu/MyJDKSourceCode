package org.nullhint.java.lang;

import static org.junit.Assert.*;

import org.junit.Test;

public class ThreadLocalTest {
    public ThreadLocal<String> str = new ThreadLocal<>();

    @Test
    public void testThreadLocal() throws Exception {
        str.set("str");
        assertEquals("str", str.get());

        new Thread(new Runnable() {
            @Override
            public void run() {
                assertNull(str.get());
            }
        }).start();
    }
}
