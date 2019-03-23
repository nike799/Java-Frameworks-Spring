package residentevil.web;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserHomeController extends BaseController{
    @GetMapping("/home")
    @PreAuthorize(value = "hasAnyAuthority('USER','MODERATOR','ADMIN')")
    public ModelAndView home(Authentication authentication, HttpSession session){
        return this.view("user-home");
    }
}
