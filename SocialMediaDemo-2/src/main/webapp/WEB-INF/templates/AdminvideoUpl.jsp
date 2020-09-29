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

				</ul>
			</div>


			<div class="divbutton2">
				<a class="buttonW" href="/SocialMediaDemo/logout">Wyloguj</a>
			</div>

		</div>


		<div id="header2">

			<a class="profileLink" href="/uploadProfilePic">
				<div class="profilowe">
					<img alt="upload profile pic" src="/userImg/${profile}">
				</div>
			</a>

			<div class="details">
				<label>Administrator:</label> <label>${name}</label>
			</div>


		</div>


		<div id="formContainer" align="center">

			<form method="post" action="/videoUpload" enctype="multipart/form-data">
				<label>Dodaj film </label> <input class="fileIn" type="file"
					name="file"> <br /> <br /> <label>Wprowadź opis
					zdjęcia</label> <br />
				<textarea name="desc" spellcheck="true" rows="5" cols="40"></textarea>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" /> <br /> <br /> <input class="sub"
					type="submit">
			</form>
			<br />
			<div class="divbutton">
				<a class="button" href="/SocialMediaDemo/out">Przeglądaj</a>
			</div>
			<br /> <label style="color: red">${err_message}</label>

		</div>


	</div>

	<div class="blank"></div>

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

</body>
</html>