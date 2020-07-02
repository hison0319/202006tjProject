function onSignIn(googleUser) {
	var profile = googleUser.getBasicProfile();
	console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	console.log('Name: ' + profile.getName());
	console.log('Image URL: ' + profile.getImageUrl());
	console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.

	var google = $("input[name^=google]");
	console.log(google);
	google[0].value = profile.getId();
	var googleId = google[0].value;
	google[1].value=profile.getName();
	var googleName = google[1].value;
	var googleEmail = profile.getEmail();
	if(googleEmail=!null){
		google[2].value=profile.getEmail();
		googleEmail = google[2].value;
		$.post("googlelogin",{googleId:googleId,googleName:googleName,googleEmail:googleEmail},function(){},'json')
		.done(function(result){
			alert(JSON.stringify(result));
		});
	}
	else {
		$.post("googlelogin",{googleId:googleId,googleName:googleName},function(){},'json')
		.done(function(result){
			alert(JSON.stringify(result));
			});
	}
}

window.addEventListener("DOMContentLoaded",function(){
	console.log("a");
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
});