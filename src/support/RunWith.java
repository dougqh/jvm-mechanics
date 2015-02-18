package support;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that specifies which JVM options are 
 * best to use for a code example.
 * @author dhawkins
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RunWith {
	public abstract int java() default 7;
	
	public abstract String[] value() default {};
}
