package residentevil.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final static String[] PERMITTED_ROUTES_ANONYMOUS = {"/","/login","/users/register"};
    private final static String[] PERMITTED_ROUTES_USER = {"/home","/viruses/show","/logout","/viruses/fetch-capitals","/viruses/fetch-viruses"};
    private final static String[] PERMITTED_ROUTES_MODERATORS = {"/viruses/add","/viruses/delete/*","/viruses/edit/*","/viruses/save/*"};
    private final static String[] PERMITTED_ROUTES_CSS_JS = {"/css/**", "/js/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers(PERMITTED_ROUTES_CSS_JS).permitAll()
                .antMatchers(PERMITTED_ROUTES_ANONYMOUS).anonymous()
                .antMatchers(PERMITTED_ROUTES_USER).hasAnyAuthority("USER","MODERATOR","ADMIN")
                .antMatchers(PERMITTED_ROUTES_MODERATORS).hasAnyAuthority("MODERATOR","ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/unauthorized")
                .and();
    }
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository =
                new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
