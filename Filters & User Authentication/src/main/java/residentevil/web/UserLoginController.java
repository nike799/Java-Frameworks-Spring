package residentevil.web;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserLoginController extends BaseController {

    @GetMapping("/login")
    @PreAuthorize(value = "isAnonymous()")
    public ModelAndView login(Principal principal) {
        System.out.println();
        return principal == null ? this.view("/user-login") : this.redirect("/home");
    }
}
