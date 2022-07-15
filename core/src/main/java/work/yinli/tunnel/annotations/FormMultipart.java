package work.yinli.tunnel.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识 multipart/form-data 和 application/x-www-form-urlencoded 的接口（推荐有文件的时候使用）
 *
 * @author yangji
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormMultipart {
}
