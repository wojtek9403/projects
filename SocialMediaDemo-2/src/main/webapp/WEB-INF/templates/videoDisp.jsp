<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="/css/video.css" rel='stylesheet' type='text/css'>
</head>
<body>

	<div id="container" align="center">
            
		<div id="container2" align="center">

                    
                        <video id = "video" preload = "metadata" class="videos" controls="true" >
                            <source src="/userImg/${video.orginalPicPath}" type = "video/mp4" />
                        </video>
                       
                        <!-- dopisaÄ ze naklikniecie w fotopokazesie stary display  b -->
                        
                         <div id="comments">
                
                             <div class="desc" align="left"> 
                                <p id="opis">${video.description}</p>
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