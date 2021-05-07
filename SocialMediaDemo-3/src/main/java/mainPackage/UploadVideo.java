package mainPackage;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import buisnessLogic.BinaryContentHandler;
import buisnessLogic.MainServicePerformerImpl;
import dbModelsnDAOs.FriendsRepository;
import dbModelsnDAOs.NotifyRepository;
import dbModelsnDAOs.PictureRepository;
import dbModelsnDAOs.User;
import dbModelsnDAOs.UserRepository;

@Import({ buisnessLogic.MainServicePerformerImpl.class })
@Controller
public class UploadVideo {

	private MainServicePerformerImpl MainServicePerformerImpl;

	private PictureRepository PictureRepository;

	private UserRepository UserRepository;

	public UploadVideo(dbModelsnDAOs.PictureRepository pictureRepository, dbModelsnDAOs.UserRepository userRepository,
			NotifyRepository NotifyRepository, FriendsRepository FriendsRepository) {
		super();
		this.MainServicePerformerImpl = new MainServicePerformerImpl(NotifyRepository, FriendsRepository);
		PictureRepository = pictureRepository;
		UserRepository = userRepository;
	}

	@GetMapping("/SocialMediaDemo/videoUpload")
	public String GetFileUpload(Model model, HttpSession sess, @RequestParam(defaultValue = "false") String error) {
		User user = UserRepository.findById((String) sess.getAttribute("user")).get();
		model.addAttribute("user", user);

		if (error.equals("true")) {
			model.addAttribute("err_message", "obsługujemy format mp4 !");
		}

		if (user.getRole().equals("ROLE_ADMIN")) {
			return "AdminvideoUpl";
		}
		return "videoUpl";
	}

	@PostMapping("/videoUpload")
	@Transactional
	public String FileUpload(@RequestParam("file") MultipartFile file, String desc, HttpSession session, Model model) throws IllegalStateException, IOException {
		return MainServicePerformerImpl.videoUploader(UserRepository, PictureRepository, file, desc, session, model);

	}

}
