package work.yinli.tunnel;


import lombok.Data;

/**
 * @author yangji
 */
@Data
public class BaseResult<T> {

    private int code;

    private String msg;

    private T data;

}
