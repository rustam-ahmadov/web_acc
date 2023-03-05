package web_acc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web_acc.dto.request.LoginRequest;
import web_acc.dto.request.RegistrationRequest;
import web_acc.entity.CustomUser;
import web_acc.service.CustomUserService;

@Controller
@RequestMapping
public class LoginController {

    private final CustomUserService userService;

    public LoginController(CustomUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("registrationRequest",new RegistrationRequest());
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@ModelAttribute(value = "registrationRequest") RegistrationRequest registrationRequest,
                               Model model) {
        CustomUser customUser = null;

        try {
            customUser = userService.save(registrationRequest);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
