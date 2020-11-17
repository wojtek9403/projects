<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="/css/video.css" rel='stylesheet' type='text/css'>
</head>
<body>

	<div id="container" align="center">
            
		<div id="container2" align="center">

                    
                        <video id = "video" preload = "metadata" class="videos" controls="true" >
                            <source src="/userImg/${video.orginalPicPath}" type = "video/mp4" />
                        </video>
                                               
                        <div id="comments">
                
                             <div class="desc" align="left"> 
                                <p id="opis">${video.description}</p>
                             </div>
                             
                             <div class="content"> 
                                <label>Comments:  </label>
                                
                               <c:forEach var="com" items="${comments}">
                                
                                <div id="post">
                                    <div id="who" align="center">
                                            <a href="#">
                                                <img id="disp" alt="xx" src="/userImg/${com.comentator.profilePicture}">
                                            </a>  
                                        <label>${com.comentator.username}</label>
                                    </div>
                                    <div id="what">
                                       ${com.tresc} 
                                    </div>                                    
                                </div>
                                
                                
                               </c:forEach>

                                
                             </div>
                         </div>
                         
                         <div>
                         	<form method="post" action="/SocialMediaDemo/ComentPhoto">
                         	
                         	<input name = "pic" type = "hidden" value="${video.id}">
                         	
                         	<textarea name = "tresc" rows="3" cols="10"></textarea>
                         	
                         	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />                         	
                         	
                         	<input type = "submit" placeholder="comment">
                         	
                         	</form>
                         	
                         </div>
		</div>
            
	</div>
		<div class = "preLoader" align="center">
            <div id="loader"> </div>
            <label>Loading</label>                   
        </div>

<script type="text/javascript">

	var loader = document.querySelector(".preLoader"); 
	
	window.addEventListener("load", vanish);

	function vanish()
	{
	  	loader.classList.add("disapear");  
	};

</script>
</body>
</html>