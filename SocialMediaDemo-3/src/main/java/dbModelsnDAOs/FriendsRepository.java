package dbModelsnDAOs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendsRepository extends JpaRepository<Friends, Long>{
	
	@Query("SELECT friends FROM Friends friends WHERE friends.userName = :username AND friends.friendName = :friendname ")
	Friends getFriendByUserNameAndFriendsName(@Param("username") String uName, @Param("friendname") String fName);
	
	@Query("SELECT friends.userName FROM Friends friends WHERE friends.friendName = :friendName")
	List<String> getAllWhoFollowsMe(@Param("friendName") String uName);
	
}
