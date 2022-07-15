package work.yinli.tunnel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识 multipart/form-data 和 application/x-www-form-urlencoded 的内容
 * 不能和 FormMap 同时使用
 *
 * @author yangji
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Form {
    String value();
}

