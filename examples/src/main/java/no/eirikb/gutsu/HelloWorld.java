package no.eirikb.gutsu;

import org.gutsu.Gutsu;

public class HelloWorld {
    public static class Hello {
        public Hello(World world) {
            world.ping();
        }
    }

    public static class World {
        public void ping() {
            System.out.println("Hello, world!");
        }
    }

    public static void main(String[] args) {
        Gutsu gutsu = new Gutsu();
        gutsu.getInstance(Hello.class);
        // Hello, world!
    }
}
