package work.yinli.tunnel.core;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangji
 */
@Getter
public class Request {
    private final String url;
    private final String body;
    private final Map<String, String> formBody;
    private final HttpMethod httpMethod;
    private final ContentType contentType;

    public Request(RequestBuilder builder) {
        this.url = builder.url;
        this.body = builder.body;
        this.formBody = builder.formBody;
        this.httpMethod = builder.httpMethod;
        this.contentType = builder.contentType;
    }

    public static class RequestBuilder {
        private String url;
        private String body;
        private final Map<String, String> parameter;
        private final Map<String, String> paths;
        private final Map<String, String> formBody;
        private final HttpMethod httpMethod;
        private final ContentType contentType;

        public RequestBuilder(String url, HttpMethod httpMethod, ContentType contentType, boolean isForm, boolean isJson) {
            this.url = url;
            this.httpMethod = httpMethod;
            this.contentType = contentType;
            this.formBody = isForm ? new HashMap<>() : null;
            this.parameter = new HashMap<>();
            this.paths = new HashMap<>();
        }

        public void setPath(String key, String value) {
            this.paths.put(key, value);
        }

        public void addQueryParam(String key, String value, boolean encoded) {
            parameter.put(key, value);
        }

        public <T> void addBody(T value) {

        }

        public void setBody(String value) {
            this.body = value;
        }

        public <T> void addBody(String key, T value) {

        }

        public void addForm(String key, String value) {
            formBody.put(key, value);
        }

        Request build() {
            return new Request(this);
        }
    }

}
