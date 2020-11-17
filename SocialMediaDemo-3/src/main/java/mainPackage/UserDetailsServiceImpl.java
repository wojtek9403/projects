package mainPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import dbModelsnDAOs.User;
import dbModelsnDAOs.UserRepository;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService 
{		
   
    private UserRepository userRepository;  
    
    private UserSessionProvider UserDelivery;

    
    public UserDetailsServiceImpl(UserRepository userRepository, UserSessionProvider userDelivery) {
		super();
		this.userRepository = userRepository;
		UserDelivery = userDelivery;
	}

	@Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) 
    {
    	
    	User user = new User();
    	user.setRole("USER");
    	user.setUsername("FAKE_NAME");
    	user.setPassword("$2b$10$//DXiVVE59p7G5k/4Klx/ezF7BI42QZKmoOD0NDvUuqxRE5bFFBLy"); 
    	user.setPasswordConfirm("$2b$10$//DXiVVE59p7G5k/4Klx/ezF7BI42QZKmoOD0NDvUuqxRE5bFFBLy"); 
    	
    	try 
    	{
    		
	    	user = userRepository.findById(username).get();
	        if (user == null) throw new UsernameNotFoundException(username);
	        
    	}
    	catch(NoSuchElementException ex)
    	{
    		System.err.println(ex.getMessage());
    	}
        
        UserDelivery.saveUserName(username); 
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));       
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}

