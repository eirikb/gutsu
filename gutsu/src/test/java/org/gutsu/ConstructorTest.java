package org.gutsu;

import org.junit.Assert;
import org.junit.Test;

public class ConstructorTest {

    private static class Testing {
        public int x;

        public Testing(String first) {
            x = 42;
        }

        public Testing(int x, int y) {
            x = 1337;
        }
    }

    @Test
    public void testFirstConstructorIsUsed() throws Exception {
        Gutsu gutsu = new Gutsu();
        Testing testing = gutsu.getInstance(Testing.class);
        Assert.assertEquals(42, testing.x);
    }
}
