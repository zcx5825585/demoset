package zcx.com.example.same_url.config;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import zcx.com.example.same_url.scheduler.SameUrlScheduler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class SameUrlDataInterceptor extends HandlerInterceptorAdapter {

    /**
     * 是否阻止提交,fasle阻止,true放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //对所有post请求进行拦截，也可自定义注解
            Class[] annotations = {PostMapping.class};

            if (hasAnnotations(method, annotations)) {
                if (SameUrlScheduler.sameUrl(request)) {
                    //是重复请求
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    response.getWriter().write("{\"code\":0,\"msg\":\"重复提交\",\"load\":null}");
                    response.getWriter().close();
                    return false;
                } else {//如果不是是重复请求
                    return true;
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }


    private boolean hasAnnotations(Method method, Class[] classes) {
        for (Class aClass : classes) {
            if (method.getAnnotation(aClass) != null) return true;
        }
        return false;
    }


}