package work.yinli.tunnel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于表示当前类、方法的接口使用的 url 地址
 * 三种情况
 * 1、当前类的所有接口
 * 2、当前方法的接口
 * 3、作用于参数 url 作为参数传递过来
 *
 * @author yangji
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseUrl {
    String value();
}

