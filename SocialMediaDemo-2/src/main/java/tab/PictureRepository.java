package tab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PictureRepository extends JpaRepository<Picture, Long>{
	
	@Query("SELECT MAX(p.id) FROM Picture p")
	long getPictureMaxId();
	
	Picture findByorginalPicPath(String orginalPicPath);
	
	
}
