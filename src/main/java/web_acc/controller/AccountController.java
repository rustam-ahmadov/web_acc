package web_acc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("account")
public class AccountController {
    @GetMapping
    public String getAccountPage()
    {
        return "account";
    }

    @GetMapping("/signin")
    public String signIn()
    {
        return "signIn";
    }

    @GetMapping("/signup")
    public String signUp()
    {
        return "signUp";
    }

}
