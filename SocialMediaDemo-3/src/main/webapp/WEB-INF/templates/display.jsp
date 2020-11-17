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

			<img id="disp" alt="xx" src="/userImg/${photo}">
			<p id="opis">${desc}</p>

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