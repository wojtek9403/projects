package mainPackage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import buisnessLogic.MainServicePerformer;
import dbModelsnDAOs.User;
import dbModelsnDAOs.UserRepository;
import net.coobird.thumbnailator.Thumbnails;

@Import({ buisnessLogic.MainServicePerformerImpl.class })
@Controller
public class UploadProfile {

	private MainServicePerformer MainServicePerformerImpl;

	private UserRepository userRepository;

	public UploadProfile(MainServicePerformer mainServicePerformerImpl, UserRepository userRepository) {
		super();
		MainServicePerformerImpl = mainServicePerformerImpl;
		this.userRepository = userRepository;
	}

	@GetMapping("/uploadProfilePic")
	public String GetFileUpload(Model model, HttpSession sess, @RequestParam(defaultValue = "false") String error) {
		User user = userRepository.findById((String) sess.getAttribute("user")).get();
		model.addAttribute("user", user);

		if (error.equals("true")) {
			model.addAttribute("err_message", "obsługujemy formaty : jpg, png, emf, bmp, tif !");
		}

		if (user.getRole().equals("ROLE_ADMIN")) {
			return "AdminProfilePic";
		}

		return "profilePic";
	}

	@PostMapping("/uploadProfilePic")
	@Transactional
	public String FileUpload(@RequestParam("file") MultipartFile file, HttpSession session, Model model)
			throws IllegalStateException, IOException {
		return MainServicePerformerImpl.profilePicUploader(userRepository, file, session, model);
	}
}
