package org.gutsu;

import junit.framework.Assert;
import org.junit.Test;

public class Binding {

    public static interface World {
        int test();
    }

    public static class Hello implements World {
        @Override
        public int test() {
            return 42;
        }
    }

    public static class Leet implements World {
        @Override
        public int test() {
            return 1337;
        }
    }

    @Test
    public void testBind() throws Exception {
        Gutsu gutsu = new Gutsu();
        gutsu.bind(World.class).to(Hello.class);
        World world = gutsu.getInstance(World.class);
        Assert.assertEquals(42, world.test());
    }

    @Test
    public void testLeet() throws Exception {
        Gutsu gutsu = new Gutsu();
        gutsu.bind(World.class).to(Leet.class);
        World world = gutsu.getInstance(World.class);
        Assert.assertEquals(1337, world.test());
    }
}
