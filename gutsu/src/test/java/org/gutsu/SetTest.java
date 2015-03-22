package org.gutsu;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Set;

public class SetTest {

    public interface Hi {
    }

    public static class Hello implements Hi {
    }

    public static class World implements Hi {
    }

    public static class App {
        public int size;

        public App(Set<Hi> his) {
            size = his.size();
        }
    }

    @Test
    public void testSet() throws Exception {
        Gutsu gutsu = new Gutsu();
        App app = gutsu.getInstance(App.class);
        Assert.assertEquals(2, app.size);
    }

    @Test
    public void testGetinstances() throws Exception {
        Gutsu gutsu = new Gutsu();
        gutsu.getInstance(App.class);
        Assert.assertEquals(2, gutsu.getInstances(Hi.class).size());
    }

    @Test
    public void testGetInstancesDirectly() throws Exception {
        Gutsu gutsu = new Gutsu();
        Set<Hi> instances = gutsu.getInstances(Hi.class);
        Assert.assertEquals(2, instances.size());
    }
}
