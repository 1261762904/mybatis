package orm.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Select {

	/**
	 * 要执行的 SELECT 语句
	 * 
	 * @return
	 */
	String value();

}
