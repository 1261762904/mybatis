package orm.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记执行 INSERT 语句的方法
 * 
 * @author wtao
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Insert {

	/**
	 * 要执行的 INSERT 语言
	 * 
	 * @return
	 */
	String value();

}
