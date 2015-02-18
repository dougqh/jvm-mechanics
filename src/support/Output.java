package support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that specifies the interesting bits 
 * of output to look for in the console log
 * (usually assumes proper use of flags specified 
 * by {@link RunWith}).
 * @author dhawkins
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Output {
  public abstract String[] highlight() default {};
  
  public abstract boolean less() default false;
}
