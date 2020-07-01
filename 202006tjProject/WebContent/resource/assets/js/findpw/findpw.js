window.addEventListener("DOMContentLoaded",function(){
	$("form").on("submit", function() {
		let formData = $("form").eq(0).serialize();
		$.ajax({
			url : "/findpw/findingpw",
			type : "post",
			data : formData,
			success : function(data) {
				if (data == "") {
					console.log(data);
					alert("이메일 오류");
				} else {
					location.href = "complete";
				}
			}
		})
		return false;
	});
});