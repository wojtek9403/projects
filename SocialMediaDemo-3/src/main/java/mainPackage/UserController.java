package mainPackage;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import buisnessLogic.MainServicePerformer;
import buisnessLogic.MulitComparator;
import buisnessLogic.RegistrationValidator;
import dbModelsnDAOs.CommentsRepository;
import dbModelsnDAOs.PictureRepository;
import dbModelsnDAOs.User;
import dbModelsnDAOs.UserRepository;

@Import({ buisnessLogic.MainServicePerformerImpl.class, buisnessLogic.MulitComparator.class,
		buisnessLogic.RegistrationValidator.class})
@RequestMapping("/SocialMediaDemo")
@Controller
@EnableGlobalMethodSecurity(securedEnabled = true)
public class UserController {

	private EmailSender emailSenderImpl;
	
	private TemplateEngine templateEngine;
	
	private UserService userService;

	private SecurityService securityService;

	private UserRepository UserRepository;

	private PictureRepository PictureRepository;

	private RegistrationValidator RegistrationValidator;

	private MainServicePerformer MainServicePerformerImpl;

	private CommentsRepository CommentsRepository;

	public UserController(UserService userService, SecurityService securityService,
			dbModelsnDAOs.UserRepository userRepository, dbModelsnDAOs.PictureRepository pictureRepository,
			buisnessLogic.RegistrationValidator registrationValidator, MainServicePerformer mainServicePerformerImpl,
			dbModelsnDAOs.CommentsRepository CommentsRepository,
			EmailSender emailSenderImpl,
			TemplateEngine templateEngine) {
		super();
		this.userService = userService;
		this.securityService = securityService;
		UserRepository = userRepository;
		PictureRepository = pictureRepository;
		RegistrationValidator = registrationValidator;
		MainServicePerformerImpl = mainServicePerformerImpl;
		this.CommentsRepository = CommentsRepository;
		this.emailSenderImpl =  emailSenderImpl;
		this.templateEngine = templateEngine;
	}

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") @Validated User userForm, BindingResult bindingResult) throws IOException
	{
		
		String orgPass = userForm.getPassword();

		RegistrationValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);
		
		try {
			Context context = new Context();
			context.setVariable("header", "Witaj "+ userForm.getName());
			context.setVariable("title", "Dziękujemy za zarejestrowanie się w serwisie");
			context.setVariable("description", "W przyszłości proces ten posłuży do dwuetapowego logowania. Tymczasem "
					+ "dziękujemy za review tej aplikacji, twój poświęcony czas pozwoli nam dopracować aplikację !"
					+ " Pozdrawiam serdecznie !");		
			String body = templateEngine.process("template.html", context);		
			emailSenderImpl.sendEmail(userForm.getEmail(), "SocialMediaDemo.pl", body);
		}catch (Exception e) {
			System.err.println("nie udało się wysłać wiadomości powitalnej - sprawdz poprawność adresu @ lub wyłącz lokalny antywirus i spróbuj ponownie");
		}
	
		securityService.autoLogin(userForm.getUsername(), orgPass); 
		
		File node = new File("userImg/"+userForm.getUsername().charAt(0));
		if (!node.exists()){
		    node.mkdir();
		}
		
		File userResource = new File("userImg/"+userForm.getUsername().charAt(0)+ "/" + userForm.getUsername());
		if (!userResource.exists()){
			userResource.mkdir();
		}
		
		File pics = new File("userImg/"+userForm.getUsername().charAt(0)+ "/" + userForm.getUsername() + "/pic");
		pics.mkdir();
		File vids = new File("userImg/"+userForm.getUsername().charAt(0)+ "/" + userForm.getUsername() + "/vids");
		vids.mkdir();
		File other = new File("userImg/"+userForm.getUsername().charAt(0)+ "/" + userForm.getUsername() + "/other");
		other.mkdir();
		
		return "redirect:/SocialMediaDemo/out";
	}

	@GetMapping("/login")
	public String login(Model model, String error) {
		if (error != null) {
			model.addAttribute("error", "błędny login lub hasło");
		}

		return "login";
	}

	@Cacheable("welcome")
	@GetMapping({ "/", "/out" })
	public String welcome1(Model model, HttpSession session, MulitComparator MulitComparator) {

		return MainServicePerformerImpl.performProfileView(UserRepository, model, session,
				MulitComparator);

	}

	@GetMapping("/out/{userImg}/{upDir}/{mainDir}/{pic}/{name}")
	public String displayPost(Model model,  @PathVariable("userImg") String userImg, @PathVariable("upDir") String upDir, @PathVariable("mainDir") String mainDir,  @PathVariable("pic") String pic, @PathVariable("name") String name,
			MulitComparator MulitComparator) {
		
		return MainServicePerformerImpl.performPostView(PictureRepository, model, userImg, upDir,mainDir,pic,name, MulitComparator);
	}

	@GetMapping("/out/display/{userImg}/{upDir}/{mainDir}/{pic}/{name}")
	public String displayPhoto(Model model, @PathVariable("userImg") String userImg, @PathVariable("upDir") String upDir, @PathVariable("mainDir") String mainDir,  @PathVariable("pic") String pic, @PathVariable("name") String name) {

		return MainServicePerformerImpl.performPhotoView(PictureRepository, model, userImg, upDir,mainDir,pic,name);
	}

	@GetMapping("/out/videos/{userImg}/{upDir}/{mainDir}/{vids}/{name}")
	public String displayVideo(Model model, @PathVariable("userImg") String userImg, @PathVariable("upDir") String upDir, @PathVariable("mainDir") String mainDir, @PathVariable("vids") String vids, @PathVariable("name") String name,
			MulitComparator MulitComparator) {

		return MainServicePerformerImpl.performVideoView(PictureRepository, model, userImg, upDir, mainDir, vids, name, MulitComparator);
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		SecurityContextHolder.getContext().setAuthentication(null);
		session.invalidate();

		System.err.println("wylogowano");

		return "redirect:/SocialMediaDemo/login";
	}

//	@ExceptionHandler(Exception.class)
//	public String handleException(Exception ex) {
//		return "Error";
//	}

	// needs to be updated !
	// needs to delete friends, comments etc.
	@GetMapping("/delete")
	public String remover(String zgoda, HttpSession session, Model model) {
		User user = UserRepository.findById((String) session.getAttribute("user")).get();
		model.addAttribute("user", user);

		return MainServicePerformerImpl.delete(UserRepository, PictureRepository, zgoda, session, model);
	}

	@GetMapping("/users")
	public String FindUser(HttpSession session, Model model, String userName, MulitComparator MulitComparator) {
		User user = UserRepository.findById((String) session.getAttribute("user")).get();
		model.addAttribute("user", user);
		;

		return MainServicePerformerImpl.UsersSearcher(UserRepository, model, userName, MulitComparator);
	}

	@GetMapping("/users/profile/{userID}")
	public String showUser(HttpSession session, Model model, @PathVariable("userID") String userID,
			MulitComparator MulitComparator) {
		User user = UserRepository.findById((String) session.getAttribute("user")).get();
		model.addAttribute("user", user);

		return MainServicePerformerImpl.performUserView(UserRepository, model, userID, session, MulitComparator);
	}

	@PostMapping("addUserToFollow")
	public String addToFollow(HttpSession session, String toFollow) {
		return MainServicePerformerImpl.addUserToFollow(session, toFollow, UserRepository);
	}

	@GetMapping("user/friends")
	public String showFriends(HttpSession session, Model model, MulitComparator MulitComparator) {
		return MainServicePerformerImpl.showFriends(session, model, MulitComparator, UserRepository);
	}

	@PostMapping("ComentPhoto")
	public String comentPhoto(HttpSession session, String tresc, String pic) {
		return MainServicePerformerImpl.comentPhoto(session, tresc, pic, CommentsRepository, UserRepository,
				PictureRepository);
	}
	
	@GetMapping("/table")
	public String tab(Model model, HttpSession session)
	{	
		return MainServicePerformerImpl.tablica(UserRepository, model, session);
	}

}