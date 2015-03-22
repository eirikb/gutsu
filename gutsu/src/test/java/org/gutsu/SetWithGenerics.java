package org.gutsu;

import com.google.common.reflect.TypeToken;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Set;

public class SetWithGenerics {

    public interface Model<T> {
        int test(T t);
    }

    public static class A implements Model<Integer> {
        @Override
        public int test(Integer t) {
            return 42 + t;
        }
    }

    public static class B implements Model<Integer> {
        @Override
        public int test(Integer t) {
            return 1337 + t;
        }
    }

    public static class App {
        public App(Data data, A a, B b) {
        }
    }

    public static class Data {
        private final Set<Model<Integer>> models;

        public Data(Set<Model<Integer>> models) {
            this.models = models;
        }

        public int test() {
            int total = 0;
            for (Model<Integer> model : models) {
                total += model.test(1);
            }
            return total;
        }
    }

    @Test
    public void testThroughConstructor() throws Exception {
        Gutsu gutsu = new Gutsu();
        gutsu.getInstance(App.class);
        Data data = gutsu.getInstance(Data.class);
        Assert.assertEquals(1381, data.test());
    }

    @Test
    public void testGetInstances() throws Exception {
        Gutsu gutsu = new Gutsu();
        gutsu.getInstance(App.class);
        int total = 0;
        for (Model<Integer> model : gutsu.getInstances(new TypeToken<Model<Integer>>() {
        })) {
            total += model.test(1);
        }
        Assert.assertEquals(1381, total);
    }
}
