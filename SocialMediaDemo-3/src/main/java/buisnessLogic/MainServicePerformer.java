package buisnessLogic;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import dbModelsnDAOs.CommentsRepository;
import dbModelsnDAOs.PictureRepository;
import dbModelsnDAOs.UserRepository;

@ComponentScan
public interface MainServicePerformer {
	public String performProfileView(UserRepository UserRepository, Model model,
			HttpSession session, MulitComparator MulitComparator);

	public String performAdminView(UserRepository UserRepository, Model model, HttpSession session,
			MulitComparator MulitComparator);

	public String performPhotoView(PictureRepository PictureRepository, Model model, String userImg, String upDir, String mainDir, String pic, String name);

	public String delete(UserRepository UserRepository, PictureRepository PictureRepository, String zgoda,
			HttpSession session, Model model);

	public String profilePicUploader(UserRepository userRepository, MultipartFile file, HttpSession session,
			Model model);

	public String deleteChosenUser(PictureRepository PictureRepository, UserRepository UserRepository,
			String userNameToRemove, HttpSession session, Model model);

	String videoUploader(UserRepository userRepository, PictureRepository PictureRepository, MultipartFile file,
			String desc, HttpSession session, Model model);

	String performVideoView(PictureRepository PictureRepository, Model model, String userImg, String upDir, String mainDir, String vids, String name,
			MulitComparator MulitComparator);

	String UsersSearcher(UserRepository UserRepository, Model model, String userToFindName,
			MulitComparator MulitComparator);

	public String performUserView(UserRepository UserRepository, Model model, String usersFoundName,
			HttpSession session, MulitComparator MulitComparator);

	public String performPostView(PictureRepository PictureRepository, Model model, String userImg, String upDir, String mainDir, String pic, String name,
			MulitComparator MulitComparator);

	String photoUploader(UserRepository userRepository, PictureRepository PictureRepository, MultipartFile file,
			String desc, HttpSession session, Model model);

	public String addUserToFollow(HttpSession session, String toFollow, UserRepository UserRepository);

	public String showFriends(HttpSession session, Model model, MulitComparator MulitComparator,
			UserRepository UserRepository);

	public String comentPhoto(HttpSession session, String tresc, String pic, CommentsRepository CommentsRepository,
			UserRepository UserRepository, PictureRepository PictureRepository);
	
	public String tablica(UserRepository UserRepository, Model model, HttpSession session);
}
