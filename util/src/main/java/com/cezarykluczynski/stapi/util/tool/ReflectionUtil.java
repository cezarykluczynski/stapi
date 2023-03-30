package com.cezarykluczynski.stapi.util.tool;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class ReflectionUtil {

	@SuppressWarnings("ThrowsCount")
	@SuppressFBWarnings("REFLF_REFLECTION_MAY_INCREASE_ACCESSIBILITY_OF_FIELD")
	public static <T> T getFieldValue(Class sourceClass, Object instance, String fieldName, Class<T> type)
			throws NoSuchFieldException, IllegalAccessException {
		Field field = sourceClass.getDeclaredField(fieldName);
		field.setAccessible(true);
		T value = (T) field.get(instance);
		return value;
	}

	@SuppressWarnings("ThrowsCount")
	public static <T> T getFieldValue(Field field, Object instance, Class<T> type) throws NoSuchFieldException, IllegalAccessException {
		field.setAccessible(true);
		T value = (T) field.get(instance);
		return value;
	}

	@SneakyThrows
	public static Field getAccessibleField(String fieldName, Class... classes) {
		Field field = null;
		for (Class clazz : classes) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
				// continue
			}
		}
		if (field == null) {
			throw new NoSuchFieldException(String.format("No such field: \"%s\".", fieldName));
		}

		field.setAccessible(true);
		return field;
	}

	public static Field[] getDeclaredAccessibleFields(Class clazz) {
		final Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
		}
		return declaredFields;
	}

	@SneakyThrows
	@SuppressFBWarnings("REFLF_REFLECTION_MAY_INCREASE_ACCESSIBILITY_OF_FIELD")
	public static void setFieldValue(Object subject, String fieldName, Object value) {
		Field field = subject.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(subject, value);
	}

}
