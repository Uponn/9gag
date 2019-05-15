package ninegag.javaee.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserSessionDTO {
    private long id;
    private String username;
    private String email;
}
