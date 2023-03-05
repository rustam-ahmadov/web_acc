package web_acc.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web_acc.entity.CustomUser;
import web_acc.service.CustomUserService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final CustomUserService customUserService;
    public static final String ATTRIBUTE_NAME = "userUUID";

    public CustomAuthenticationSuccessHandler(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        response.addHeader("Welcome", "Welcome on board!");
        String uuid = getUserUUID(authentication);
        addAttributeToSession(request, ATTRIBUTE_NAME, uuid);
        response.sendRedirect("/");
    }

    private String getUserUUID(Authentication authentication) {

        String userEmail = (String) authentication.getPrincipal();
        CustomUser customUser = (CustomUser) customUserService.loadUserByUsername(userEmail);

        return customUser.getUuid();
    }

    private void addAttributeToSession(HttpServletRequest request, String name, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(name, value);
    }
}
