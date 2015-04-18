package no.eirikb.gutsu;

public class Factory {
    public interface Greet {
        String greet();
    }

    public interface FamilyFactory {
        Greet dad();

        Greet mom();
    }

    public class Dad implements Greet {
        @Override
        public String greet() {
            return "Hi greet, I am dad";
        }
    }

    public class Mom implements Greet {

        @Override
        public String greet() {
            return "Hello";
        }
    }

    public static void main(String[] args) {
        Gutsu gutsu = new Gutsu();
        FamilyFactory familyFactory = gutsu.factory(FamilyFactory.class);
        gutsu.bind(familyFactory.dad(), Dad.class);
        gutsu.bind(familyFactory.mom(), Mom.class);

        FamilyFactory ff = gutsu.getInstance(FamilyFactory.class);
        System.out.println(ff.dad().greet());
        // Hi greet, I am dad
        System.out.println(ff.mom().greet());
        // Hello
    }
}
