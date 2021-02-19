package mainPackage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.internal.build.AllowSysOut;
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
	private List<String> giveSth(HttpSession session, @RequestHeader("doWhat") String doWhat)
	{	
		List<String> posts = new ArrayList<>();
		
		List <Picture> list;	
		
		if(doWhat.equals("onScroll"))
		{		
			long x = (long) session.getAttribute("p");
			long y = (long) session.getAttribute("q");
			
			long newP = x-11;
			long newQ = y-11;
			
			session.setAttribute("p", newP);
			session.setAttribute("q", newQ);
			
			list = PicDao.getNNpics(newP, newQ);
			if(list.isEmpty()) return null;
						
		}
		else
		{
			
			long p = PicDao.getPictureMaxId();
			long q = PicDao.getPictureMaxId()-9;
			
			session.setAttribute("p", p);
			session.setAttribute("q", q);		
			
			list = PicDao.getNNpics(p, q);
		}
	
	
		for(Picture x : list)
		{
			dbModelsnDAOs.User owner = x.getUsers().iterator().next();
			
			posts.add("<div class='post'><div class='who'><img id='imgMin' src=" +"/userImg/"+owner.getProfilePicture()+ "/><a id='naglowek' href="+"/SocialMediaDemo/users/profile/"+owner.getUsername()+">"+ owner.getUsername()+"</a></div>" 
				+ "<div id='postContent'>"
				+ "<img id='contentPic' src=" + "/userImg/" + x.getOrginalPicPath() +"/><p>"+ x.getDescription()+ "</p>"
				+ "</div></div>");
		}
		
		return posts;
	}

}
