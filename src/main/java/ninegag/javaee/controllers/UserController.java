package ninegag.javaee.controllers;


import ninegag.javaee.models.dto.UserLoginDTO;
import ninegag.javaee.models.dto.UserRegisterDTO;
import ninegag.javaee.models.pojo.User;
import ninegag.javaee.models.repositories.UserRepo;
import ninegag.javaee.util.ResponseMsg;
import ninegag.javaee.util.exceptions.AlreadyTakenException;
import ninegag.javaee.util.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
public class UserController extends BaseController{
    private static final String EMAIL_VERIFICATION = "^([\\p{L}-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

    @Autowired
    UserRepo userRepo;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping(value = "/register")
    public Object registerUser(@RequestBody UserRegisterDTO userRegisterDTO) throws InvalidInputException {
        validateRegisterDetails(userRegisterDTO);
        if (!userRepo.existsByUsername(userRegisterDTO.getUsername()) && !userRepo.existsByEmail(userRegisterDTO.getEmail())){
            User user = new User(userRegisterDTO.getFullName(), userRegisterDTO.getUsername(),
                    encoder.encode(userRegisterDTO.getPassword()),
                    userRegisterDTO.getEmail(),
                    userRegisterDTO.getAge());
            userRepo.save(user);
            return new ResponseMsg("Registration was successful", HttpStatus.OK.value(), LocalDateTime.now());
        }else {
            throw new AlreadyTakenException("Username or Email already taken.");
        }
    }
    @PostMapping(value = "/login")
    public Object loginUser(@RequestBody UserLoginDTO userLoginDTO) throws InvalidInputException{
        validateLoginDetails(userLoginDTO);
        if (userRepo.existsByUsername(userLoginDTO.getUsername())) {
            User dbUser = userRepo.getByUsername(userLoginDTO.getUsername());
            if (encoder.matches(userLoginDTO.getPassword(), dbUser.getPassword())){
                //TODO add session
                return dbUser;
            }
        }
        return "Something went wrong";
    }

    private void validateLoginDetails(UserLoginDTO user) throws InvalidInputException{
        String username = user.getUsername();
        String password = user.getPassword();
        if (username == null || username.isEmpty() || username.contains( " " )){
            throw new InvalidInputException("The username field is empty, please enter an username.");
        }
        if (password == null || password.isEmpty() || password.contains( " " )){
            throw new InvalidInputException("The password field is empty, please enter a password.");
        }
    }

    private void validateRegisterDetails(UserRegisterDTO user) throws InvalidInputException{
        String fullName = user.getFullName();
        String username = user.getUsername();
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();
        String email = user.getEmail();
        int age = user.getAge();

        if (fullName == null || fullName.isEmpty() || !fullName.contains( " " )) {
            throw new InvalidInputException("You have to enter your Full Name.");
        }
        if (username == null || username.isEmpty() || username.contains( " " )){
            throw new InvalidInputException("You have to enter an username.");
        }
        if (password == null || password.isEmpty() || password.contains( " " )){
            throw new InvalidInputException("You have left the password field empty, please enter a password.");
        }
        if (!password.equals(confirmPassword)){
            throw new InvalidInputException("The two passwords you've entered do not match, please double check this.");
        }
        if (!password.matches(PASSWORD_PATTERN)){
            throw new InvalidInputException("We want our passwords to :\n" +
                    "Be between 8 and 40 characters long\n" +
                    "Contain at least one digit.\n" +
                    "Contain at least one lower case character.\n" +
                    "Contain at least one upper case character.\n" +
                    "Contain at least on special character from [ @ # $ % ! . ].");
        }
        if (email == null || email.isEmpty()){
            throw new InvalidInputException("The email fields are empty, please fill those and continue.");
        }
        if (!email.matches( EMAIL_VERIFICATION )){
            throw new InvalidInputException("Please enter a valid email address.");
        }
        if (age <= 0 || age > 120){
            throw new InvalidInputException("Please enter a valid age.");
        }

    }
}
