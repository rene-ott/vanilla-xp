package rscvanilla.xp.infrastructure.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import rscvanilla.xp.domain.utils.DateTime;
import rscvanilla.xp.infrastructure.time.SystemTime;
import rscvanilla.xp.infrastructure.time.SystemTimeContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class WebRequestTimeInterceptor implements HandlerInterceptor {

    private final SystemTimeContext systemTimeContext;

    @Autowired
    public WebRequestTimeInterceptor(SystemTimeContext systemTimeContext) {
        this.systemTimeContext = systemTimeContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        systemTimeContext.setRequestTime(DateTime.now());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        systemTimeContext.clearRequestTime();
    }
}
