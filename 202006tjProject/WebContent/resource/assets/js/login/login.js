//var googleId;
//var googleName;
//var googleEmail;
//
//function onSignIn(googleUser) {
//	var profile = googleUser.getBasicProfile();
//	console.log('ID: ' + profile.getId()); // Do not send to your backend! Use
//	// an ID token instead.
//	console.log('Name: ' + profile.getName());
//	console.log('Image URL: ' + profile.getImageUrl());
//	console.log('Email: ' + profile.getEmail()); // This is null if the
//	// 'email' scope is not
//	// present.
//	
//	googleId = profile.getId();
//	googleName = profile.getName();
//	googleEmail = profile.getEmail();
//}

window.addEventListener("DOMContentLoaded", function() {
//	onSignIn(googleUser);
	$("form").on("submit", function() {
		let formData = $("form").eq(0).serialize();
		$.ajax({
			url : "/login/matching",
			type : "post",
			data : formData,
			success : function(data) {
				if (data == "") {
					console.log(data);
					alert("아이디 오류");
				} else if (data == "p") {
					alert("비밀번호 오류");
				} else {
					location.href = "complete";
				}
			}
		})
		return false;
	});
	
//	$("#googleLogin").on("click", function() {
//		var googleInfo = new Object();
//		googleInfo.googleId = googleId;
//		googleInfo.googleName = googleName;
//		googleInfo.googleEmail = googleEmail;
//		console.dir(googleInfo);
//
//		$.ajax({
//			type : "post",
//			url : "/googlelogin",
//			data : googleInfo,
//			success : function(result) {
//				console.log(result);
//				if(result == "f") {
////					location.replace("/")				
//				}
//			}
//		});
//	})
});