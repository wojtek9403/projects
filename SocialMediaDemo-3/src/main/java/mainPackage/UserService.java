package mainPackage;

import dbModelsnDAOs.User;

public interface UserService {
	
    void save(User user);

    User findByUsername(String Username);
    
}
