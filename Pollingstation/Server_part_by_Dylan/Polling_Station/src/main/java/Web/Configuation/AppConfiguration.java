package Web.Configuation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableScheduling
@EnableWebMvc
@ComponentScan(basePackages = { "com.boraji.tutorial.spring.interceptor" })
public class AppConfiguration extends WebMvcConfigurerAdapter{

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    //https://blog.csdn.net/aqudgv83/article/details/79375425 resource
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       // Register guest interceptor with single path pattern
       registry.addInterceptor(new InterceptorReceipt()).addPathPatterns(new String[] { "/receipt", "/receipt/*" });
       
       registry.addInterceptor(new InterceptorPublic()).addPathPatterns(new String[] { "/public", "/public/*" });
  
       // Register admin interceptor with multiple path patterns
       registry.addInterceptor(new InterceptorCoordinator()).addPathPatterns(new String[] { "/coordinator", "/coordinator/*" });
    }
    
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    
}
