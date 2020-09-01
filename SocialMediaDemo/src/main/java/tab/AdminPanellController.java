package tab;



import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/Demo") 
public class AdminPanellController {
	
	
	private UserRepository UserRepository;
	
	private PictureRepository PictureRepository;

	private MainServicePerformer MainServicePerformerImpl;

	public AdminPanellController(tab.UserRepository userRepository, tab.PictureRepository pictureRepository,
			MainServicePerformer mainServicePerformerImpl) {
		super();
		UserRepository = userRepository;
		PictureRepository = pictureRepository;
		MainServicePerformerImpl = mainServicePerformerImpl;
	}

	
    @Secured("ROLE_ADMIN") 
	@Cacheable("AdminViewx")
    @GetMapping("/AdminControll")
    public String AdminControllPanel(Model model, HttpSession session) {
    	
    	return MainServicePerformerImpl.performAdminView(UserRepository, model, session);
    }
    
    @Secured("ROLE_ADMIN") 
    @GetMapping("/usuwacz")
    public String remover(String userNameToRemove, HttpSession session, Model model)
    {
		User user = UserRepository.findById((String) session.getAttribute("user")).get();
		model.addAttribute("profile", user.getProfilePicture());
		model.addAttribute("name", user.getUsername());
    	
    	return 	MainServicePerformerImpl.deleteChosenUser(PictureRepository, UserRepository, userNameToRemove, session, model); 	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
}
