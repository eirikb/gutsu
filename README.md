# Gutsu

Dependency Injection in Java

Some key points:

  * Everything is a single instance.
  * Supports generics.
  * Only allow injection through constructor.


## Hello world

```Java
public class Hello {
    public Hello(World world) {
        world.ping();
    }
}

public class World {
    public void ping() {
        System.out.println("Hello, world!");
    }
}

public static void main(String[] args) {
    Gutsu gutsu = new Gutsu();
    gutsu.getInstance(Hello.class);
    // Hello, world!
}
```

## Single instance

```Java
public class A {
    public A(B b) {
        b.test = 42;
    }
}

public class B {
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
```

## Generics

```Java
public class Hello<T> {
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
```

## Set of instances
```Java
public interface Hello {
}

public class A implements Hello {
}

public class B implements Hello {
}

public static void main(String[] args) {
    Gutsu gutsu = new Gutsu();
    Set<Hello> instances = gutsu.getInstances(Hello.class);
    System.out.println(instances.size());
    // 2
}
```

## Binding
```Java
public interface Hello {
    String ping();
}

public class A implements Hello {

    @Override
    public String ping() {
        return "Hello, world!";
    }
}

public static void main(String[] args) {
    Gutsu gutsu = new Gutsu();
    gutsu.bind(A.class, Hello.class);
    Hello hello = gutsu.getInstance(Hello.class);
    System.out.println(hello.ping());
    // Hello, world!
}
```

### Rebinding

It is possible to change the binding run-time:

```Java

// ...
public class Derp implements Hello {

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
```

## Example with models

 Alternative approach to event system
 ```Java
public interface Model<T> {
    void update(T t);
}

public class Person {
    public String name;
}

public class PersonLabel implements Model<Person> {

    @Override
    public void update(Person person) {
        System.out.println("Set label to: " + person.name);
    }
}

public class PersonButton implements Model<Person> {

    @Override
    public void update(Person person) {
        System.out.println("Set button to: " + person.name);
    }
}

public class App {
    public App(PersonLabel personLabel, PersonButton personButton) {
    }
}

public void main(String[] args) {
    Person person = new Person();
    person.name = "Hello, world!";

    Gutsu gutsu = new Gutsu();
    gutsu.getInstance(App.class);

    for (Model<Person> personModel : gutsu.getInstances(new TypeToken<Model<Person>>() {
    })) {
        personModel.update(person);
    }
    // Set label to: Hello, world!
    // Set button to: Hello, world!
}
```

It is also possible to pass a `Set` as constructor:

```Java
public class DataHandler {
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
```
