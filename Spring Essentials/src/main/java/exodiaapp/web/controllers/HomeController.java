package exodiaapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController {
    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView, HttpSession session){
        if (session.getAttribute("username") != null) {
            return this.redirect("/home");
        }
        return this.view("index");
    }

}
