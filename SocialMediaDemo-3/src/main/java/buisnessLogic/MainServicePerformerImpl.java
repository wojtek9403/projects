package buisnessLogic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import dbModelsnDAOs.Comments;
import dbModelsnDAOs.CommentsRepository;
import dbModelsnDAOs.Friends;
import dbModelsnDAOs.FriendsRepository;
import dbModelsnDAOs.NotifyRepository;
import dbModelsnDAOs.Picture;
import dbModelsnDAOs.PictureRepository;
import dbModelsnDAOs.User;
import dbModelsnDAOs.UserRepository;
import mainPackage.UserSessionProvider;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class MainServicePerformerImpl implements MainServicePerformer {

	private NotifyRepository NotifyRepository;
	private FriendsRepository FriendsRepository;

	public MainServicePerformerImpl(dbModelsnDAOs.NotifyRepository notifyRepository,
			dbModelsnDAOs.FriendsRepository FriendsRepository) {
		super();
		NotifyRepository = notifyRepository;
		this.FriendsRepository = FriendsRepository;
	}

	@Override
	public String performProfileView(UserRepository UserRepository, UserSessionProvider userDelivery, Model model,
			HttpSession session, MulitComparator MulitComparator) {

		session.setMaxInactiveInterval(120);

		if (session.getAttribute("user") != null) {
			System.err.println(session.getAttribute("user"));
			userDelivery.saveUserName(null);
		} else {
			System.err.println(session.getAttribute("user"));
			session.setAttribute("user", userDelivery.getUserName());
			userDelivery.saveUserName(null);
		}

		User user = UserRepository.findById((String) session.getAttribute("user")).get();

		if (user.getRole().equals("ROLE_ADMIN")) {
			return "redirect:/Demo/AdminControll";
		}

		Object[] pics = user.getPictures().toArray();

		List<Picture> minPaths = new ArrayList<Picture>();
		List<Picture> minVideoPaths = new ArrayList<Picture>();

		for (int i = 0; i < pics.length; i++) {
			Picture picture = (Picture) pics[i];

			if (picture.getOrginalPicPath().endsWith(".mp4")) {
				minVideoPaths.add(picture);
			} else {
				minPaths.add(picture);
			}
		}

		minPaths.sort(MulitComparator);
		minVideoPaths.sort(MulitComparator);

		user.updateNotifs(model);
		model.addAttribute("videoPaths", minVideoPaths);
		model.addAttribute("paths", minPaths);
		model.addAttribute("user", user);

		return "out";
	}

	@Secured("ROLE_ADMIN")
	@Override
	public String performAdminView(UserRepository UserRepository, Model model, HttpSession session,
			MulitComparator MulitComparator) {

		User user = UserRepository.findById((String) session.getAttribute("user")).get();

		Object[] pics = user.getPictures().toArray();

		List<Picture> minPaths = new ArrayList<Picture>();
		List<Picture> minVideoPaths = new ArrayList<Picture>();

		for (int i = 0; i < pics.length; i++) {
			Picture picture = (Picture) pics[i];

			if (picture.getOrginalPicPath().endsWith(".mp4")) {
				minVideoPaths.add(picture);
			} else {
				minPaths.add(picture);
			}
		}

		minPaths.sort(MulitComparator);
		minVideoPaths.sort(MulitComparator);

		user.updateNotifs(model);
		model.addAttribute("videoPaths", minVideoPaths);
		model.addAttribute("paths", minPaths);
		model.addAttribute("user", user);

		return "AdminView";
	}

	@Override
	public String performPhotoView(Model model, String photoName) {

		model.addAttribute("photo", photoName);

		return "display";
	}

	@Override
	public String performPostView(PictureRepository PictureRepository, Model model, String photoName,
			MulitComparator MulitComparator) {

		Picture pic = PictureRepository.findByorginalPicPath(photoName);

		List<Comments> com = new ArrayList<Comments>(pic.getMyComments());

		com.sort(MulitComparator);

		model.addAttribute("photo", pic);
		model.addAttribute("comments", com);

		return "photoView";
	}

	// needs to be updated !
	// needs to delete friends, comments etc.
	@Override
	public String delete(UserRepository UserRepository, PictureRepository PictureRepository, String zgoda,
			HttpSession session, Model model) {

//		User user = new User();
//
//		try {
//			user = UserRepository.findById((String) session.getAttribute("user")).get();
//		} catch (Exception ex) {
//			model.addAttribute("err_message", "nie znaleziono");
//			return "usunProfil";
//		}
//
//		if (zgoda != null) {
//			System.err.println(zgoda);
//
//			if (zgoda.equals("on")) {
//				File ProfilePicToDelete = new File("userImg/" + user.getProfilePicture());
//				ProfilePicToDelete.delete();
//
//				List<Long> listOfPics = new ArrayList<Long>();
//
//				Iterator<Picture> iter = user.getPictures().iterator();
//
//				while (iter.hasNext()) {
//					Picture picture = iter.next();
//
//					listOfPics.add(picture.getId());
//
//					String org = picture.getOrginalPicPath();
//
//					File ThFileToDelete = new File("userImg/" + org);
//					ThFileToDelete.delete();
//
//				}
//
//				user.getPictures().clear();
//				UserRepository.save(user);
//				UserRepository.delete(user);
//
//				for (Long xx : listOfPics) {
//					PictureRepository.deleteById(xx);
//				}
//
//				return "redirect:/SocialMediaDemo/logout";
//			} else {
//				if (user.getRole().equals("ROLE_ADMIN")) {
//					return "AdminUsunProfil";
//				}
//
//				return "usunProfil";
//			}
//		} else {
//			if (user.getRole().equals("ROLE_ADMIN")) {
//				return "AdminUsunProfil";
//			}
		return "usunProfil";
//		}

	}

	@Override
	public String photoUploader(UserRepository userRepository, PictureRepository PictureRepository, MultipartFile file,
			String desc, HttpSession session, Model model) {

		if (!file.isEmpty()) {
			try {

				String format = file.getOriginalFilename();

				if (format.endsWith(".jpg") || format.endsWith(".png") || format.endsWith(".tif")
						|| format.endsWith(".bmp") || format.endsWith(".emf")) {
					System.out.println(format);
				} else {
					return "redirect:/upload?error=true";
				}

				UUID uuid = UUID.randomUUID();
				byte[] bytes = file.getBytes();
				String newFilename = "userImg/" + uuid.toString() + format;
				File ThFile = new File(newFilename);
				ThFile.createNewFile();

				BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(bytes));

				if (originalImage.getWidth() > 1000 || originalImage.getHeight() > 1000) {
					Thumbnails.of(originalImage).size(1000, 1000).toFile(ThFile);
				} else if (originalImage.getWidth() < 1000 || originalImage.getHeight() < 1000) {
					Thumbnails.of(originalImage).size(originalImage.getWidth(), originalImage.getHeight())
							.toFile(ThFile);
				}

				Picture newPic = new Picture();

				try {
					newPic.setId(PictureRepository.getPictureMaxId() + (long) 1);
				} catch (Exception ex) {
					ex.getMessage();
					newPic.setId((long) 0);
				}

				newPic.setOrginalPicPath(ThFile.getName());
				newPic.setDescription(desc);
				PictureRepository.saveAndFlush(newPic);

				String userName = (String) session.getAttribute("user");
				System.err.println("user z sesji to :" + userName);

				User userZbazy = userRepository.findById(userName).get();

				userZbazy.getPictures().add(newPic);
				userRepository.save(userZbazy);

				List<String> toNotify = FriendsRepository.getAllWhoFollowsMe(userZbazy.getUsername());

				for (String x : toNotify) {

					User owner = userRepository.findById(x).get();

					userZbazy.notify(userZbazy.getUsername(), userZbazy.getName(), x, " dodał(a) ", owner,
							NotifyRepository, " zdjęcie ", ThFile.getName());
				}

				// userZbazy.notify(userZbazy, " dodał(a) ", " zdjęcie ",
				// "videos/"+ThFile.getName(), userRepository, FriendsRepository,
				// NotifyRepository);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("pusty plik");
		}

		return "redirect:/SocialMediaDemo/out";

	}

	@Override
	public String profilePicUploader(UserRepository userRepository, MultipartFile file, HttpSession session,
			Model model) {

		if (!file.isEmpty()) {
			try {
				String format = file.getOriginalFilename();

				if (format.endsWith(".jpg") || format.endsWith(".png") || format.endsWith(".tif")
						|| format.endsWith(".bmp") || format.endsWith(".emf")) {
					System.out.println(format);
				} else {
					return "redirect:/uploadProfilePic?error=true";
				}

				String userName = (String) session.getAttribute("user");
				User userZbazy0 = userRepository.findById(userName).get();

				if (userZbazy0.getProfilePicture() != null) {
					File ThFileToDelete = new File("userImg/" + userZbazy0.getProfilePicture());
					ThFileToDelete.delete(); // działa usuwa plik, nalezy w nazwie wskazac bezpoosredni katalog/nazwa
				}

				UUID uuid = UUID.randomUUID();

				byte[] bytes = file.getBytes();
				String newFilename = "userImg/" + "profilePic" + uuid.toString() + format;
				File ThFile = new File(newFilename);
				ThFile.createNewFile();

				BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(bytes));

				Thumbnails.of(originalImage).size(150, 150).toFile(ThFile);

				userZbazy0.setProfilePicture(ThFile.getName());
				userRepository.save(userZbazy0);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("pusty plik");
		}

		return "redirect:/SocialMediaDemo/out";

	}

	// needs to be updated !
	// needs to delete friends, comments etc.
	@Secured("ROLE_ADMIN")
	@Override
	public String deleteChosenUser(PictureRepository PictureRepository, UserRepository UserRepository,
			String userNameToRemove, HttpSession session, Model model) {

//		if (userNameToRemove == null) {
//			return "usuwacz";
//		} else {
//			User user = new User();
//
//			try {
//				user = UserRepository.findById(userNameToRemove).get();
//			} catch (Exception ex) {
//				model.addAttribute("err_message", "nie znalexiono");
//				return "usuwacz";
//			}
//
//			if (((String) session.getAttribute("user")).equals(userNameToRemove)) {
//				model.addAttribute("err_message", "skorzystaj z możliwości usuń swoje konto");
//				return "usuwacz";
//			}
//
//			File ProfilePicToDelete = new File("userImg/" + user.getProfilePicture());
//			ProfilePicToDelete.delete();
//
//			List<Long> listOfPics = new ArrayList<Long>();
//
//			Iterator<Picture> iter = user.getPictures().iterator();
//
//			while (iter.hasNext()) {
//				Picture picture = iter.next();
//
//				listOfPics.add(picture.getId());
//
//				File ThFileToDelete = new File("userImg/" + picture.getOrginalPicPath());
//				ThFileToDelete.delete();
//
//			}
//
//			user.getPictures().clear();
//			UserRepository.save(user);
//			UserRepository.delete(user);
//
//			for (Long xx : listOfPics) {
//				PictureRepository.deleteById(xx);
//			}
//
//			System.err.println("usunięto: " + userNameToRemove);
//
		return "redirect:/Demo/AdminControll";
	}
// }	

	@Override
	public String videoUploader(UserRepository userRepository, PictureRepository PictureRepository, MultipartFile file,
			String desc, HttpSession session, Model model) {

		if (!file.isEmpty()) {
			try {
				UUID uuid = UUID.randomUUID();

				String format = file.getOriginalFilename();

				if (format.endsWith(".mp4")) {
					System.out.println(format);
				} else {
					return "redirect:/SocialMediaDemo/videoUpload?error=true";
				}

				String newFilename = "userImg/" + uuid.toString() + format;
				File ThFile = new File(newFilename);
				ThFile.createNewFile();

				final int buffer = 4 * 1024;
				FileInputStream in = (FileInputStream) file.getInputStream();
				FileOutputStream out1 = new FileOutputStream(ThFile);

				try {
					byte[] bytes = new byte[buffer];
					while (in.available() != 0) {
						in.read(bytes);
						out1.write(bytes);

					}
					out1.close();

				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				try {

				} catch (Exception ex) {
					System.out.println();
				}

				Picture newPic = new Picture();

				try {
					newPic.setId(PictureRepository.getPictureMaxId() + (long) 1);
				} catch (Exception ex) {
					ex.getMessage();
					newPic.setId((long) 0);
				}

				newPic.setOrginalPicPath(ThFile.getName());
				newPic.setDescription(desc);
				PictureRepository.saveAndFlush(newPic);

				String userName = (String) session.getAttribute("user");
				User userZbazy = userRepository.findById(userName).get();

				userZbazy.getPictures().add(newPic);
				userRepository.save(userZbazy);

				List<String> toNotify = FriendsRepository.getAllWhoFollowsMe(userZbazy.getUsername());

				for (String x : toNotify) {
					User owner = userRepository.findById(x).get();

					userZbazy.notify(userZbazy.getUsername(), userZbazy.getName(), x, " dodał(a) ", owner,
							NotifyRepository, " film ", "videos/" + ThFile.getName());
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("pusty plik");
		}

		return "redirect:/SocialMediaDemo/out";

	}

	@Override
	public String performVideoView(PictureRepository PictureRepository, Model model, String videoName,
			MulitComparator MulitComparator) {

		Picture pic = PictureRepository.findByorginalPicPath(videoName);

		List<Comments> com = new ArrayList<Comments>(pic.getMyComments());

		com.sort(MulitComparator);
		model.addAttribute("video", pic);
		model.addAttribute("comments", com);

		return "videoDisp";
	}

	public String UsersSearcher(UserRepository UserRepository, Model model, String userToFindName,
			MulitComparator MulitComparator) {

		userToFindName.toLowerCase();

		String[] q = userToFindName.split(" ");
		List<User> users = new ArrayList<User>();
		Set<User> setOfUsers = new HashSet<User>();

		if (q.length == 2) {
			try {

				setOfUsers.addAll(UserRepository.findAllByNameAndSurname(q[0], q[1]));

				setOfUsers.addAll(UserRepository.findAllByNameAndSurname(q[1], q[0]));

			} catch (Exception ex) {
				System.err.println("not found by Name and Surname !");
			}
		}

		if (setOfUsers.size() == 0) {

			for (String xx : q) {

				try {

					setOfUsers.addAll(UserRepository.findAllByName(xx));

				} catch (Exception ex) {
					System.err.println("not found by Name !");
				}
				try {

					setOfUsers.addAll(UserRepository.findAllBySurname(xx));

				} catch (Exception ex) {
					System.err.println("not found by Surname !");
				}
			}

		}

		users.addAll(setOfUsers);
		users.sort(MulitComparator);
		model.addAttribute("count", users.size());
		model.addAttribute("Users", users);

		return "UsersByName";
	}

	public String performUserView(UserRepository UserRepository, Model model, String usersFoundName,
			HttpSession session, MulitComparator MulitComparator) {

		User user = UserRepository.findById(usersFoundName).get();
		User currentUser = UserRepository.findById((String) session.getAttribute("user")).get();

		if (user.getUsername().equals(currentUser.getUsername())) {
			return "redirect:/SocialMediaDemo/out";
		}

		Object[] pics = user.getPictures().toArray();

		List<Picture> minPaths = new ArrayList<Picture>();
		List<Picture> minVideoPaths = new ArrayList<Picture>();

		for (int i = 0; i < pics.length; i++) {
			Picture picture = (Picture) pics[i];

			if (picture.getOrginalPicPath().endsWith(".mp4")) {
				minVideoPaths.add(picture);
			} else {
				minPaths.add(picture);
			}
		}

		minPaths.sort(MulitComparator);
		minVideoPaths.sort(MulitComparator);

		if (!currentUser.getFriends().contains(user)) {
			model.addAttribute("follow", "Obserwuj");
		} else {
			model.addAttribute("follow", "Nie obserwuj");
		}

		model.addAttribute("videoPaths", minVideoPaths);
		model.addAttribute("paths", minPaths);
		model.addAttribute("userDetails", user);

		return "SthAccount";
	}

//-----------------------------------------------------------------------------------------------------------//
	public String addUserToFollow(HttpSession session, String toFollow, UserRepository UserRepository) {

		User user = UserRepository.findById((String) session.getAttribute("user")).get();
		User newFriend = UserRepository.findById(toFollow).get();

		if (!user.getFriends().contains(UserRepository.findById(toFollow).get())) {
			user.getFriends().add(newFriend);
			Friends friends = new Friends();
			friends.setUserName(user.getUsername());
			friends.setFriendName(newFriend.getUsername());
			FriendsRepository.saveAndFlush(friends);
			UserRepository.save(user);

			user.notify(user.getUsername(), user.getName(), newFriend.getUsername(), " obserwuje Cię", newFriend,
					NotifyRepository, "", "");

		} else {
			user.getFriends().remove(newFriend);
			FriendsRepository.delete(FriendsRepository.getFriendByUserNameAndFriendsName(user.getUsername(), toFollow));
		}

		return "redirect:/SocialMediaDemo/users/profile/" + toFollow;

	}

	public String showFriends(HttpSession session, Model model, MulitComparator MulitComparator,
			UserRepository UserRepository) {
		User user = UserRepository.findById((String) session.getAttribute("user")).get();

		List<User> friends = new ArrayList<User>(user.getFriends());

		friends.sort(MulitComparator);

		model.addAttribute("Users", friends);
		model.addAttribute("user", user);

		return "iFollow";
	}

	public String comentPhoto(HttpSession session, String tresc, String pic, CommentsRepository CommentsRepository,
			UserRepository UserRepository, PictureRepository PictureRepository) {
		Picture picture = PictureRepository.findById(Long.parseLong(pic)).get();
		User user = UserRepository.findById((String) session.getAttribute("user")).get(); // user ktory oglada foto
																							// (niekoniecznie wlascicel)

		Comments com = new Comments();
		com.setTresc(tresc);
		com.setComentator(user);
		com.setPicture(picture);
		CommentsRepository.save(com);
		picture.getMyComments().add(com);
		User owner = picture.getUsers().iterator().next();

		if (picture.getOrginalPicPath().endsWith(".mp4")) {
			System.err.println("mp4");
			user.notify(user.getUsername(), user.getName(), owner.getUsername(), " skomentował(a) ", owner,
					NotifyRepository, " film ", "videos/" + picture.getOrginalPicPath());
			return "redirect:/SocialMediaDemo/out/videos/" + picture.getOrginalPicPath();
		} else {
			user.notify(user.getUsername(), user.getName(), owner.getUsername(), " skomentował(a) ", owner,
					NotifyRepository, " zdjęcie ", picture.getOrginalPicPath());
			return "redirect:/SocialMediaDemo/out/" + picture.getOrginalPicPath();

		}

	}

}
