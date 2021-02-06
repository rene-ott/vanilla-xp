package rscvanilla.xp.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rscvanilla.xp.infrastructure.interceptors.WebRequestTimeInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private WebRequestTimeInterceptor webRequestTimeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webRequestTimeInterceptor);
    }
}
