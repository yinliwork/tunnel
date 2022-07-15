package work.yinli.tunnel.annotations;

import java.lang.annotation.*;

/**
 * 标识当前接口为 POST 请求方式
 *
 * @author yangji
 */
@Target(ElementType.METHOD)   // Method declaration
@Retention(RetentionPolicy.RUNTIME)
// Annotations are to be recorded in the class file by the compiler and  retained by the VM at run time, so they may be read reflectively.
@Documented
public @interface Post {
    String value();
}

