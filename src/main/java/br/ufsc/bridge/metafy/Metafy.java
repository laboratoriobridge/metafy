package br.ufsc.bridge.metafy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates {@link MetaBean} for the annotated class
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.CLASS)
public @interface Metafy {

}