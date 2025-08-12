package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("/home/")
public class HomeController {

    @ResponseBody
    @GetMapping("/greet")
    public String greet() {
        return "welcome to the Home Page !";
    }
}
