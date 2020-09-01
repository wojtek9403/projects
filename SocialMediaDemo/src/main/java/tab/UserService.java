package tab;


public interface UserService {
	
    void save(User user);

    User findByUsername(String Username);
    
}
