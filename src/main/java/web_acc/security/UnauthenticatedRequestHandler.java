package web_acc.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
    To customize the response for a failed authentication, we can implement an
    AuthenticationEntryPoint. Its commence() method receives the
    HttpServletRequest,the HttpServletResponse, and the AuthenticationException that
    cause the authentication to fail.
    NOTE It’s a little ambiguous that the name of the AuthenticationEntryPoint 'interface'
    does not reflect its usage on authentication failure.
    In the Spring Security architecture, this is used directly by a component called
    ExceptionTranslationManager, which handles any AccessDeniedException and AuthenticationException thrown within the filter chain.
    You can view the ExceptionTranslationManager as a bridge between Java
    exceptions and HTTP responses.
 */
public class UnauthenticatedRequestHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.addHeader("message", "Rustam don't allow u enter, " +
                "provide our credentials in login page please!");
        response.sendRedirect("/login");
    }
}
