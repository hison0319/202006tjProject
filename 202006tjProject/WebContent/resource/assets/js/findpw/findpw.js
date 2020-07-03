window.addEventListener("DOMContentLoaded",function(){
	$("form").on("submit", function() {
		let formData = $("form").eq(0).serialize();
		$.ajax({
			url : "/findpw/findingpw",
			type : "post",
			data : formData,
			success : function(data) {
				if (data == "") {
					document.querySelector("#foundpw").innerText="존재하지 않는 이메일 또는 아이디 입니다.";
					/*alert("존재하지 않는 이메일 또는 아이디 입니다.");*/
				} else {
					document.querySelector("#foundpw").innerText=data;
				}
			}
		})
		return false;
	});
});