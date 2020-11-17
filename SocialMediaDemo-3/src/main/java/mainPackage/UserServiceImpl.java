package mainPackage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import dbModelsnDAOs.User;
import dbModelsnDAOs.UserRepository;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
	
    
    private UserRepository userRepository; 
    
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

    @Override
    public void save(User user) {
    	
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); 
        user.setRole("ROLE_USER");  
        user.setProfilePicture("dodaj profilowke.jpg");
        
        if(this.userRepository.findRegistredLogin(user.getUsername())!=null)
    	{
    		System.err.println("findOutDiffrentLogin");
    	}
    	else 
    	{   
    		userRepository.save(user);   
    	}

    }



	@Override
    public User findByUsername(String Username) {
    	 	
        return userRepository.findById(Username).get();

    }
}