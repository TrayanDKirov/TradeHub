package bg.sofia.uni.fmi.tdkirov.tradehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Folders served as resources (see WebConfiguration) must be excluded,
    // otherwise their files are forwarded to the Angular app
    @GetMapping(value = {"/{path:[^\\.]*}", "/{path:(?!images$|uploads$)[^\\.]*}/**"})
    public String forward() {
        return "forward:/index.html";
    }
}