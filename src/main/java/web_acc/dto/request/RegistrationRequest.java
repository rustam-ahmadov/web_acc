package web_acc.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String username;
    private String surname;
    private String email;
    private String password;
}
