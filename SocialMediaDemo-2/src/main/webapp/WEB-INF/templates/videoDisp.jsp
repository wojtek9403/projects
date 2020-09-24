<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="/css/display.css" rel='stylesheet' type='text/css'>
</head>
<body>

	<div id="container" align="left">
		<div id="container2" align="center">

			<video
					id = "video"
					height="400px" width="800px" autoplay="true" muted="flase" loop="true"
					poster="" class="videos" controls="true"
						src="/userImg/${video}"></video>
			<p id="opis">${desc}</p>

		</div>
	</div>
</body>
</html>


