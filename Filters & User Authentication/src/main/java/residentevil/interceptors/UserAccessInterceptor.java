package residentevil.interceptors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("userAccessInterceptor")
public class UserAccessInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("principal", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        modelAndView.addObject("isAdmin", SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(r-> r.getAuthority().equals("ADMIN")));
        modelAndView.addObject("isModerator", SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(r-> r.getAuthority().equals("MODERATOR")));
        modelAndView.addObject("isUser", SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().noneMatch(r-> r.getAuthority().equals("MODERATOR") || r.getAuthority().equals("ADMIN") ));
        super.postHandle(request, response, handler, modelAndView);
    }
}
