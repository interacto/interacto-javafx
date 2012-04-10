package test.org.malai;

import java.lang.reflect.Field;

public abstract class HelperTest {
	public static Field getField(Class<?> clazz, String name) throws SecurityException, NoSuchFieldException {
		Field field = clazz.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}
}
