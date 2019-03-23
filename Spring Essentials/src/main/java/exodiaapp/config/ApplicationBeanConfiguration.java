package exodiaapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ApplicationBeanConfiguration implements WebMvcConfigurer {
    private final HandlerInterceptor userAccessInterceptor;
    private final HandlerInterceptor guestAccessInterceptor;

    public ApplicationBeanConfiguration(@Qualifier("userAccessInterceptor") HandlerInterceptor userAccessInterceptor,
                                        @Qualifier("guestAccessInterceptor") HandlerInterceptor guestAccessInterceptor) {
        this.userAccessInterceptor = userAccessInterceptor;
        this.guestAccessInterceptor = guestAccessInterceptor;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
   //    registry.addInterceptor(this.userAccessInterceptor).addPathPatterns("/", "/login", "/register");
   //    registry.addInterceptor(this.guestAccessInterceptor).excludePathPatterns("/", "/login", "/register");
    }
}
