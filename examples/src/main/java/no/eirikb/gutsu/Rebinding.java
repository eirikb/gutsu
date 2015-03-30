package no.eirikb.gutsu;

public class Rebinding {

    public interface Hello {
        String ping();
    }

    public static class A implements Hello {

        @Override
        public String ping() {
            return "Hello, world!";
        }
    }

    public static class Derp implements Hello {

        @Override
        public String ping() {
            return "Derp";
        }
    }

    public static void main(String[] args) {
        Gutsu gutsu = new Gutsu();
        gutsu.bind(A.class, Hello.class);
        Hello hello = gutsu.getInstance(Hello.class);
        System.out.println(hello.ping());
        // Hello, world!

        gutsu.bind(Derp.class, Hello.class);
        hello = gutsu.getInstance(Hello.class);
        System.out.println(hello.ping());
        // Derp
    }
}
