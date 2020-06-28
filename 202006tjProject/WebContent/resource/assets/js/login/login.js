window.addEventListener("DOMContentLoaded",function(){
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