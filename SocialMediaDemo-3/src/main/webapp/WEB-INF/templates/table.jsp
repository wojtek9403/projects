<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="/css/AdminMain.css" rel='stylesheet' type='text/css'>
</head>
<body onload="ajaxInit('heather');" onscroll="onScrollAppend();">

	<div id="container" align="center">

		<div id="heather1">

			<div class="css-3d-text">AntiSocialMedia.com</div>
			
			<div id="browser">
				<form method="get" action="/SocialMediaDemo/users">
					<input name = "userName" type = "text" placeholder="Wyszukaj" /> <input type="submit" value="Szukaj"/>
				</form>
			</div>
			<div class = "notifs">
                            
                            <label>Powiadomienia</label>   <div class = "circle">!</div> 
                            
                            <div class="list">
                                
                                <c:forEach var="no" items="${notifs}">

                                    <div class = "notifItem">
                                        <a href="/SocialMediaDemo/users/profile/${no.nadawcaID}">${no.nadawca}<a> ${no.tresc} <a href="/SocialMediaDemo/out/${no.path}">${no.what}</a> !
                                    </div>
                                                                   
								</c:forEach>
                                 
                            </div>
                            
                        </div>
			
			<div class="divMenu">
				Menu
				<ul>
					<li class="liBottom"><a class="buttonLi"
						href="/SocialMediaDemo/out">Strona profilowa</a></li>
					<li class="liBottom"><a class="buttonLi"
						href="/uploadProfilePic">Dodaj zdjęcie profilowe</a></li>
					<li class="liBottom"><a class="buttonLi" href="/upload">Dodaj
							zdjęcie</a></li>
					<li class="liBottom"><a class="buttonLi"
						href="/SocialMediaDemo/delete">Usun profil</a></li>
					<li class="liBottom"><a class="buttonLi"
						href="/SocialMediaDemo/videoUpload">Dodaj film</a></li>
						<li class="liBottom"><a class="buttonLi"
						href="/SocialMediaDemo/user/friends">Obserwowani</a></li>
						<li class="liBottom"><a class="buttonLi"
						href="/SocialMediaDemo/table">Aktualności</a></li>
				</ul>
			</div>


			<div class="divbutton2">
				<a class="buttonW" href="/SocialMediaDemo/logout">Wyloguj</a>
			</div>




		</div>


		<div id="header2">

			<a class="profileLink" href="/uploadProfilePic">
				<div class="profilowe">
					<img alt="upload profile pic" src="/userImg/${user.profilePicture}">
				</div>
			</a>

			<div class="details">
				<label>${user.name}</label>
				<label>${user.surname}</label>
			</div>

		</div>

		<div class="blank"></div>

		<div id="photoContainer">


			<div id="posts">
			

			</div>

		</div>


	</div>

	<footer>


		<div class="bottomLogo">
			<div class="css-3d-text">xyz.com</div>
		</div>



		<div id="footText" align="center">
			<div class="divTop">
				<a class="buttonTop" href="#top">Na górę</a>
			</div>
			<table>
				<tr>
					<td>Wojciech Świechowski</td>
				</tr>
				<tr>
					<td>watus.swiecho@gmail.com</td>
				</tr>
			</table>
		</div>

	</footer>
	
		<div class = "preLoader" align="center">
            <div id="loader"> </div>
            <label>Loading</label>                   
        </div>
        
	
	<script type="text/javascript">
//for onscroll posts loading	
	var j ;
	var x ;
	var doIhaveToAppend;
//-----------------------------//
	
	
	var loader = document.querySelector(".preLoader"); 
	
	window.addEventListener("load", vanish);

	function vanish()
	{
	  	loader.classList.add("disapear");  
	};

	var video = document.querySelector("video");
	video.onmouseover = function(){
	video.setAttribute("autoplay","true");
	}
	video.onmouseout = function(){
		video.setAttribute("autoplay","false");
	}

	var videos = document.getElementsByClassName("videos");
	[].forEach.call(videos, function (e) {
	    e.addEventListener('mouseover', hoverVideo, false);
	    e.addEventListener('mouseout', hideVideo, false);
	});
	
	function hoverVideo(e)
	{   
	    this.play();
	};
	
	function hideVideo(e)
	{
	    this.pause();
	};
	
	//----------------------------------------------------//
	
	var div;
		
	function ajaxInit(heather) { 
		
		 doIhaveToAppend = true;
		 j = document.documentElement.scrollHeight - window.innerHeight - 0.000000001;;
		 x = document.documentElement.scrollHeight - window.innerHeight - 0.000000001;;
	
    var XHR = null;

    try 
	{
        XHR = new XMLHttpRequest();
    } 
	catch (e)
	{
        try 
		{
            XHR = new ActiveXObject("Msxm12.XMLHTTP");
        } 
		catch (e1) 
		{
            try 
			{
                XHR = new ActiveXObject("Microsoft.XMLHTTP");
            }
			catch(e2)
			{
				alert("nie udalo sie");
			}
        }

    }
	
	console.log("jest ajax");
	
    if(XHR!=null && doIhaveToAppend == true)
	    {
	        XHR.open("POST", "http://localhost:8080/PostHandler/give10posts", true);
	        
			XHR.setRequestHeader("X-CSRF-TOKEN", "${_csrf.token}");
			XHR.setRequestHeader("doWhat", heather);
	        
	        XHR.onreadystatechange = function(){

	            if(XHR.readyState == 1||XHR.readyState == 2||XHR.readyState == 3)
	            {
                	console.log("loading");

	            }
	            else if(XHR.readyState == 4)
	            {
	                if(XHR.status==200)
	                {
	                	if(XHR.responseText != "")
	                	{
		                	var Lista = JSON.parse(XHR.responseText);
		                	
		                	for (x of Lista)
							{
		                		div += x;
							}
		                	
	               			 document.getElementById("posts").innerHTML = div;
	                	}
	                	else
	                	{
	                		doIhaveToAppend = false;
	                	}
	                }
	                else
	                {
	                    alert("błąd" + XHR.status); 
	                }
	            }
	        }
	        XHR.send(null);
	    }
    
}

	
	function onScrollAppend()
	{			
		x = document.documentElement.scrollHeight - window.innerHeight - 0.000000001;
		
		if(document.documentElement.scrollTop > x && document.documentElement.scrollTop > j)		
		{
			ajaxInit("onScroll");
			j = document.documentElement.scrollTop;
		}
	}

	window.onscroll = function() 
	{
		onScrollAppend();	
	};
	

	</script>

</body>
</html>