window.addEventListener("DOMContentLoaded",function(){
	//정규식
	var empJ = /\s/g; //공백체크
	var passwordPattern = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;

	$("#password").on("keyup", function(){
		if($("#password").val() != ""){
			document.querySelector("#pwMsg").innerText = "";
		}
		else {
			document.querySelector("#pwMsg").innerText = "비밀번호를 입력해주세요.";
		}
	});	
	
	$("#check_pw").on("click",function(){
		//비밀번호 공백확인
		if ($("#password").val() == "") {
			document.querySelector("#pwMsg").innerText = "비밀번호를 입력해주세요.";
			$("#password").focus();
			return false;
		}
		//비밀번호 유효성 검사
		if (!memberIdPattern.test($("#password").val())) {
			document.querySelector("#pwMsg").innerText = "비밀번호 형식에 맞게 입력해주세요.";
			$("#password").val("");
			$("#password").focus();
			return false;
		}
	});
	
	$("form").on("submit",function(){
	    //비밀번호 공백 확인
	    if ($("#password").val() == "") {
	    	document.querySelector("#pwMsg").innerText = "비밀번호를 입력하세요.";
	       $("#password").focus();
	       return false;
	      }
	    //비밀번호 유효성 검사
	    if (!passwordPattern.test($("#password").val())) {
	    	document.querySelector("#pwMsg").innerText = "비밀번호 형식에 맞게 입력해주세요.";
	       $("#password").val("");
	       $("#password").focus();
	       return false;
	      }
	});

});