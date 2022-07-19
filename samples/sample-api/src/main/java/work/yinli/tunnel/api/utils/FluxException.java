package work.yinli.tunnel.api.utils;

import lombok.Getter;

@Getter
public class FluxException extends RuntimeException {
    private final int code;
    private final String message;

    public FluxException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}