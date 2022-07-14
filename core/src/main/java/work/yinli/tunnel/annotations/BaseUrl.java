package work.yinli.tunnel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)   // Class, interface (including annotation type), or enum declaration
@Retention(RetentionPolicy.RUNTIME)   // Annotations are to be recorded in the class file by the compiler and  retained by the VM at run time, so they may be read reflectively.
public @interface BaseUrl {
    String value();
}

