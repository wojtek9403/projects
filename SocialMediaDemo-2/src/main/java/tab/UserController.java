package tab;

import java.io.File;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javassist.NotFoundException;

@RequestMapping("/SocialMediaDemo") 
@Controller
@EnableGlobalMethodSecurity(securedEnabled = true)
public class UserController { 

	
    private UserService userService; 
    
    private SecurityService securityService;      

	private UserSessionProvider UserDelivery;
    	
	private UserRepository UserRepository;
		
	private PictureRepository PictureRepository;
	
	private RegistrationValidator RegistrationValidator; 
	
	private MainServicePerformer MainServicePerformerImpl;

	
	public UserController(UserService userService, SecurityService securityService, UserSessionProvider userDelivery,
			tab.UserRepository userRepository, tab.PictureRepository pictureRepository,
			tab.RegistrationValidator registrationValidator, MainServicePerformer mainServicePerformerImpl) {
		super();
		this.userService = userService;
		this.securityService = securityService;
		UserDelivery = userDelivery;
		UserRepository = userRepository;
		PictureRepository = pictureRepository;
		RegistrationValidator = registrationValidator;
		MainServicePerformerImpl = mainServicePerformerImpl;
	}

	@GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }
    
    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") @Validated User userForm, BindingResult bindingResult) {
    	
    	RegistrationValidator.validate(userForm, bindingResult); 
    	
    	if(bindingResult.hasErrors())
		{
			return "registration";
		} 
    	
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        

        return "redirect:/SocialMediaDemo/out";
    }

    @GetMapping("/login") 
    public String login(Model model, String error) {
    	if(error!=null)
    	{
    		model.addAttribute("error", "błędny login lub hasło");
    	}


        return "login";
    }
    
    
    @Cacheable("welcome") 
    @GetMapping({"/", "/out"})
    public String welcome1(Model model, HttpSession session) {
    	
    	return MainServicePerformerImpl.performProfileView(UserRepository, UserDelivery, model, session);
    	
    }
       
 
	@GetMapping("/out/{photoName}") 
	public String displayPost(Model model, @PathVariable("photoName") String photoName )
	{
		
		return MainServicePerformerImpl.performPostView(PictureRepository, model, photoName);
	}
	
	@GetMapping("/out/display/{photoName}") 
	public String displayPhoto(Model model, @PathVariable("photoName") String photoName )
	{
		
		return MainServicePerformerImpl.performPhotoView(model, photoName);
	}
	
	
	@GetMapping("/out/videos/{videoName}") 
	public String displayVideo(Model model, @PathVariable("videoName") String videoName )
	{
		
		return MainServicePerformerImpl.performVideoView(PictureRepository, model, videoName);
	}
    
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	
    	UserDelivery.saveUserName(null);
    	
    	SecurityContextHolder.getContext().setAuthentication(null);
    	session.invalidate();
    	
    	System.err.println("wylogowano");
    	    	
        return "redirect:/SocialMediaDemo/login";
    }
  
  @ExceptionHandler(Exception.class)
  public String handleException(Exception ex)
  {
      return "Error";
  }
    


  @GetMapping("/delete")
  public String remover(String zgoda, HttpSession session, Model model)
  {
	  	User user = UserRepository.findById((String) session.getAttribute("user")).get();
		model.addAttribute("user", user);

	  
	  return MainServicePerformerImpl.delete(UserRepository, PictureRepository, zgoda, session, model);
  }
    
  
  // odtad do nowego kontrolera socialControler
  
  @GetMapping("/users")
  public String FindUser(HttpSession session, Model model, String userName)
  {
	  	User user = UserRepository.findById((String) session.getAttribute("user")).get();
		model.addAttribute("user", user);

	  
	  return MainServicePerformerImpl.UsersSearcher(UserRepository, model, userName);
  }
  
  @GetMapping("/users/profile/{userID}")
  public String showUser(HttpSession session, Model model, @PathVariable("userID") String userID)
  {
	  	User user = UserRepository.findById((String) session.getAttribute("user")).get();
		model.addAttribute("user", user);
	  
	  return MainServicePerformerImpl.performUserView(UserRepository, model, userID);
  }
  
  
  
  
  public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public SecurityService getSecurityService() {
		return securityService;
	}


	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}


	public UserSessionProvider getUserDelivery() {
		return UserDelivery;
	}


	public void setUserDelivery(UserSessionProvider userDelivery) {
		UserDelivery = userDelivery;
	}


	public UserRepository getUserRepository() {
		return UserRepository;
	}


	public void setUserRepository(UserRepository userRepository) {
		UserRepository = userRepository;
	}


	public PictureRepository getPictureRepository() {
		return PictureRepository;
	}


	public void setPictureRepository(PictureRepository pictureRepository) {
		PictureRepository = pictureRepository;
	}


	public RegistrationValidator getRegistrationValidator() {
		return RegistrationValidator;
	}


	public void setRegistrationValidator(RegistrationValidator registrationValidator) {
		RegistrationValidator = registrationValidator;
	}


	public MainServicePerformer getMainServicePerformerImpl() {
		return MainServicePerformerImpl;
	}


	public void setMainServicePerformerImpl(MainServicePerformer mainServicePerformerImpl) {
		MainServicePerformerImpl = mainServicePerformerImpl;
	}

  
  
  
  
  
    
}