package web_acc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomePageController {
    @GetMapping
    public String getHomePage(@RequestParam(name = "name", required = false, defaultValue = "???")
                              String name, Model model) {
        model.addAttribute("name",name);
        return "home";
    }
}
