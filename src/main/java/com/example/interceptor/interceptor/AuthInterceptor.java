package com.example.interceptor.interceptor;

import com.example.interceptor.annotation.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();

        URI uri = UriComponentsBuilder.fromUriString(request.getRequestURI())
                        .query(request.getQueryString()).build().toUri();

        log.info("request url : {}", url);

        boolean hasAnnotation = checkAnnotaiton(handler, Auth.class);

        log.info("has annotation : {}", hasAnnotation);

        //서버는 모두 public으로 동작하는데, Auth 권한을 가진 요청에 대해서는 세션, 쿠키를 이용한다.
        if(hasAnnotation){
            String query = uri.getQuery();
            if(query.equals("name = yuni"))
                return true;

            //return false;
            throw new AuthException();
        }

        return false;
    }

    private boolean checkAnnotaiton(Object handler, Class clazz){
        //resoruce, javascrit, html ...
        if(handler instanceof ResourceHttpRequestHandler)
            return true;

        //annotation check
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //Auth annotation이 있을 때 true
        if(null != handlerMethod.getMethodAnnotation(clazz) || null != handlerMethod.getBeanType().getAnnotation(clazz))
            return true;

        return false;

    }
}
