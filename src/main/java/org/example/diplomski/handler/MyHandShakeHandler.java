package org.example.diplomski.handler;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MyHandShakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        // You can extract the username from query parameters just for testing
        String username = getQueryParam(request, "user");
        return () -> username != null ? username : "anonymous";
    }

    private String getQueryParam(ServerHttpRequest request, String key) {
        List<String> values = request.getURI().getQuery() != null ?
                Arrays.stream(request.getURI().getQuery().split("&"))
                        .filter(param -> param.startsWith(key + "="))
                        .map(param -> param.split("=")[1])
                        .collect(Collectors.toList())
                : Collections.emptyList();
        return values.isEmpty() ? null : values.get(0);
    }
}