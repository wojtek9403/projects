var j;
var x;
var doIhaveToAppend;
var z;
var XHR;
var div;
var video;
var videos;
//-----------------------------//

var site;

function initXHR() {

	var XHR = null;

	try {
		XHR = new XMLHttpRequest();
	}
	catch (e) {
		try {
			XHR = new ActiveXObject("Msxm12.XMLHTTP");
		}
		catch (e1) {
			try {
				XHR = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e2) {
				alert("Change Browser");
			}
		}

	}

	return XHR;
};

function loadPosts(heather, token) {

	var XHR = initXHR();

	if (XHR != null && doIhaveToAppend == true) {
		XHR.open("POST", "http://localhost:8080/PostHandler/give10posts", true);
		XHR.setRequestHeader("X-CSRF-TOKEN", token);
		XHR.setRequestHeader("doWhat", heather);
		XHR.onreadystatechange = function() {

			if (XHR.readyState == 1 || XHR.readyState == 2 || XHR.readyState == 3) {
				document.querySelector(".ajaxloader0").innerHTML = "<img alt='Loading ...' src='/images/ajax-loader.gif'></img>";
			}
			else if (XHR.readyState == 4) {
				if (XHR.status == 200) {
					if (XHR.responseText != " ") {
						var Lista = JSON.parse(XHR.responseText);

						for (x of Lista) {
							div += x;
						}

						document.getElementById("posts").innerHTML = div;
						document.querySelector(".ajaxloader0").innerHTML = " ";
					}
					else {
						document.querySelector(".ajaxloader0").innerHTML = " ";
						doIhaveToAppend = false;
					}
				}
				else {
					alert("błąd" + XHR.status);
				}
			}
		}
		XHR.send(null);
	}

	document.querySelector(".ajaxloader0").innerHTML = " ";
}

function onScrollAppend(token) {
	x = document.documentElement.scrollHeight - window.innerHeight - 20;

	if (document.documentElement.scrollTop > x && document.documentElement.scrollTop > j) {
		loadPosts("onScroll", token);
		j = document.documentElement.scrollTop;
	}

};


function hoverVideo() {
	this.play();
};

function hideVideo() {
	this.pause();
};

//mian
function main(token) {

	site = document.getElementsByTagName("title")[0];

	var loader = document.getElementsByClassName("preLoader");
	loader[0].classList.add("disapear");


	if (site.innerHTML == "News") {
		doIhaveToAppend = true;
		j = document.documentElement.scrollHeight - window.innerHeight;
		x = document.documentElement.scrollHeight - window.innerHeight;
		z = false;

		loadPosts("heather", token);

		window.addEventListener("scroll", function() {
			onScrollAppend(token);
		});
	}
	
	var videos = document.getElementsByClassName("videos");
		
		for(x of videos){
			x.addEventListener("mouseover",function(){
				this.play();
			});
			x.addEventListener("mouseout",function(){
				this.pause();
			});
		}


};
