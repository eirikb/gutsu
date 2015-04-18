package no.eirikb.gutsu;

import junit.framework.Assert;
import org.junit.Test;

public class FactoryTest {

    public interface Greet {
        String test();
    }

    public class Hello implements Greet {

        @Override
        public String test() {
            return "Hello";
        }
    }

    public class World implements Greet {

        @Override
        public String test() {
            return "World";
        }
    }

    public interface Factory {
        Greet hello();

        Greet world();
    }

    @Test
    public void testFactoryClassBind() throws Exception {
        Gutsu gutsu = new Gutsu();
        Factory factory = gutsu.factory(Factory.class);
        gutsu.bind(factory.hello(), Hello.class);
        gutsu.bind(factory.world(), World.class);

        Factory f = gutsu.getInstance(Factory.class);
        Assert.assertEquals("Hello", f.hello().test());
        Assert.assertEquals("World", f.world().test());
    }
}
