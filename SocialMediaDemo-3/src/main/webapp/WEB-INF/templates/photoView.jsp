<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="/css/photoView.css" rel='stylesheet' type='text/css'>
</head>
<body>

	<div id="container" align="center">
            
		<div id="container2" align="center">

                    <a href="/SocialMediaDemo/out/display/${photo.orginalPicPath}">
			<img id="disp" alt="xx" src="/userImg/${photo.orginalPicPath}">
                    </a>   
                        
                         <div id="comments">
                
                             <div class="desc" align="left"> 
                                <p id="opis">${photo.description}</p>
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
                         	
                         	<input name = "pic" type = "hidden" value="${photo.id}">
                         	
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