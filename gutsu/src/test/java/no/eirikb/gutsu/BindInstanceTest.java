package no.eirikb.gutsu;

import junit.framework.Assert;
import org.junit.Test;

public class BindInstanceTest {
    public interface Hello {
        int test();
    }

    @Test
    public void testBindInstance() throws Exception {
        Gutsu gutsu = new Gutsu();
        gutsu.bind(Hello.class, new Hello() {
            @Override
            public int test() {
                return 42;
            }
        });
        Assert.assertEquals(42, gutsu.getInstance(Hello.class).test());
    }

    @Test
    public void testBindString() throws Exception {
        Gutsu gutsu = new Gutsu();
        gutsu.bind(String.class, "Hello, world!");
        Assert.assertEquals("Hello, world!", gutsu.getInstance(String.class));
    }
}
