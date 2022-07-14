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

public class HttpProxy {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{tClass}, new InvocationHandler() {   // Returns the system class loader for delegation.  This is the default  delegation parent for new ClassLoader instances, and is
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 走的是 InvocationHandler的对象 toString，原因是 this
                if (method.getDeclaringClass() == Object.class) {   // }
                    return method.invoke(this, args);   // Invokes the underlying method represented by this  Method  object, on the specified object with the specified parameters.
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

    @SuppressWarnings("unchecked")
    private static Object handleResponse(String response, Method method) {
        final Type returnType = method.getGenericReturnType();   // Returns a  Type  object that represents the formal return
        // 转换器
        return JSON.parseObject(response, returnType);
        /* // 注解返回值
        final Class<BaseServiceResult<?>> value = (Class<BaseServiceResult<?>>) result.value();
        // 回调内容
        final Type[] actualTypeArguments = ((ParameterizedTypeImpl) returnType).getActualTypeArguments();   // Returns an array of Type objects representing the actual type  arguments to this type.
        // 组合注解返回值<回调具体类容>
        final ParameterizedTypeImpl resultBody = ParameterizedTypeImpl.make(value, actualTypeArguments, null);   // Static factory. Given a (generic) class, actual type arguments  and an owner type, creates a parameterized type.
        final BaseServiceResult<?> serviceResult = JSON.parseObject(response, resultBody);
        return serviceResult.toBaseResult();*/
    }

    private static String handleHttp(Method method, Object[] args) {
        final Get getMethod = method.getAnnotation(Get.class);   // }
        if (getMethod != null) {
            return handleHttpGet(getMethod.value(), method, args);
        }
        final Post postMethod = method.getAnnotation(Post.class);   // }
        if (postMethod != null) {
            return handleHttpPost(postMethod.value(), method, args);
        }
        final PostForm postForm = method.getAnnotation(PostForm.class);   // }
        if (postForm != null) {
            return httpPostForm(postForm.value(), method, args);
        }
        return null;
    }

    private static String httpPostForm(String url, Method method, Object[] args) {
        String httpUrl = getHttpUrl(url, method, args);
        MultiValueMap<String, String> formValue = new LinkedMultiValueMap<>();
        final Parameter[] parameters = method.getParameters();   // Returns an array of  Parameter  objects that represent
        for (int i = 0; i < parameters.length; i++) {
            final Form form = parameters[i].getAnnotation(Form.class);   // }
            if (form != null) {
                formValue.set(form.value(), args[i].toString());   // Returns a string representation of the object. In general, the
                continue;
            }
            final FormMap formMap = parameters[i].getAnnotation(FormMap.class);   // }
            if (formMap != null) {
                final Object arg = args[i];
                final JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(arg));
                for (String key : jsonObject.keySet()) {   // Returns a    view of the keys contained in this map.
                    formValue.set(key, jsonObject.getString(key));
                }
            }
        }
        HttpHeaders headers = new HttpHeaders();   // Constructs a new, empty instance of the  HttpHeaders  object.
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);   // Set the    of the body,
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formValue, headers);   // Create a new  HttpEntity  with the given body and headers.
        return new RestTemplate().postForObject(httpUrl, entity, String.class);   // Create a new resource by POSTing the given object to the URI template,  and returns the representation found in the response.
    }

    private static String handleHttpGet(String url, Method method, Object[] args) {
        String httpUrl = getHttpUrl(url, method, args);
        return new RestTemplate().getForObject(httpUrl, String.class);   // Retrieve a representation by doing a GET on the specified URL.  The response (if any) is converted and returned.
    }

    private static String handleHttpPost(String url, Method method, Object[] args) {
        String httpUrl = getHttpUrl(url, method, args);
        final Parameter[] parameters = method.getParameters();   // Returns an array of  Parameter  objects that represent
        Object requestBody = null;
        for (int i = 0; i < method.getParameters().length; i++) {   // Returns an array of  Parameter  objects that represent
            Body body = parameters[i].getAnnotation(Body.class);   // }
            if (body != null) {
                if (requestBody != null) {
                    throw new RuntimeException();   // Constructs a new runtime exception with  null  as its
                }
                requestBody = args[0];
            }
        }
        return new RestTemplate().postForObject(httpUrl, requestBody, String.class);   // Create a new resource by POSTing the given object to the URI template,  and returns the representation found in the response.
    }

    private static String getHttpUrl(String url, Method method, Object[] args) {
        final Parameter[] parameters = method.getParameters();   // Returns an array of  Parameter  objects that represent
        final StringBuilder urlParams = new StringBuilder();   // Constructs a string builder with no characters in it and an  initial capacity of 16 characters.
        for (int i = 0; i < parameters.length; i++) {
            final Query query = parameters[i].getAnnotation(Query.class);   // }
            if (query != null) {
                String key = query.value();
                if (urlParams.length() == 0) {   // Returns the length (character count).
                    urlParams.append(url.contains("?") ? "&" : "?");   // Returns true if and only if this string contains the specified  sequence of char values.
                }
                urlParams.append(key).append("=").append(args[i]);   // Appends the string representation of the  Object  argument.
            }
        }
        return url + urlParams;
    }
}

