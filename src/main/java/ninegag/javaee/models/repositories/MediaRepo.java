package ninegag.javaee.models.repositories;

import ninegag.javaee.models.pojo.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepo extends JpaRepository<Media, Long> {

}
