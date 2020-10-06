<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
                        <!-- dopisaÄ ze naklikniecie w fotopokazesie stary display  b -->
                        
                         <div id="comments">
                
                             <div class="desc" align="left"> 
                                <p id="opis">${photo.description}</p>
                             </div>
                             
                             <div class="content"> 
                                <label>Comments:  </label>
                                <!-- petla -->
                                
                                <div id="post">
                                    <div id="who" align="center">
                                            <a href="#">
                                                <img id="disp" alt="xx" src="sadelkowiec.jpg">
                                            </a>  
                                        <label>${who}</label>
                                    </div>
                                    <div id="what">
                                        sdvjnsdjnsvvjsdnjsdvnsdvjnsdjnsvvjsdnjsdvnsdvjnsdjnsvvjsdnjsdvnsdvjnsdjnsvvjsdnjsdvnsdvjnsdjnsvvjsdnjsdvnsdvjnsdjnsvvjsdnjsdvn
                                        sdvjnsdjnsvvjsdnjsdvnsdvjnsdjnsvvjsdnjsdvnsdvjnsdjnsvvjsdnjsdvnsdvjnsdjnsvvjsdnjsdvnsdvjnsdjnsvvjsdnjsdvn
                                    </div>                                    
                                </div>
                                
                                
                                <!-- petla -->

                                
                             </div>
                         </div>
		</div>
            
	</div>
</body>
</html>