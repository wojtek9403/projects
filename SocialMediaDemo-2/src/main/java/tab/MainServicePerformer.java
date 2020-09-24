package tab;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface MainServicePerformer 
{
	public String performProfileView(UserRepository UserRepository, UserSessionProvider userDelivery, Model model, HttpSession session);
	
	public String performAdminView(UserRepository UserRepository, Model model, HttpSession session);
	
	public String performPhotoView(PictureRepository PictureRepository, Model model,String photoName);
	
	public String delete(UserRepository UserRepository, PictureRepository PictureRepository, String zgoda, HttpSession session, Model model);
	
	public String photoUploader(UserRepository userRepository, PictureRepository PictureRepository, MultipartFile file, String desc, HttpSession session, Model model);
	
	public String profilePicUploader(UserRepository userRepository, MultipartFile file, HttpSession session, Model model);
	
	public String deleteChosenUser(PictureRepository PictureRepository, UserRepository UserRepository,String userNameToRemove, HttpSession session, Model model);

	String videoUploader(UserRepository userRepository, PictureRepository PictureRepository, MultipartFile file,
			String desc, HttpSession session, Model model);

	String performVideoView(PictureRepository PictureRepository, Model model, String videoName);
}
