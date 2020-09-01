package tab;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;


@Controller
public class UploadController {
	
	private MainServicePerformer MainServicePerformerImpl;
	
	private PictureRepository PictureRepository;
	
	private UserRepository UserRepository;
	

	public UploadController(MainServicePerformer mainServicePerformerImpl, tab.PictureRepository pictureRepository,
			tab.UserRepository userRepository) {
		super();
		MainServicePerformerImpl = mainServicePerformerImpl;
		PictureRepository = pictureRepository;
		UserRepository = userRepository;
	}

	@GetMapping("/upload")
	public String GetFileUpload(Model model, HttpSession sess)
	{
		User user = UserRepository.findById((String) sess.getAttribute("user")).get();
		model.addAttribute("profile", user.getProfilePicture());
		model.addAttribute("name", user.getUsername());
		if(user.getRole().equals("ROLE_ADMIN"))
		{
			return "AdminMain";
		}
		return "main";
	}
	
	@PostMapping("/upload")
	@Transactional
	public String FileUpload(@RequestParam("file") MultipartFile file, String desc, HttpSession session, Model model) throws IllegalStateException, IOException
	{
		return  MainServicePerformerImpl.photoUploader(UserRepository, PictureRepository, file, desc, session, model);

	}

}
