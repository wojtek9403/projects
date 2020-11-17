package mainPackage;

import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import buisnessLogic.MainServicePerformer;
import buisnessLogic.MulitComparator;
import buisnessLogic.RegistrationValidator;
import dbModelsnDAOs.CommentsRepository;
import dbModelsnDAOs.FriendsRepository;
import dbModelsnDAOs.NotifyRepository;
import dbModelsnDAOs.PictureRepository;
import dbModelsnDAOs.User;
import dbModelsnDAOs.UserRepository;

@Import({ buisnessLogic.MainServicePerformerImpl.class, buisnessLogic.MulitComparator.class,
		buisnessLogic.RegistrationValidator.class })
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

	private FriendsRepository FriendsRepository;

	private NotifyRepository NotifyRepository;

	private CommentsRepository CommentsRepository;

	public UserController(UserService userService, SecurityService securityService, UserSessionProvider userDelivery,
			dbModelsnDAOs.UserRepository userRepository, dbModelsnDAOs.PictureRepository pictureRepository,
			buisnessLogic.RegistrationValidator registrationValidator, MainServicePerformer mainServicePerformerImpl,
			dbModelsnDAOs.FriendsRepository FriendsRepository, dbModelsnDAOs.NotifyRepository NotifyRepository,
			dbModelsnDAOs.CommentsRepository CommentsRepository) {
		super();
		this.userService = userService;
		this.securityService = securityService;
		UserDelivery = userDelivery;
		UserRepository = userRepository;
		PictureRepository = pictureRepository;
		RegistrationValidator = registrationValidator;
		MainServicePerformerImpl = mainServicePerformerImpl;
		this.FriendsRepository = FriendsRepository;
		this.NotifyRepository = NotifyRepository;
		this.CommentsRepository = CommentsRepository;
	}

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") @Validated User userForm, BindingResult bindingResult) {

		RegistrationValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);
		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

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

		return MainServicePerformerImpl.performProfileView(UserRepository, UserDelivery, model, session,
				MulitComparator);

	}

	@GetMapping("/out/{photoName}")
	public String displayPost(Model model, @PathVariable("photoName") String photoName,
			MulitComparator MulitComparator) {

		return MainServicePerformerImpl.performPostView(PictureRepository, model, photoName, MulitComparator);
	}

	@GetMapping("/out/display/{photoName}")
	public String displayPhoto(Model model, @PathVariable("photoName") String photoName) {

		return MainServicePerformerImpl.performPhotoView(model, photoName);
	}

	@GetMapping("/out/videos/{videoName}")
	public String displayVideo(Model model, @PathVariable("videoName") String videoName,
			MulitComparator MulitComparator) {

		return MainServicePerformerImpl.performVideoView(PictureRepository, model, videoName, MulitComparator);
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
	public String handleException(Exception ex) {
		return "Error";
	}

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

}