package rscvanilla.xp.infrastructure.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import rscvanilla.xp.infrastructure.time.SystemTime;
import rscvanilla.xp.infrastructure.time.SystemTimeContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebRequestTimeInterceptor implements HandlerInterceptor {

    @Autowired
    private SystemTimeContext systemTimeContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        systemTimeContext.setTime(SystemTime.now());
        return true;
    }
}
