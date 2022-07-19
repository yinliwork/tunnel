package work.yinli.tunnel.api.common;

import lombok.Getter;

@Getter
public enum StatusCodeEnum {
    /**/
    SUCCESS(0, "成功"),
    FAILED(1, "失败"),
    ERROR(2, "异常");

    private final int code;
    private final String msg;

    StatusCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
