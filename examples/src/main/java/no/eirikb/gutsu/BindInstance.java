package no.eirikb.gutsu;

public class BindInstance {

    public interface Hello {
        void ping();
    }

    public static void main(String[] args) {
        Gutsu gutsu = new Gutsu();
        gutsu.bind(Hello.class, new Hello() {
            @Override
            public void ping() {
                System.out.println("Hello, world!");
            }
        });
        gutsu.getInstance(Hello.class).ping();
        // Hello, world!

        gutsu.bind(String.class, "Hello, world!");
        System.out.println(gutsu.getInstance(String.class));
        // Hello, world!
    }
}
