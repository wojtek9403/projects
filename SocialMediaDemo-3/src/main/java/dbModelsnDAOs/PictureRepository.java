package dbModelsnDAOs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PictureRepository extends JpaRepository<Picture, Long> {

	@Query("SELECT MAX(p.id) FROM Picture p")
	long getPictureMaxId();

	Picture findByorginalPicPath(String orginalPicPath);
	
	@Query("SELECT p FROM Picture p WHERE p.id > 0")
	List<Picture> getAll();
	
	@Query("SELECT p FROM Picture p WHERE p.id <= :p AND p.id >= :q ORDER BY p.id DESC")
	List<Picture> getNNpics(@Param("p") long p, @Param("q") long q);
	
	
	
//	@Query("SELECT p FROM Picture p, User u WHERE u.Username = :friendName")
//	List<Picture> findMyFriendsPictures(@Param("friendName") String friendName);

}
