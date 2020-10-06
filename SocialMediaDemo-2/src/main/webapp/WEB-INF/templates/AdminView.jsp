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
<body>

	<div id="container" align="center">

		<div id="heather1">

			<div class="css-3d-text">AntiSocialMedia.com</div>
			
			<div id="browser">
				<form method="get" action="/SocialMediaDemo/users">
					<input name = "userName" type = "text" placeholder="Wyszukaj" /> <input type="submit" value="Szukaj"/>
				</form>
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
					<li class="liBottom"><a class="buttonLi" href="/Demo/usuwacz">Usun
							kogos</a></li>
							<li class="liBottom"><a class="buttonLi"
						href="/SocialMediaDemo/videoUpload">Dodaj film</a></li>

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


			<div>
				<c:forEach var="path" items="${paths}">

					<a id="xx" href="/SocialMediaDemo/out/${path}"><img class = "picTable"
						src="/userImg/${path}"></img></a>

				</c:forEach>

			</div>
			
			<div>
				<c:forEach var="videoPath" items="${videoPaths}">

					<a id="xx" href="/SocialMediaDemo/out/videos/${videoPath}"><video
					id = "video" preload = "metadata" 
					height="200px" width="300px" muted="true" loop="true"
					poster="" class="videos"
						><source src="/userImg/${videoPath}#t=0.5" type = "video/mp4" /></video></a>
					<!-- aby wyświetlić miniature video należy uzyc tagu source i #t=czas w sekundach klatki ktorama byc wyswietlona -->
				</c:forEach>

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
		</footer>
	<script type="text/javascript">


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


	</script>

</body>
</html>