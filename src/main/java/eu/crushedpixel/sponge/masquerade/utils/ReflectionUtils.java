package eu.crushedpixel.sponge.masquerade.utils;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

public class ReflectionUtils {

    /**
     * Returns a List containing all fields of a class and its superclasses.
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        return getAllFields(clazz, null);
    }

    private static List<Field> getAllFields(Class<?> clazz, Class<?> parentClass) {
        List<Field> currentClassFields = Lists.newArrayList(clazz.getDeclaredFields());
        Class<?> superclass = clazz.getSuperclass();

        if (superclass != null && (parentClass == null || !(superclass.equals(parentClass)))) {
            List<Field> parentClassFields = getAllFields(superclass, parentClass);
            currentClassFields.addAll(parentClassFields);
        }

        return currentClassFields;
    }



}
