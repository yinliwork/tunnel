package work.yinli.tunnel.sample.common;

import lombok.Getter;

/**
 * @author yangji
 */
@Getter
public class BaseResult<T> {

    private int code;

    private String msg;

    private T data;

    public static <T> BaseResult<T> success(T data) {
        BaseResult<T> result = new BaseResult<>();
        result.data = data;
        result.code = StatusCodeEnum.SUCCESS.getCode();
        return result;
    }

    public static <T> BaseResult<T> error(StatusCodeEnum code) {
        BaseResult<T> result = new BaseResult<>();
        result.code = code.getCode();
        result.msg = code.getMsg();
        return result;
    }

    public static <T> BaseResult<T> error(StatusCodeEnum code, String msg) {
        BaseResult<T> result = new BaseResult<>();
        result.code = code.getCode();
        result.msg = msg;
        return result;
    }
}
