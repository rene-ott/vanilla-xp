package rscvanilla.xp.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String index() {
        return "forward:/";
    }
}
