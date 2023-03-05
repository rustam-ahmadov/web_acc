package web_acc.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import web_acc.dto.ImageDTO;
import web_acc.dto.UserDTO;
import web_acc.entity.CustomUser;
import web_acc.service.CustomUserService;

@Controller
@RequestMapping("/")
public class HomePageController {

    private final CustomUserService customUserService;

    public HomePageController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @GetMapping
    public String getHomePage(HttpSession session, @SessionAttribute("userUUID") String userUUID, Model model) {
        String sessionId = session.getId();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();

        CustomUser user = (CustomUser) customUserService.loadUserByUUID(userUUID);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setSurname(user.getSurname());
        userDTO.setUsername(user.getUsername());

        model.addAttribute("userDTO", userDTO);
        model.addAttribute("imageDTO", new ImageDTO());

        return "home";
    }
}
