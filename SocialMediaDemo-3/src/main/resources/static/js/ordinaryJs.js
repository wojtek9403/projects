/**
 * 
 */
var site;

window.onload = function(){
	
	site = document.getElementsByTagName("title")[0];
	var loader = document.querySelector(".preLoader");
	loader.classList.add("disapear");


	if (site.innerHTML == "Admin Main" || site.innerHTML =="AdminProfilePic" || site.innerHTML =="AdminVideoUpload" || site.innerHTML =="AddPhoto" || site.innerHTML =="AddProfilePicture" || site.innerHTML =="Upload Video" || site.innerHTML=="Delete your account") {	
		
		var uploader = document.getElementsByClassName("uploader"); 			
		var submitButton = document.getElementById("submitUploadButton");
		
		submitButton.addEventListener("click", function(){
			uploader[0].classList.add("apear_upload");
		});
		
	}
	
	if (site.innerHTML == "AdminView" || site.innerHTML =="MyProfile" || site.innerHTML =="SthAccount") {		
		
		var videos = document.getElementsByClassName("videos");
		
		for(x of videos){
			x.addEventListener("mouseover",function(){
				this.play();
			});
			x.addEventListener("mouseout",function(){
				this.pause();
			});
		}
	}
	
	
};
