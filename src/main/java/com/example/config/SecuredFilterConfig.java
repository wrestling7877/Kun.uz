package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilterConfig {

    @Autowired
    private TokenFilter tokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean bean = new FilterRegistrationBean();

        bean.setFilter(tokenFilter);

        bean.addUrlPatterns("/profile/admin/*");
        bean.addUrlPatterns("/region/*");
        bean.addUrlPatterns("/article_type/admin/*");
        bean.addUrlPatterns("/category/*");
        bean.addUrlPatterns("/attach/admin/*");
        bean.addUrlPatterns("/article/admin/*");
        bean.addUrlPatterns("/emailHistoryController/admin/*");
        bean.addUrlPatterns("/comment/*");
        return bean;
    }
}
