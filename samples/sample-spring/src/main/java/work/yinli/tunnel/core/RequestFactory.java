package work.yinli.tunnel.core;

import lombok.*;

import java.lang.reflect.Method;

/**
 * @author yangji
 */
@Getter
@AllArgsConstructor
public class RequestFactory {
    private final Method method;
    private final String httpUrl;
    private final String relativeUrl;
    private final HttpMethodEnum httpMethod;
    private final boolean hasBody;
    private final boolean isFormEncoded;
    private final boolean isMultipart;
}
