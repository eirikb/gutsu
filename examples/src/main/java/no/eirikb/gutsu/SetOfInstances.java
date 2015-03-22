package no.eirikb.gutsu;

import org.gutsu.Gutsu;

import java.util.Set;

public class SetOfInstances {

    public interface Hello {
    }

    public static class A implements Hello {
    }

    public static class B implements Hello {
    }

    public static void main(String[] args) {
        Gutsu gutsu = new Gutsu();
        Set<Hello> instances = gutsu.getInstances(Hello.class);
        System.out.println(instances.size());
        // 2
    }
}
