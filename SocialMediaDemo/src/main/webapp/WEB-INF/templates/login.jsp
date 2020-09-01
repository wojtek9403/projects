<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Register user</title>
<link href="/css/login.css" rel='stylesheet' type='text/css'>
</head>
<body>

	<div id="heather1">
		<div class="css-3d-text">AntiSocialMedia.com</div>
	</div>


	<div id="mainContainer" align="center">

		<div id="formCon">
			<div id="login" align="center">
				<form method="post" action="/SocialMediaDemo/login"
					modelAttribute="user">

					<div class="login">
						<label>Login: </label> <input name="username" type="text" /> <br />
						<br />
					</div>

					<div class="haslo">
						<label>Hasło: </label> <input name="password" type="password" /> <br />
						<br /> <label style="color: red">${error}</label> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

					</div>

					<div class="divbutton3">
						<button class="button" type="submit">Zaloguj się</button>
					</div>


				</form>


				<div class="divbutton4">
					<a class="button" href="/SocialMediaDemo/registration">zarejestruj
						się</a>
				</div>

			</div>

			<div id="img" align="center">
				<img alt="fdffd" src="/images/free-rectangle-stamp-1.png"
					height="190px width=390px"></img>
			</div>

		</div>
	</div>

	<footer>


	<div class="bottomLogo">
		<div class="css-3d-text">xyz.com</div>
	</div>

	<div id="footText" align="center">

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