<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>
<link href="/css/AdminMain.css" rel='stylesheet' type='text/css'>
<script type="text/javascript" src="/js/ordinaryJs.js" async = "true"></script>
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
		
				<label>Znaleziono: </label> ${count} 
						<br/>
						<br/>

		<div id="photoContainer">

		<c:forEach var="user" items="${Users}">
	
			<div id="profileContainer">
						<br/>
				<a id="xx" href="/SocialMediaDemo/users/profile/${user.username}"><img class = "picTable"
						src="/userImg/${user.profilePicture}"></img></a>
						<br/>
						<label>${user.name} ${user.surname}</label>
						<br/>
						<br/>
			</div>	
							
		</c:forEach>
		
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

</body>
</html>