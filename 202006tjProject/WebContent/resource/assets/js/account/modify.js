window.addEventListener("DOMContentLoaded",function(){
	//정규식
	var empJ = /\s/g; //공백체크
	var memberIdPattern = /^[a-z | A-Z]{3,6}[0-9]{3,6}$/;
	var passwordPattern = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
	var emailPattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
	var phonePattern =  /^\d{3}\d{3,4}\d{4}$/;

	var emailOk = true;
	var PhoneOk = true;
	
	$("#email").on("change",function() {
		alert("사용가능한 메일인지 확인받으세요.");
		var emailOk = false;
	});
	$("#phone").on("change",function() {
		alert("사용가능한 전화번호인지 확인받으세요.");
		var emailOk = false;
	});
	
	$("#email_check").on("click",function(){
		//이메일 공백 확인
	    if ($("#email").val() == "") {
	       alert("이메일을 입력해주세요");
	       $("#email").focus();
	       return false;
	      }
	    //이메일 유효성 검사
	    if (!emailPattern.test($("#email").val())) {
	       alert("이메일형식에 맞게 입력해주세요")
	       $("#email").val("");
	       $("#email").focus();
	       return false;
	      }
		let formData = $("#email").serialize();
		$.ajax({
			url:"/account/confirmEmail",
			type:"post",
			data:formData,
			success:function(data){
				if(data == "s") {
					alert("변경하지 않았습니다.")
				} else if (data == "t"){
					alert("사용가능 합니다.");
				} else {
					alert("중복된 메일이 있습니다.");					
				}
			}
		})
		return false;
	});
	
	$("#phone_check").on("click",function(){
		//전화번호 공백 확인
	    if ($("#phone").val() == "") {
	        alert("전화번호를 입력해주세요");
	        $("#phone").focus();
	        return false;
	      }
	      //전화번호 유효성 검사
	      if (!phonePattern.test($("#phone").val())) {
	          alert("전화번호 형식에 맞게 입력해주세요")
	          $("#phone").val("");
	          $("#phone").focus();
	          return false;
	      }
		let formData = $("#phone").serialize();
		$.ajax({
			url:"/account/confirmPhone",
			type:"post",
			data:formData,
			success:function(data){
				if(data == "s") {
					alert("변경하지 않았습니다.")
				} else if (data == "t"){
					alert("사용가능 합니다.");
				} else {
					alert("중복된 메일이 있습니다.");					
				}
			}
		})
		return false;
	});
	
	$("form").on("submit",function(){

	    //비밀번호 공백 확인
	    if ($("#password").val() == "") {
	       alert("비밀번호를 입력하세요");
	       $("#password").focus();
	       return false;
	      }
	    //비밀번호 유효성 검사
	    if (!passwordPattern.test($("#password").val())) {
	       alert("비밀번호 형식에 맞게 입력해주세요");
	       $("#password").val("");
	       $("#password").focus();
	       return false;
	      }
	    //비밀번호확인 공백 확인
	    if ($("#passwordCheck").val() == "") {
	       alert("비밀번호를 확인하세요");
	       $("#passwordCheck").focus();
	       return false;
	      }
	    //비밀번호 확인
	    if ($("#password").val() != ($("#passwordCheck").val())) {
	       alert("비밀번호가 같지 않습니다.");
	       $("#password").val("");
	       $("#passwordCheck").val("");
	       $("#passwordCheck").focus();
	       return false;
	      }
	    //이메일 공백 확인
	    if ($("#email").val() == "") {
	       alert("이메일을 입력해주세요");
	       $("#email").focus();
	       return false;
	      }
	    //이메일 유효성 검사
	    if (!emailPattern.test($("#email").val())) {
	       alert("이메일형식에 맞게 입력해주세요")
	       $("#email").val("");
	       $("#email").focus();
	       return false;
	      }
	    //전화번호 공백 확인
	    if ($("#phone").val() == "") {
	        alert("전화번호를 입력해주세요");
	        $("#phone").focus();
	        return false;
	      }
	      //전화번호 유효성 검사
	      if (!phonePattern.test($("#phone").val())) {
	          alert("전화번호 형식에 맞게 입력해주세요")
	          $("#phone").val("");
	          $("#phone").focus();
	          return false;
	      }
	   if (!emailOk) {
		   alert("사용 가능한 이메일인지 확인받으세요.");
		   return false;
	   }
	   if (!phoneOk) {
	    	alert("사용 가능한 전화번호인지 확인받으세요.");
	    	return false;
	   }
	});
});

