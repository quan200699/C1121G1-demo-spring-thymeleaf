package com.codegym.config;

import com.codegym.service.IProductService;
import com.codegym.service.ProductService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@EnableWebMvc
@Configuration
@ComponentScan("com.codegym.controller")
public class AppConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //Cấu hình thymeleaf
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver(); //SpringResourceTemplateResolver => class con của interface TemplateResolver
        //Định nghĩa bộ khung cho view
        templateResolver.setPrefix("/WEB-INF/views/"); //tiền tố
        templateResolver.setSuffix(".html"); //Đuổi file
        templateResolver.setTemplateMode(TemplateMode.HTML); //Định dạng của view
        templateResolver.setCharacterEncoding("UTF-8"); // Viết tiếng việt
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() { //Template engine được khai báo để sử dụng bộ khung template resolver ở trên
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() { //Sử dụng bộ template engine đã khai báo
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    public IProductService productService() {
        return new ProductService();
    }
}
