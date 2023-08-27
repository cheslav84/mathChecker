package com.havryliuk.mathchecker.model.util.annotations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * ApplicationServiceContext instantiates Service classes and the fields of that class
 * which annotated with certain annotation.
 */
public class ApplicationProcessor {
    private static final Logger LOG = LogManager.getLogger(ApplicationProcessor.class);
    /**
     * Predicate that is checking whether the field is marked by @Autowired annotation.
     */
    private static final Predicate<Field> HAS_ANNOTATION =
            field -> field.getDeclaredAnnotation(Autowired.class) != null;

    /**
     * Method receives as parameter a class that implements Injectable interface, creates an instance of that class,
     * then gets declared fields of created class and instantiates that fields of class which
     * marked by certain annotation.
     *
     * @param clazz any class that should be instantiated as well as the fields of that class
     *              which is marked by @Autowired annotation.
     * @param <T>   any class that implements Injectable interface.
     * @return the instance of the class that implements Injectable interface.
     * @throws IllegalStateException if some Exception occurs during instantiation Service class.
     */
    @SuppressWarnings("unchecked")
    public static synchronized  <T extends Injectable> T getInstance(Class<? extends Injectable> clazz) {
        T object;
        try {
            Constructor<?> constructor = clazz.getConstructor();
            object = (T) constructor.newInstance();
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            String errorMessage = "Class doesn't have a constructor.";
            LOG.error(errorMessage);
            throw new IllegalStateException(errorMessage, e);
        }
        final Field[] declaredFields = clazz.getDeclaredFields();
        injectAnnotatedFields(object, declaredFields);
        return object;
    }

    /**
     * Method checks if any fields of object is annotated by certain annotation,
     * sets the proper accessibility of that fields, gets the instances of the fields
     * and sets the instance to every field that has annotation. In case of that instantiated
     * objects has its own fields with the proper annotation, the instantiation procedure
     * will be repeated with recursion.
     *
     * @param object         fields of which should be instantiated.
     * @param declaredFields an Array of declared fields of that object.
     */
    private static synchronized <T> void injectAnnotatedFields(T object, Field[] declaredFields) {
        Arrays.stream(declaredFields)
                .filter(HAS_ANNOTATION).toList()
                .forEach(field -> {
                    field.setAccessible(true);
                    Class<?> clazz = field.getType();
                    try {
                        Object annotatedObject = clazz.getConstructor().newInstance();
                        field.set(object, annotatedObject);
                        injectAnnotatedFields(object, clazz.getDeclaredFields());
                        LOG.debug("{} initialised.", annotatedObject.getClass());
                    } catch (InstantiationException |
                             IllegalAccessException |
                             InvocationTargetException |
                             NoSuchMethodException e) {
                        String errorMessage = "Field doesn't have a constructor.";
                        LOG.error(errorMessage);
                        throw new IllegalStateException(errorMessage, e);
                    }
                });
    }
}
