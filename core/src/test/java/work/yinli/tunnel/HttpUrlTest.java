package work.yinli.tunnel;

import com.sun.istack.internal.Nullable;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUrlTest {

    private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
    private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")}");

    void testPath(String value) {
        // Get the relative URL path and existing query string, if present.
        int question = value.indexOf('?');
        if (question != -1 && question < value.length() - 1) {
            // Ensure the query string does not have any named parameters.
            String queryParams = value.substring(question + 1);
            Matcher queryParamMatcher = PARAM_URL_REGEX.matcher(queryParams);
            if (queryParamMatcher.find()) {
                throw methodError(null, "URL query string \"%s\" must not have replace block. "
                        + "For dynamic query parameters use @Query.", queryParams);
            }
        }
    }

    @Test
    public void testPath() {
        testPath("/path?id=%s");
    }

    static RuntimeException methodError(Method method, String message, Object... args) {
        return methodError(method, null, message, args);
    }

    static RuntimeException methodError(Method method, @Nullable Throwable cause, String message,
                                        Object... args) {
        message = String.format(message, args);
        return new IllegalArgumentException(message
                + "\n    for method "
                + method.getDeclaringClass().getSimpleName()
                + "."
                + method.getName(), cause);
    }
}
