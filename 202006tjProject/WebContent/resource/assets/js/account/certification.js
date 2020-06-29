window.addEventListener("DOMContentLoaded",function(){
	console.log("a");
	$("form").on("submit", function() {
		let formData = $("form").eq(0).serialize();
		$.ajax({
			url : "/certify/confirm",
			type : "post",
			data : formData,
			success : function(data) {
				if (data) {
					$(".message").text("인증이 확인되었습니다!");
					$(".message").attr("style","color:#87cefa;");
				} else {
					$(".message").text("죄송합니다. 인증되지 않습니다.");
					$(".message").attr("style","color:#ff0000");
				}
			}
		})
		return false;
	});
});