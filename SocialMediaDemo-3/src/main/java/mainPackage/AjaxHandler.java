package mainPackage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dbModelsnDAOs.Picture;
import dbModelsnDAOs.PictureRepository;

@RestController
@RequestMapping("/PostHandler")
public class AjaxHandler {

	@Autowired
	PictureRepository PicDao;

	@PostMapping("/give10posts")
	private List<String> giveSth(HttpSession session, @RequestHeader("doWhat") String doWhat) {
		List<String> posts = new ArrayList<>();

		List<Picture> list;

		try {

			if (doWhat.equals("onScroll")) {
				long x = (long) session.getAttribute("p");
				long y = (long) session.getAttribute("q");

				long newP = x - 11;
				long newQ = y - 11;

				session.setAttribute("p", newP);
				session.setAttribute("q", newQ);

				list = PicDao.getNNpics(newP, newQ);

				if (list.isEmpty()) {
					List<String> Empty = new ArrayList<String>();
					Empty.add(" ");

					return Empty;
				}
			} else {
				long p = PicDao.getPictureMaxId();
				long q = PicDao.getPictureMaxId() - 9;

				session.setAttribute("p", p);
				session.setAttribute("q", q);

				list = PicDao.getNNpics(p, q);
			}

			for (Picture x : list) {
				dbModelsnDAOs.User owner = x.getUsers().iterator().next();

				if (x.getOrginalPicPath().endsWith(".mp4")) {
					posts.add("<div class='post'><div class='who'><a id='naglowek' href="
							+ "/SocialMediaDemo/users/profile/" + owner.getUsername() + ">" + "<img id='imgMin' src="
							+ "/" + owner.getProfilePicture() + "/>" + "</a></div>" + "<div class = 'whoName'><label>"
							+ owner.getUsername() + "</label></div>" + "<div id='postContent'>" + "<pre>"
							+ x.getDescription() + "</pre>"
							+ "<video class='videos' id='video' muted='true' height='500px' width='700px'>"
							+ "<source src='/" + x.getOrginalPicPath() + "#t=0.5' type ='video/mp4' /></video>"
							+ "</div></div>");
				} else {
					posts.add("<div class='post'><div class='who'><a id='naglowek' href="
							+ "/SocialMediaDemo/users/profile/" + owner.getUsername() + ">" + "<img id='imgMin' src="
							+ "/" + owner.getProfilePicture() + "/>" + "</a></div>" + "<div class = 'whoName'><label>"
							+ owner.getUsername() + "</label></div>" + "<div id='postContent'>" + "<pre>"
							+ x.getDescription() + "</pre>" + "<img id='contentPic' src=" + "/" + x.getOrginalPicPath()
							+ "/>" + "</div></div>");
				}
			}

		} catch (AopInvocationException ae) {
			List<String> Empty = new ArrayList<String>();
			Empty.add(" ");

			return Empty;
		}

		return posts;
	}

}
