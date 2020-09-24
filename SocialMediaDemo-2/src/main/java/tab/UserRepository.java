package tab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("SELECT user.Username FROM User user WHERE user.Username = :login")
	String findRegistredLogin(@Param("login") String login);

}
