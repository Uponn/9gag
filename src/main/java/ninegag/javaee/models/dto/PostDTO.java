package ninegag.javaee.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {
    private String author;
    private String title;
    private long likes;
    private byte[] file;
    private LocalDateTime uploadTime;

    public PostDTO(String author, String title, long likes) {
        this.author = author;
        this.title = title;
        this.likes = likes;
    }
}
