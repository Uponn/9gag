package ninegag.javaee.controllers;

import ninegag.javaee.models.dto.PostDTO;
import ninegag.javaee.models.pojo.Media;
import ninegag.javaee.models.repositories.MediaRepo;
import ninegag.javaee.models.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BrowseController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    MediaRepo mediaRepo;
    @GetMapping(value = "/")
    public List<PostDTO> browseAll() throws IOException {
        List<Media> dbMedia = mediaRepo.findAll();
        List<PostDTO> list = new ArrayList<>();
        for (Media media : dbMedia){
            list.add(media.convertToPostDTO());
        }
        for (PostDTO post : list){
            post.setFile(returnImage());
        }
        return list;
    }
    private byte[] returnImage() throws IOException {
        List<Media> media = mediaRepo.findAll();
        MultipartFile image = null;

        for (Media file : media){
            File newImage = new File(file.getDir());
            FileInputStream fis = new FileInputStream(newImage);
            return fis.readAllBytes();
        }
        return new byte[1];
    }
}
