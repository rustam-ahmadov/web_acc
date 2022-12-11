package web_acc.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomePageController {
    @GetMapping
    public ResponseEntity<?> getHomePage(@RequestParam String name)
    {
        return ResponseEntity.ok(name);
    }
}
