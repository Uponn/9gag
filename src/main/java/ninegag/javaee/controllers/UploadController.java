package ninegag.javaee.controllers;

import ninegag.javaee.models.pojo.Media;
import ninegag.javaee.models.pojo.User;
import ninegag.javaee.models.repositories.MediaRepo;
import ninegag.javaee.models.repositories.UserRepo;
import ninegag.javaee.util.ResponseMsg;
import ninegag.javaee.util.SessionManager;
import ninegag.javaee.util.exceptions.NotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UploadController extends BaseController{
    @Autowired
    UserRepo userRepo;
    @Autowired
    MediaRepo mediaRepo;

    @PostMapping(value = "/upload")

    public Object uploadFile(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException, NotLoggedException{
        if (!SessionManager.isLogged(session)){
            throw new NotLoggedException("You have to be logged to do that.");
        }
        saveFileToServer(file);
        setFileFields(file, session);
        return new ResponseMsg("Media uploaded successfully", HttpStatus.OK.value(), LocalDateTime.now());
    }

    private void setFileFields(MultipartFile file, HttpSession session) {
        Media media = new Media();
        User user = userRepo.getById(SessionManager.getUserId(session));
        media.setTimeUploaded(LocalDateTime.now());
        media.setDir("D:\\test\\" + file.getOriginalFilename());
        media.setAuthor(user.getUsername());
        mediaRepo.save(media);
    }

    private void saveFileToServer(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        File newFile = new File("D:\\test\\" + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(bytes);
        fos.close();
    }
}
