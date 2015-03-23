package org.gutsu;

import junit.framework.Assert;
import org.junit.Test;

public class SingleInstanceTest {

    public static class World {
        public int test;
    }

    public static class Hello {
        public int test;

        public Hello(World world) {
            this.test = world.test;
        }
    }

    @Test
    public void testSingleInstance() throws Exception {
        Gutsu gutsu = new Gutsu();
        World world = gutsu.getInstance(World.class);
        world.test = 42;
        Hello hello = gutsu.getInstance(Hello.class);
        Assert.assertEquals(42, hello.test);
    }
}
