package org.gutsu;

import com.google.common.reflect.TypeToken;
import org.reflections.Reflections;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Gutsu {

    private final HashMap<String, Set<Object>> instanceMap;
    private final Reflections reflections;
    private final Map<Class, Class> mappings;

    public Gutsu() {
        reflections = new Reflections();
        mappings = new HashMap<>();
        instanceMap = new HashMap<>();
    }

    private <T> void addInstance(Class<T> clazz, Object instance) {
        String key = getName(TypeToken.of(clazz));
        Set<Object> instances = instanceMap.get(key);
        if (instances == null) {
            instances = new HashSet<>();
            instanceMap.put(key, instances);
        }
        instances.add(instance);
    }

    private <T> T create(Class<T> clazz) {
        try {

            if (clazz.isInterface()) {
                Class<T> mapping = mappings.get(clazz);
                if (mapping == null) {
                    throw new IllegalArgumentException("No binding for interface " + clazz.getSimpleName());
                }
                clazz = mapping;
            }

            Constructor<?>[] constructors = clazz.getConstructors();
            if (constructors.length == 0) {
                throw new IllegalArgumentException("No constructors for class " + clazz.getSimpleName());
            }
            Constructor<?> c = null;
            int count = Integer.MAX_VALUE;
            for (Constructor<?> constructor : constructors) {
                int pc = constructor.getParameterTypes().length;
                if (pc < count) {
                    count = pc;
                    c = constructor;
                }
            }
            Type[] types = c.getGenericParameterTypes();
            Object[] params = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                params[i] = getInstance(TypeToken.of(types[i]));
            }

            T t = (T) c.newInstance(params);
            addInstance(clazz, t);

            for (Class<?> aClass : clazz.getInterfaces()) {
                addInstance(aClass, t);
            }

            return t;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Set<T> getInstances(Class<T> clazz) {
        String key = getName(TypeToken.of(clazz));
        return getInstances(key, clazz);
    }

    public <T> Set<T> getInstances(TypeToken<T> typeToken) {
        String key = "java.util.Set<" + getName(typeToken) + ">";
        return (Set<T>) getInstances(key, typeToken.getRawType());
    }

    public <T> Set<T> getInstances(String key, Class<T> clazz) {
        Set<T> res = (Set<T>) instanceMap.get(key);
        if (res == null) {
            Set<Object> set = new HashSet<>();
            for (Class<?> aClass : reflections.getSubTypesOf(TypeToken.of(clazz).getRawType())) {
                set.add(getInstance(aClass));
            }
            instanceMap.put(key, set);
            return (Set<T>) set;
        }
        return (Set<T>) instanceMap.get(key);
    }


    public <T> T getInstance(Class<T> clazz) {
        String key = getName(TypeToken.of(clazz));
        Set<Object> instances = instanceMap.get(key);
        if (instances == null || instances.size() == 0) {
            return create(clazz);
        }
        return (T) instances.iterator().next();
    }

    private static String getName(TypeToken typeToken) {
        return "" + typeToken.getType();
    }

    public <T> T getInstance(TypeToken<T> typeToken) {
        String key = getName(typeToken);
        Set<Object> instances = instanceMap.get(key);
        if (instances == null || instances.size() == 0) {

            if (typeToken.getRawType() == Set.class) {
                Type genericType = ((ParameterizedTypeImpl) typeToken.getType()).getActualTypeArguments()[0];
                return (T) getInstances(TypeToken.of(genericType).getRawType());
            }

            return (T) create(typeToken.getRawType());
        }
        return (T) instances.iterator().next();
    }

    public <Interface, Implementation extends Interface> void bind(Class<Implementation> implementationClass, Class<Interface> interfaceClass) {
        instanceMap.remove(getName(TypeToken.of(interfaceClass)));
        mappings.put(interfaceClass, implementationClass);
    }

    public <T> void bind(Class<T> clazz, T instance) {
        addInstance(clazz, instance);
    }
}
