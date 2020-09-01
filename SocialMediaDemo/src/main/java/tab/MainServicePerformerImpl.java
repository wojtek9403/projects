package tab;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class MainServicePerformerImpl implements MainServicePerformer {

	@Override
	public String performProfileView(UserRepository UserRepository, UserSessionProvider userDelivery, Model model,
			HttpSession session) {

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

		List<String> minPaths = new ArrayList<String>();

		for (int i = 0; i < pics.length; i++) {
			Picture picture = (Picture) pics[i];
			minPaths.add(picture.getMinPicPath());
		}

		model.addAttribute("paths", minPaths);
		model.addAttribute("profile", user.getProfilePicture());
		model.addAttribute("name", user.getUsername());

		return "out";
	}

	@Secured("ROLE_ADMIN")
	@Override
	public String performAdminView(UserRepository UserRepository, Model model, HttpSession session) {

		User user = UserRepository.findById((String) session.getAttribute("user")).get();

		Object[] pics = user.getPictures().toArray();

		List<String> minPaths = new ArrayList<String>();

		for (int i = 0; i < pics.length; i++) {
			Picture picture = (Picture) pics[i];
			minPaths.add(picture.getMinPicPath());
		}

		System.out.println(minPaths);
		model.addAttribute("paths", minPaths);
		model.addAttribute("profile", user.getProfilePicture());
		model.addAttribute("name", user.getUsername());

		return "AdminView";
	}

	@Override
	public String performPhotoView(PictureRepository PictureRepository, Model model, String photoName) {

		String[] name = photoName.split("M!10!nm");

		model.addAttribute("photo", name[1]);

		Picture pic = PictureRepository.findByorginalPicPath(name[1]);
		System.err.println(pic.getDescription());
		model.addAttribute("desc", pic.getDescription());

		return "display";
	}

	@Override
	public String delete(UserRepository UserRepository, PictureRepository PictureRepository, String zgoda,
			HttpSession session, Model model) {

		User user = new User();

		try {
			user = UserRepository.findById((String) session.getAttribute("user")).get();
		} catch (Exception ex) {
			model.addAttribute("err_message", "nie znaleziono");
			return "usunProfil";
		}

		if (zgoda != null) {
			System.err.println(zgoda);

			if (zgoda.equals("on")) {
				File ProfilePicToDelete = new File("userImg/" + user.getProfilePicture());
				ProfilePicToDelete.delete();

				List<Long> listOfPics = new ArrayList<Long>();

				Iterator<Picture> iter = user.getPictures().iterator();

				while (iter.hasNext()) {
					Picture picture = iter.next();

					listOfPics.add(picture.getId());

					String org = picture.getOrginalPicPath();
					String min = picture.getMinPicPath();

					File ThFileToDelete = new File("userImg/" + org);
					ThFileToDelete.delete();

					ThFileToDelete = new File("userImg/" + min);
					ThFileToDelete.delete();

				}

				user.getPictures().clear();
				UserRepository.save(user);
				UserRepository.delete(user);

				for (Long xx : listOfPics) {
					PictureRepository.deleteById(xx);
				}

				return "redirect:/SocialMediaDemo/logout";
			} else {
				if (user.getRole().equals("ROLE_ADMIN")) {
					return "AdminUsunProfil";
				}

				return "usunProfil";
			}
		} else {
			if (user.getRole().equals("ROLE_ADMIN")) {
				return "AdminUsunProfil";
			}
			return "usunProfil";
		}

	}

	@Override
	public String photoUploader(UserRepository userRepository, PictureRepository PictureRepository, MultipartFile file,
			String desc, HttpSession session, Model model) {

		if (!file.isEmpty()) {
			try {
				UUID uuid = UUID.randomUUID();

				String format = file.getOriginalFilename();

				if (format.endsWith(".jpg") || format.endsWith(".png") || format.endsWith(".tif")
						|| format.endsWith(".bmp") || format.endsWith(".emf")) {
					System.out.println(format);
				} else {
					model.addAttribute("err_message", "obsługujemy formaty : jpg, png, emf, bmp, tif !");
					return "main";
				}

				byte[] bytes = file.getBytes();
				String newFilename = "userImg/" + uuid.toString() + format;
				String minNewFilename = "userImg/" + "M!10!nm" + uuid.toString() + format;
				File ThFile = new File(newFilename);
				ThFile.createNewFile();

				File minThFile = new File(minNewFilename);
				minThFile.createNewFile();

				BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(bytes));

				if (originalImage.getWidth() > 1000 || originalImage.getHeight() > 1000) {
					Thumbnails.of(originalImage).size(1000, 1000).toFile(ThFile);
				} else if (originalImage.getWidth() < 1000 || originalImage.getHeight() < 1000) {
					Thumbnails.of(originalImage).size(originalImage.getWidth(), originalImage.getHeight())
							.toFile(ThFile);
				}

				if (originalImage.getWidth() < 200 || originalImage.getHeight() < 200) {
					Thumbnails.of(originalImage).size(originalImage.getWidth(), originalImage.getHeight())
							.toFile(minThFile);
				} else if (originalImage.getWidth() > 200 || originalImage.getHeight() > 200) {
					Thumbnails.of(originalImage).size(200, 200).toFile(minThFile);
				}

				Picture newPic = new Picture();

				try {
					newPic.setId(PictureRepository.getPictureMaxId() + (long) 1);
				} catch (Exception ex) {
					ex.getMessage();
					newPic.setId((long) 0);
				}

				newPic.setOrginalPicPath(ThFile.getName());
				newPic.setMinPicPath(minThFile.getName());
				newPic.setDescription(desc);
				PictureRepository.saveAndFlush(newPic);

				String userName = (String) session.getAttribute("user");
				System.err.println("user z sesji to :" + userName);

				User userZbazy = userRepository.findById(userName).get();

				userZbazy.getPictures().add(newPic);
				userRepository.save(userZbazy);

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
					model.addAttribute("err_message", "obsługujemy formaty : jpg, png, emf, bmp, tif !");
					return "profilePic";
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

	@Secured("ROLE_ADMIN")
	@Override
	public String deleteChosenUser(PictureRepository PictureRepository, UserRepository UserRepository,
			String userNameToRemove, HttpSession session, Model model) {

		if (userNameToRemove == null) {
			return "usuwacz";
		} else {
			User user = new User();

			try {
				user = UserRepository.findById(userNameToRemove).get();
			} catch (Exception ex) {
				model.addAttribute("err_message", "nie znalexiono");
				return "usuwacz";
			}

			if (((String) session.getAttribute("user")).equals(userNameToRemove)) {
				model.addAttribute("err_message", "skorzystaj z możliwości usuń swoje konto");
				return "usuwacz";
			}

			File ProfilePicToDelete = new File("userImg/" + user.getProfilePicture());
			ProfilePicToDelete.delete();

			List<Long> listOfPics = new ArrayList<Long>();

			Iterator<Picture> iter = user.getPictures().iterator();

			while (iter.hasNext()) {
				Picture picture = iter.next();

				listOfPics.add(picture.getId());

				String org = picture.getOrginalPicPath();
				String min = picture.getMinPicPath();

				File ThFileToDelete = new File("userImg/" + org);
				ThFileToDelete.delete();

				ThFileToDelete = new File("userImg/" + min);
				ThFileToDelete.delete();

			}

			user.getPictures().clear();
			UserRepository.save(user);
			UserRepository.delete(user);

			for (Long xx : listOfPics) {
				PictureRepository.deleteById(xx);
			}

			System.err.println("usunięto: " + userNameToRemove);

			return "redirect:/Demo/AdminControll";
		}
	}

}
