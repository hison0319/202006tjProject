window.addEventListener("DOMContentLoaded",function(){
	$("form").on("submit", function() {
		let formData = $("form").eq(0).serialize();
		$.ajax({
			url : "/account/confirmP",
			type : "post",
			data : formData,
			success : function(data) {
				if (data == "t") {
					location.replace("/account/update");
				} else if (data == "f") {
					alert("비밀번호가 맞지 않습니다.");
				}
			}
		})
		return false;
	});
});