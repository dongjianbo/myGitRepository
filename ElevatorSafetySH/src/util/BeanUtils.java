package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {
	public static Field getDeclaredField(Object object, String propertyName)
			throws NoSuchFieldException {
		return getDeclaredField(object.getClass(), propertyName);
	}

	@SuppressWarnings("rawtypes")
	public static Field getDeclaredField(Class clazz, String propertyName)
			throws NoSuchFieldException {
		Class superClass = clazz;
		try {
			return superClass.getDeclaredField(propertyName);
		} catch (NoSuchFieldException localNoSuchFieldException) {
			do
				superClass = superClass.getSuperclass();

			while (superClass != Object.class);

			throw new NoSuchFieldException("No such field: " + clazz.getName()
					+ '.' + propertyName);
		}
	}

	public static Object forceGetProperty(Object object, String propertyName)
			throws NoSuchFieldException {
		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		field.setAccessible(accessible);
		return result;
	}

	public static void forceSetProperty(Object object, String propertyName,
			Object newValue) throws NoSuchFieldException {
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		field.setAccessible(accessible);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Field> getFieldsByType(Object object, Class type) {
		List list = new ArrayList();
		Field[] fields = object.getClass().getDeclaredFields();
		Field[] arrayOfField1 = fields;
		int i = 0;
		for (int j = arrayOfField1.length; i < j; ++i) {
			Field field = arrayOfField1[i];
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}

		}

		return list;
	}

	@SuppressWarnings("rawtypes")
	public static Class getPropertyType(Class type, String name)
			throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}
}