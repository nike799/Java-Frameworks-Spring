package residentevil.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import residentevil.interceptors.UserAccessInterceptor;


@Configuration
public class ApplicationBeanConfiguration implements WebMvcConfigurer {
    private final UserAccessInterceptor userAccessInterceptor;

    public ApplicationBeanConfiguration(UserAccessInterceptor userAccessInterceptor) {
        this.userAccessInterceptor = userAccessInterceptor;
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
        registry.addInterceptor(userAccessInterceptor).addPathPatterns("/home","/viruses/show","/viruses/add","/viruses/edit/*","/users/all");
    }
}
