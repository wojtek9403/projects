package buisnessLogic;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import dbModelsnDAOs.CommentsRepository;
import dbModelsnDAOs.PictureRepository;
import dbModelsnDAOs.UserRepository;
import mainPackage.UserSessionProvider;

@ComponentScan
public interface MainServicePerformer 
{
	public String performProfileView(UserRepository UserRepository, UserSessionProvider userDelivery, Model model, HttpSession session, MulitComparator MulitComparator);
	
	public String performAdminView(UserRepository UserRepository, Model model, HttpSession session, MulitComparator MulitComparator);
	
	public String performPhotoView(Model model,String photoName);
	
	public String delete(UserRepository UserRepository, PictureRepository PictureRepository, String zgoda, HttpSession session, Model model);
		
	public String profilePicUploader(UserRepository userRepository, MultipartFile file, HttpSession session, Model model);
	
	public String deleteChosenUser(PictureRepository PictureRepository, UserRepository UserRepository,String userNameToRemove, HttpSession session, Model model);

	String videoUploader(UserRepository userRepository, PictureRepository PictureRepository, MultipartFile file,
			String desc, HttpSession session, Model model);

	String performVideoView(PictureRepository PictureRepository, Model model, String videoName, MulitComparator MulitComparator);
	
	String UsersSearcher(UserRepository UserRepository, Model model, String userToFindName, MulitComparator MulitComparator);
	
	public String performUserView(UserRepository UserRepository, Model model, String usersFoundName, HttpSession session, MulitComparator MulitComparator);
	
	public String performPostView(PictureRepository PictureRepository, Model model, String photoName, MulitComparator MulitComparator);

	String photoUploader(UserRepository userRepository, PictureRepository PictureRepository, MultipartFile file,
			String desc, HttpSession session, Model model);
	
	
	public String addUserToFollow(HttpSession session, String toFollow, UserRepository UserRepository);
	
	public String showFriends(HttpSession session, Model model, MulitComparator MulitComparator, UserRepository UserRepository);
	
	public String comentPhoto(HttpSession session, String tresc, String pic, CommentsRepository CommentsRepository,UserRepository UserRepository,PictureRepository PictureRepository);
}
