package com.example.interceptor.config;

import com.example.interceptor.annotation.Auth;
import com.example.interceptor.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//어노테이션, 빈을 등록
@RequiredArgsConstructor//어노테이션은 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성
public class MvcConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
        /* 특정 uri만 검사할 경우
        registry.addInterceptor(authInterceptor).addPathPatterns("/api/private/*");
        registry.addInterceptor(authInterceptor).addPathPatterns("/api/private/*", "uri를 추가하면 된다.");

        검사하지 않고자하는 uri가 있을 경우
        registry.addInterceptor(authInterceptor).excludePathPatterns("uri를 추가한다.");
        */

    }
}
