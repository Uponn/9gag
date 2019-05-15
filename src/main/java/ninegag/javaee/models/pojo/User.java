package ninegag.javaee.models.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ninegag.javaee.models.dto.UserSessionDTO;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private int age;

    public User(String fullName, String username, String password, String email, int age) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public UserSessionDTO convertToUserSessionDTO(){
        return new UserSessionDTO(this.getId(), this.getUsername(), this.getEmail());
    }
}
