package no.eirikb.gutsu;

public class SingleInstance {

    public static class A {
        public A(B b) {
            b.test = 42;
        }
    }

    public static class B {
        public int test;
    }

    public static void main(String[] args) {
        Gutsu gutsu = new Gutsu();
        B b = gutsu.getInstance(B.class);
        b.test = 1337;
        gutsu.getInstance(A.class);
        System.out.println(b.test);
        // 42
    }
}
