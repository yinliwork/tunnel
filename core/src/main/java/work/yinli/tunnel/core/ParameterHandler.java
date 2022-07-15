package work.yinli.tunnel.core;

import java.util.Map;

import com.google.gson.Gson;

/**
 * 1.  @Path 路径
 * 2.  @Query get单属性
 * 3.  @QueryMap get多属性
 * 4.  @Form 表单单属性
 * 5.  @FormMap 表单多属性
 * 6.  @Part 表单文件单属性
 * 7.  @PartMap 表单文件多属性
 * 8.  @Body json 单属性
 * 9.  @BodyMap json 多属性
 * 10. @Url 地址
 *
 * @author yangji
 */
public interface ParameterHandler<T> {

    /**
     * @param builder 请求对象
     * @param value   数据
     */
    void apply(Request.RequestBuilder builder, T value);

    class Path<T> implements ParameterHandler<T> {
        private final String key;

        public Path(String key) {
            this.key = key;
        }

        @Override
        public void apply(Request.RequestBuilder builder, T value) {
            builder.setPath(key, String.valueOf(value));
        }
    }

    class Query<T> implements ParameterHandler<T> {
        private final String key;
        private final boolean encoded;

        public Query(String key, boolean encoded) {
            this.key = key;
            this.encoded = encoded;
        }

        @Override
        public void apply(Request.RequestBuilder builder, T value) {
            builder.addQueryParam(key, String.valueOf(value), encoded);
        }
    }

    class QueryMap<T> implements ParameterHandler<Map<String, T>> {
        private final boolean encoded;

        public QueryMap(boolean encoded) {
            this.encoded = encoded;
        }

        @Override
        public void apply(Request.RequestBuilder builder, Map<String, T> value) {
            value.forEach((k, v) -> {
                builder.addQueryParam(k, String.valueOf(v), encoded);
            });
        }
    }

    class BodyMap<T> implements ParameterHandler<T> {
        @Override
        public void apply(Request.RequestBuilder builder, T value) {
            builder.setBody(new Gson().toJson(value));
        }
    }

    class Body<T> implements ParameterHandler<T> {
        private final String key;

        public Body(String key) {
            this.key = key;
        }

        @Override
        public void apply(Request.RequestBuilder builder, T value) {
            builder.addBody(key, value);
        }
    }

    class Form<T> implements ParameterHandler<T> {
        private final String key;

        public Form(String key) {
            this.key = key;
        }

        @Override
        public void apply(Request.RequestBuilder builder, T value) {
            builder.addForm(key, String.valueOf(value));
        }
    }

    class FormMap<T> implements ParameterHandler<Map<String, T>> {
        @Override
        public void apply(Request.RequestBuilder builder, Map<String, T> value) {
            value.forEach((k, v) -> builder.addForm(k, String.valueOf(v)));
        }
    }
}
