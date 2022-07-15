package work.yinli.tunnel.annotations;


import java.lang.annotation.*;

/**
 * 标识 multipart/form-data 和 application/x-www-form-urlencoded 的接口（推荐无文件的时候使用）
 *
 * @author yangji
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormUrlEncoded {
}
