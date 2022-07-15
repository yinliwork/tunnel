package work.yinli.tunnel.annotations;


import java.lang.annotation.*;

/**
 * 标识接口的参数,用于多个参数 Map<String,?> ? 为基本类型
 * 例如：?xx=xx&xx=xx
 *
 * @author yangji
 */
@Target(ElementType.PARAMETER)   // Formal parameter declaration
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryMap {
}

