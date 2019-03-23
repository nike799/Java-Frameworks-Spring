package exodiaapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController extends BaseController {
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        if (session.getAttribute("username") == null) {
           return this.redirect("/");
        }
        session.invalidate();
        return this.view("index");
    }
}
