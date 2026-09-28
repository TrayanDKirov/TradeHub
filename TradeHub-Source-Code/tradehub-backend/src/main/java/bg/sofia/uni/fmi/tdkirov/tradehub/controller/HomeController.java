package bg.sofia.uni.fmi.tdkirov.tradehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/{path:[^\\.]*}", "/{path:(?!images$)[^\\.]*}/**"})
    public String forward() {
        return "forward:/index.html";
    }
}