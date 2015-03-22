package org.gutsu;

import com.google.common.reflect.TypeToken;
import junit.framework.Assert;
import org.junit.Test;

public class MultiGenerics {

    public static class Hello<A, B> {
        public String test(A a, B b) {
            return "" + a + " " + b;
        }
    }

    @Test
    public void testMultipleGenerics() throws Exception {
        Gutsu gutsu = new Gutsu();
        Hello<Integer, String> hello = gutsu.getInstance(new TypeToken<Hello<Integer, String>>() {
        });
        Assert.assertEquals("42 world", hello.test(42, "world"));
    }
}
