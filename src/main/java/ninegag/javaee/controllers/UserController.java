package ninegag.javaee.controllers;


import ninegag.javaee.models.dto.UserLoginDTO;
import ninegag.javaee.models.dto.UserRegisterDTO;
import ninegag.javaee.models.pojo.User;
import ninegag.javaee.models.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    private static final String EMAIL_VERIFICATION = "^([\\p{L}-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

    @Autowired
    UserRepo userRepo;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping(value = "/register")
    public Object registerUser(@RequestBody UserRegisterDTO userRegisterDTO){
        validateRegisterDetails(userRegisterDTO);
        if (!userRepo.existsByUsername(userRegisterDTO.getUsername()) && !userRepo.existsByEmail(userRegisterDTO.getEmail())){
            User user = new User(userRegisterDTO.getFullName(), userRegisterDTO.getUsername(),
                    encoder.encode(userRegisterDTO.getPassword()),
                    userRegisterDTO.getEmail(),
                    userRegisterDTO.getAge());
            userRepo.save(user);
            return user;
        }else {
            //TODO throw username or email already taken exception
        }
        return "Something went wrong";
    }
    @PostMapping(value = "/login")
    public Object loginUser(@RequestBody UserLoginDTO userLoginDTO){
        validateLoginDetails(userLoginDTO);
        if (userRepo.existsByUsername(userLoginDTO.getUsername())) {
            User dbUser = userRepo.getByUsername(userLoginDTO.getUsername());
            if (encoder.matches(userLoginDTO.getPassword(), dbUser.getPassword())){
                return dbUser;
            }
        }
        return "Something went wrong";
    }

    private void validateLoginDetails(UserLoginDTO user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (username == null || username.isEmpty() || username.contains( " " )){
            //TODO throw input exception
        }
        if (password == null || password.isEmpty() || password.contains( " " )){
            //TODO throw input exception
        }
    }

    private void validateRegisterDetails(UserRegisterDTO user) {
        String fullName = user.getFullName();
        String username = user.getUsername();
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();
        String email = user.getEmail();
        int age = user.getAge();

        if (fullName == null || fullName.isEmpty() || !fullName.contains( " " )) {
            //TODO throw input exception
        }
        if (username == null || username.isEmpty() || username.contains( " " )){
            //TODO throw input exception
        }
        if (password == null || password.isEmpty() || password.contains( " " )){
            //TODO throw input exception
        }
        if (!password.equals(confirmPassword)){
            //TODO throw input exception
        }
        if (!password.matches(PASSWORD_PATTERN)){
            //TODO throw input exception
        }
        if (email == null || email.isEmpty() || !email.matches( EMAIL_VERIFICATION )){
            //TODO throw input exception
        }
        if (age <= 0 || age > 120){
            //TODO throw input exception
        }

    }
}
