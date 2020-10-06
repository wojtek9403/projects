package tab;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("SELECT user.Username FROM User user WHERE user.Username = :login")
	String findRegistredLogin(@Param("login") String login);
	
	@Query("SELECT user FROM User user WHERE user.Name = :Name")
	List<User> findAllByName(@Param("Name") String Name);
	
	@Query("SELECT user FROM User user WHERE user.Surname = :Surname")
	List<User> findAllBySurname(@Param("Surname") String Surname);
	
	@Query("SELECT user FROM User user WHERE user.Name = :Name AND user.Surname = :Surname")
	List<User> findAllByNameAndSurname(@Param("Name") String Name, @Param("Surname") String Surname);

}
