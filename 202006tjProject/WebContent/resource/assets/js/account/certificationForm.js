window.addEventListener("DOMContentLoaded",function(){
	console.log("a");
	$("form").on("submit", function() {
		let formData = $("form").eq(0).serialize();
		$.ajax({
			url : "/certify/form",
			type : "post",
			data : formData,
			success : function(data) {
				if (data == "t") {
					$(".message").text("메일을 보냈습니다. 확인해주세요.");
					$(".message").attr("style","color:#87cefa;");
				} else {
					$(".message").text("죄송합니다. 오류입니다. 메일이 전송되지 않습니다.");
					$(".message").attr("style","color:#ff0000");
				}
			}
		})
		return false;
	});
});

