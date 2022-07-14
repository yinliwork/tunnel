package work.yinli.tunnel.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)   // Method declaration
@Retention(RetentionPolicy.RUNTIME)   // Annotations are to be recorded in the class file by the compiler and  retained by the VM at run time, so they may be read reflectively.
@Documented
public @interface Post {
    String value() default "";
}

