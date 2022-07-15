package work.yinli.tunnel.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import work.yinli.tunnel.annotations.*;

import java.lang.reflect.*;

/**
 * @author yangji
 */
public class HttpProxy {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{tClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 走的是 InvocationHandler的对象 toString，原因是 this
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }

                String response = handleHttp(method, args);

                return handleResponse(response, method);
            }

            @Override
            public String toString() {
                return "234";
            }
        });
    }

    private static Object handleResponse(String response, Method method) {
        final Type returnType = method.getGenericReturnType();
        // 转换器
        return JSON.parseObject(response, returnType);
        /*
        // 注解返回值
        final Class<BaseServiceResult<?>> value = (Class<BaseServiceResult<?>>) result.value();
        // 回调内容
        final Type[] actualTypeArguments = ((ParameterizedTypeImpl) returnType).getActualTypeArguments();
        // 组合注解返回值<回调具体类容>
        final ParameterizedTypeImpl resultBody = ParameterizedTypeImpl.make(value, actualTypeArguments, null);
        final BaseServiceResult<?> serviceResult = JSON.parseObject(response, resultBody);
        return serviceResult.toBaseResult();*/
    }

    private static String handleHttp(Method method, Object[] args) {
        final Get getMethod = method.getAnnotation(Get.class);
        if (getMethod != null) {
            return handleHttpGet(getMethod.value(), method, args);
        }
        final Post postMethod = method.getAnnotation(Post.class);
        if (postMethod != null) {
            return handleHttpPost(postMethod.value(), method, args);
        }
        final PostForm postForm = method.getAnnotation(PostForm.class);
        if (postForm != null) {
            return httpPostForm(postForm.value(), method, args);
        }
        return null;
    }

    private static String httpPostForm(String url, Method method, Object[] args) {
        String httpUrl = getHttpUrl(url, method, args);
        MultiValueMap<String, String> formValue = new LinkedMultiValueMap<>();
        final Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            final Form form = parameters[i].getAnnotation(Form.class);
            if (form != null) {
                formValue.set(form.value(), args[i].toString());
                continue;
            }
            final FormMap formMap = parameters[i].getAnnotation(FormMap.class);
            if (formMap != null) {
                final Object arg = args[i];
                final JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(arg));
                for (String key : jsonObject.keySet()) {
                    formValue.set(key, jsonObject.getString(key));
                }
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formValue, headers);
        return new RestTemplate().postForObject(httpUrl, entity, String.class);
    }

    private static String handleHttpGet(String url, Method method, Object[] args) {
        String httpUrl = getHttpUrl(url, method, args);
        return new RestTemplate().getForObject(httpUrl, String.class);
    }

    private static String handleHttpPost(String url, Method method, Object[] args) {
        String httpUrl = getHttpUrl(url, method, args);
        final Parameter[] parameters = method.getParameters();
        Object requestBody = null;
        for (int i = 0; i < method.getParameters().length; i++) {
            Body body = parameters[i].getAnnotation(Body.class);
            if (body != null) {
                if (requestBody != null) {
                    throw new RuntimeException();
                }
                requestBody = args[0];
            }
        }
        return new RestTemplate().postForObject(httpUrl, requestBody, String.class);
    }

    private static String getHttpUrl(String url, Method method, Object[] args) {
        final Parameter[] parameters = method.getParameters();
        final StringBuilder urlParams = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            final Query query = parameters[i].getAnnotation(Query.class);
            if (query != null) {
                String key = query.value();
                if (urlParams.length() == 0) {
                    urlParams.append(url.contains("?") ? "&" : "?");
                }
                urlParams.append(key).append("=").append(args[i]);
            }
        }
        return url + urlParams;
    }
}

