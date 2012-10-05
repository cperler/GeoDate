package geodate.client.proxy;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Path {
	String methodName();
	boolean hasSimpleReturnType() default false;
	Class<?> type() default String.class;
}
