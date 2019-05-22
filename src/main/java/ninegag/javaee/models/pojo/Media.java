package ninegag.javaee.models.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ninegag.javaee.models.dto.PostDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "files")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String dir;
    private long likes;
    private long dislikes;
    private String author;
    private LocalDateTime timeUploaded;


    public PostDTO convertToPostDTO(){
        return new PostDTO(this.getAuthor(), this.getTitle(), this.getLikes());
    }
}
