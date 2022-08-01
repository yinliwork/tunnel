package work.yinli.tunnel.core;

import lombok.Getter;
import work.yinli.tunnel.Tunnel;
import work.yinli.tunnel.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @author yangji
 */
@Getter
public class RequestFactory {
    private final Method method;
    private final String httpUrl;
    private final String httpPath;
    private final HttpMethod httpMethod;
    private final boolean hasBody;
    private final boolean isFormEncoded;
    private final boolean isMultipart;
    private final boolean isPostJson;
    private final ParameterHandler<?>[] parameterHandlers;

    public RequestFactory(Builder builder) {
        this.method = builder.method;
        this.httpUrl = builder.httpUrl;
        this.httpPath = builder.httpPath;
        this.httpMethod = builder.httpMethod;
        this.hasBody = builder.hasBody;
        this.isFormEncoded = builder.isFormEncoded;
        this.isMultipart = builder.isMultipart;
        this.isPostJson = builder.isPostJson;
        this.parameterHandlers = builder.parameterHandlers;
    }


    public static RequestFactory parseAnnotations(Object proxyApi, Method method) {
        return new Builder(proxyApi, method).build();
    }

    public static class Builder {
        private final Method method;
        private final Object proxyApi;

        private String httpUrl;
        private String httpPath;
        private HttpMethod httpMethod;
        private boolean hasBody;
        private boolean isFormEncoded;
        private boolean isMultipart;
        private boolean isPostJson;
        private ParameterHandler<?>[] parameterHandlers;

        public Builder(Object proxyApi, Method method) {
            this.proxyApi = proxyApi;
            this.method = method;
        }

        public RequestFactory build() {
            parseBaseUrl(proxyApi);
            parseAnnotationsPathAndHttpMethods(method);
            parseParameterHandler(method);
            return new RequestFactory(this);
        }

        private void parseParameterHandler(Method method) {
            final Parameter[] parameters = method.getParameters();
            final int length = parameters.length;
            parameterHandlers = new ParameterHandler[length];
            for (int i = 0; i < length; i++) {
                parameterHandlers[i] = parseParameter(parameters[i]);
            }
        }

        private ParameterHandler<?> parseParameter(Parameter parameter) {
            final Annotation[] annotations = parameter.getAnnotations();
            final Type parameterizedType = parameter.getParameterizedType();

            ParameterHandler<?> result = null;
            for (Annotation annotation : annotations) {
                ParameterHandler<?> handler = null;
                if (annotation instanceof Path) {
                    // 校验参数 parameterizedType 是否为正常的基本类型
                    handler = new ParameterHandler.Path<>(((Path) annotation).value());
                }
                if (annotation instanceof Query) {
                    // 校验参数 parameterizedType 是否为正常的基本类型
                    handler = new ParameterHandler.Query<>(((Query) annotation).value(), false);
                }
                if (annotation instanceof QueryMap) {
                    // 校验参数 parameterizedType 是否为Map 或者 Object 对象（不能是基本类型）
                    handler = new ParameterHandler.QueryMap<>(false);
                }
                if (annotation instanceof Form) {
                    // 校验参数 parameterizedType 基本类型
                    handler = new ParameterHandler.Form<>(((Form) annotation).value());
                }
                if (annotation instanceof FormMap) {
                    // 校验参数 parameterizedType Map 或者 Object对象（不能是基本类型）
                    handler = new ParameterHandler.FormMap<>();
                }
                if (annotation instanceof Body) {
                    // 校验参数 parameterizedType 是否为正常的基本类型
                    handler = new ParameterHandler.Body<>(((Body) annotation).value());
                }
                if (annotation instanceof BodyMap) {
                    // 校验参数 parameterizedType Map 或者 Object对象（不能是基本类型）
                    handler = new ParameterHandler.BodyMap<>();
                }
                if (handler != null && result != null) {
                    throw new RuntimeException("参数异常");
                }
                result = handler;
            }
            return result;
        }

        private void parseAnnotationsPathAndHttpMethods(Method method) {
            for (Annotation annotation : method.getAnnotations()) {
                parseHttpMethodAndPath(annotation);
            }
        }

        private void parseHttpMethodAndPath(Annotation annotation) {
            if (annotation instanceof Get) {
                parseHttpPath(HttpMethod.GET, ((Get) annotation).value(), false);
            } else if (annotation instanceof Post) {
                parseHttpPath(HttpMethod.POST, ((Post) annotation).value(), true);
            } else if (annotation instanceof FormUrlEncoded) {
                isFormEncoded = true;
            } else if (annotation instanceof FormMultipart) {
                isMultipart = true;
            } else if (annotation instanceof ApplicationJson) {
                isPostJson = true;
            }
        }

        private void parseHttpPath(HttpMethod methodEnum, String value, boolean hasBody) {
            this.httpPath = value;
            this.hasBody = hasBody;
            this.httpMethod = methodEnum;
        }

        private void parseBaseUrl(Object proxyApi) {
            BaseUrl url = ((Class<?>) (proxyApi.getClass().getAnnotatedInterfaces()[0].getType())).getAnnotation(BaseUrl.class);
            this.httpUrl = url.value();
        }
    }

}
