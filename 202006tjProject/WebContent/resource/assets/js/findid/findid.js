window.addEventListener("DOMContentLoaded",function(){
	$("form").on("submit", function() {
		let formData = $("form").eq(0).serialize();
		$.ajax({
			url : "/findid/findingid",
			type : "post",
			data : formData,
			dataType:"text",
			success : function(data) {
				if (data == "") {
					document.querySelector("#foundid").innerText= "이메일과 일치하는 아이디가 없습니다.";
					/*alert("이메일과 일치하는 아이디가 없습니다.");*/
				} else {
					document.querySelector("#foundid").innerText=data;
				}
			}
		})
		return false;
	});
});