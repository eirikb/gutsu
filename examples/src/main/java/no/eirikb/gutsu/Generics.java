package no.eirikb.gutsu;

import com.google.common.reflect.TypeToken;

public class Generics {

    public static class Hello<T> {
        public T get(T t) {
            return t;
        }
    }

    public static void main(String[] args) {
        Gutsu gutsu = new Gutsu();
        Hello<String> hello = gutsu.getInstance(new TypeToken<Hello<String>>() {
        });

        System.out.println(hello.get("Hello, world!"));
        // Hello, world!
    }
}
