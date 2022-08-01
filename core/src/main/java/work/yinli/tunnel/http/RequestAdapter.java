package work.yinli.tunnel.http;

import com.github.kevinsawicki.http.HttpRequest;
import work.yinli.tunnel.core.HttpAdapter;
import work.yinli.tunnel.core.Request;
import work.yinli.tunnel.core.Response;


public class RequestAdapter implements HttpAdapter {

    @Override
    public Response call(Request request) {
        HttpRequest.get(request.getUrl());
        return null;
    }
}
