<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Register user</title>
<link href="/css/registration.css" rel='stylesheet' type='text/css'>
</head>
<body>


	<div id="heather1">
		<div class="css-3d-text">AntiSocialMedia.com</div>
	</div>


	<div id="mainContainer" align="center">

		<div id="formCon">
			<div id="login" align="center">
				<form:form method="post" modelAttribute="userForm">

					<div class="login">
						<form:input type="text" path="username" placeholder="login"/>
						<br />
						<form:errors path="Username" style="color:red"
							class="form-text text-muted" />
						<br />
						<form:errors path="loginIstnieje" style="color:red"
							class="form-text text-muted" />
						<br />	
						<form:input type="text" path="name" placeholder="Imię"/>
						<br />
						<br />
						<form:errors path="email" style="color:red" class="form-text text-muted" />
						<br/>					
						<form:input type="text" path="email" placeholder="E-mail"/>
						<br />
						<br />
						<form:errors path="Name" style="color:red"
							class="form-text text-muted" />
						<br />
	
						<form:input type="text" path="surname" placeholder="Nazwisko"/>
						<br />
						<br />
						<form:errors path="Surname" style="color:red"
							class="form-text text-muted" />
					</div>
					


					<div class="haslo">
					
						<form:input type="password" path="password" placeholder="Hasło"/>
						<br />
						<br />
						<form:errors path="password" style="color:red"
							class="form-text text-muted" />

					</div>

					<div class="powt">
						<br /> 
						<form:input type="password" path="passwordConfirm" placeholder="Powtórz hasło"/>
 						<br />
 						<br />

						<form:errors path="passwordConfirm" style="color:red"
							class="form-text text-muted" />

					</div>

					<div class="divbutton3">
						<button class="button" type="submit">Utwórz konto</button>
					</div>


				</form:form>


				<div class="divbutton4">
					<a class="button" href="/SocialMediaDemo/login">Zaloguj się</a>
				</div>

			</div>

			<div id="img" align="center">
				<img alt="fdffd" src="/images/front.png" height="190px width=390px"></img>
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










<script type="text/javascript">

	alert("Po dokonaniu rejestracji prosimy o odczytanie wysłanej na podany adres E-mail wiadomości powitalnej !");

</script>
</body>
</html>