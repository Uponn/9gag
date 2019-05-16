package ninegag.javaee.models.repositories;

import ninegag.javaee.models.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User getByUsername(String username);
    User getById(long id);
}
