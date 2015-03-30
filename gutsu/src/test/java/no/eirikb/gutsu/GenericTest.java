package no.eirikb.gutsu;

import com.google.common.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

public class GenericTest {

    public static class Hello<T> {
        public String test(T t) {
            return "hello " + t.toString();
        }
    }

    @Test
    public void testGeneric() throws Exception {
        Gutsu gutsu = new Gutsu();
        Hello<String> hello = gutsu.getInstance(new TypeToken<Hello<String>>() {
        });
        Assert.assertEquals("hello world", hello.test("world"));
    }

    @Test
    public void testGenericInstances() throws Exception {
        Gutsu gutsu = new Gutsu();
        Hello<String> hello = gutsu.getInstance(new TypeToken<Hello<String>>() {
        });
        Hello<Integer> ehlo = gutsu.getInstance(new TypeToken<Hello<Integer>>() {
        });
        Assert.assertNotSame(hello.hashCode(), ehlo.hashCode());
    }
}
