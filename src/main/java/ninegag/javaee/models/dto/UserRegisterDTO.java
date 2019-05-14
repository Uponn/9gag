package ninegag.javaee.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {
    private String fullName;
    private String username;
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassword;
    private String email;
    private int age;
}
