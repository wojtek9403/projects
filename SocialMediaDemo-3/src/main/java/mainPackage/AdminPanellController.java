package mainPackage;

import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import buisnessLogic.MainServicePerformerImpl;
import buisnessLogic.MulitComparator;
import dbModelsnDAOs.FriendsRepository;
import dbModelsnDAOs.NotifyRepository;
import dbModelsnDAOs.PictureRepository;
import dbModelsnDAOs.User;
import dbModelsnDAOs.UserRepository;

@Import({ buisnessLogic.MainServicePerformerImpl.class })
@Controller
@RequestMapping("/Demo")
public class AdminPanellController {

	private UserRepository UserRepository;

	private PictureRepository PictureRepository;

	private MainServicePerformerImpl MainServicePerformerImpl;

	public AdminPanellController(dbModelsnDAOs.UserRepository userRepository,
			dbModelsnDAOs.PictureRepository pictureRepository, NotifyRepository NotifyRepository, FriendsRepository FriendsRepository) {
		super();
		UserRepository = userRepository;
		PictureRepository = pictureRepository;
		this.MainServicePerformerImpl = new MainServicePerformerImpl(NotifyRepository,FriendsRepository);
	}

	@Secured("ROLE_ADMIN")
	@Cacheable("AdminViewx")
	@GetMapping("/AdminControll")
	public String AdminControllPanel(Model model, HttpSession session, MulitComparator MulitComparator) {

		return MainServicePerformerImpl.performAdminView(UserRepository, model, session, MulitComparator);
	}

	// needs to be updated !
	@Secured("ROLE_ADMIN")
	@GetMapping("/usuwacz")
	public String remover(String userNameToRemove, HttpSession session, Model model) {
		User user = UserRepository.findById((String) session.getAttribute("user")).get();
		model.addAttribute("user", user);

		return MainServicePerformerImpl.deleteChosenUser(PictureRepository, UserRepository, userNameToRemove, session,
				model);
	}

}
