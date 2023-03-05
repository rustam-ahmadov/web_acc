package web_acc.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web_acc.dto.ImageDTO;
import web_acc.dto.UserDTO;
import web_acc.entity.Image;
import web_acc.service.CustomUserService;

import java.io.IOException;

@Controller
public class CustomUserController {
    private final CustomUserService customUserService;


    public CustomUserController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }


    @GetMapping(value = "showAllPersons")
    public String getAllPersons(Model model) {
        model.addAttribute("persons", customUserService.getAll());
        return "allPersons";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String update(Authentication authentication,
                         HttpSession session,
                         @ModelAttribute(value = "userDTO") UserDTO userDTO,
                         @SessionAttribute("userUUID") String userUUID) {
        //we can use String uuid = (String) session.getAttribute("userUUID");
        //But better way is to use it as shown below(@SessionAttribute)
        customUserService.update(userDTO, userUUID);

        return "home";
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadImage(ImageDTO imageDTO, @SessionAttribute("userUUID") String userUUID) {
        try {
            customUserService.saveUserImage(imageDTO, userUUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/images")
    public ResponseEntity<?> getImageByName(HttpSession session,
                                            @SessionAttribute("userUUID") String userUUID) throws IOException {
        //byte[] image = imageRepository.findByName(name).orElse(null);
        String uuid = session.getAttribute("userUUID").toString();
        Image image = customUserService.getUserImage(uuid);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image.getBytes());
    }
}

