package no.eirikb.gutsu;

import com.google.common.reflect.TypeToken;

import java.util.Set;

public class Models {

    public interface Model<T> {
        void update(T t);
    }

    public static class Person {
        public String name;
    }

    public static class PersonLabel implements Model<Person> {

        @Override
        public void update(Person person) {
            System.out.println("Set label to: " + person.name);
        }
    }

    public static class PersonButton implements Model<Person> {

        @Override
        public void update(Person person) {
            System.out.println("Set button to: " + person.name);
        }
    }

    public static class App {
        public App(DataHandler dataHandler, PersonLabel personLabel, PersonButton personButton) {
        }
    }

    public static class DataHandler {
        private final Set<Model<Person>> persons;

        public DataHandler(Set<Model<Person>> persons) {
            this.persons = persons;
        }

        public void update(Person person) {
            for (Model<Person> personModel : persons) {
                personModel.update(person);
            }
        }
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.name = "Hello, world!";

        Gutsu gutsu = new Gutsu();
        gutsu.getInstance(App.class);
        gutsu.getInstance(DataHandler.class).update(person);
        // Set label to: Hello, world!
        // Set button to: Hello, world!

        for (Model<Person> personModel : gutsu.getInstances(new TypeToken<Model<Person>>() {
        })) {
            personModel.update(person);
        }
        // Set label to: Hello, world!
        // Set button to: Hello, world!
    }
}
