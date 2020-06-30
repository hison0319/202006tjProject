window.addEventListener("DOMContentLoaded",function(){
	//정규식
	var empJ = /\s/g; //공백체크
	var memberIdPattern = /^[a-z | A-Z]{3,6}[0-9]{3,6}$/;
	var passwordPattern = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
	var emailPattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
	var phonePattern =  /^\d{3}\d{3,4}\d{4}$/;
	
	var memberIdOk = false;
	var emailOk = false;
	var phoneOk = false;
	
	$("#memberId").on("keyup", function(){
		if($("#memberId").val() != ""){
			document.querySelector("#idMsg").innerText = "사용 가능한 아이디인지 확인받으세요.";
		}
		else {
			document.querySelector("#idMsg").innerText = "필수 정보입니다.";
		} 
	});		
	$("#password").on("keyup", function(){
		if($("#password").val() != ""){
			document.querySelector("#pwMsg").innerText = "";
		}
		else {
			document.querySelector("#pwMsg").innerText = "필수 정보입니다.";
		}
	});	
	$("#passwordCheck").on("keyup", function(){
		if($("#passwordCheck").val() != ""){
			document.querySelector("#pwCheckMsg").innerText = "";
		}
		else {
			document.querySelector("#pwCheckMsg").innerText = "필수 정보입니다.";
		}
	});	
	$("#email").on("keyup", function(){
		if($("#email").val() != ""){
			document.querySelector("#emailMsg").innerText = "사용 가능한 이메일인지 확인받으세요.";
		}
		else {
			document.querySelector("#emailMsg").innerText = "필수 정보입니다.";
		}
	});
	$("#phone").on("keyup", function(){
		if($("#phone").val() != ""){
			document.querySelector("#phoneMsg").innerText = "사용 가능한 번호인지 확인받으세요.";
		}
		else {
			document.querySelector("#phoneMsg").innerText = "필수 정보입니다.";
		}
	});
	
	
	$("#memberId").on("change",function() {
		var memberIdOk = false;
	});
	$("#email").on("change",function() {
		var emailOk = false;
	});
	$("#phone").on("change",function() {
		var phoneOk = false;
	});
	
	
	$("#id_check").on("click",function(){
		//아이디 공백확인
		if ($("#memberId").val() == "") {
			document.querySelector("#idMsg").innerText = "아이디를 입력해주세요.";
			$("#memberId").focus();
			return false;
		}
		//아이디의 유효성 검사
		if (!memberIdPattern.test($("#memberId").val())) {
			document.querySelector("#idMsg").innerText = "아이디를 올바르게 입력해주세요.";
			$("#memberId").val("");
			$("#memberId").focus();
			return false;
		}
		let formData = $("#memberId").serialize();
		$.ajax({
			url:"/signup/confirmMemberId",
			type:"post",
			data:formData,
			success:function(data){
				if(data == "t") {
					memberIdOk = true;
					document.querySelector("#idMsg").innerText = "사용 가능 합니다.";
				} else {
					document.querySelector("#idMsg").innerText = "중복된 아이디가 있습니다.";
				}
			}
		})
		return false;
	});
	
	$("#email_check").on("click",function(){
		//이메일 공백 확인
	    if ($("#email").val() == "") {
		document.querySelector("#emailMsg").innerText = "이메일을 입력해주세요.";
	       $("#email").focus();
	       return false;
	      }
	    //이메일 유효성 검사
	    if (!emailPattern.test($("#email").val())) {
	    	document.querySelector("#emailMsg").innerText = "이메일형식에 맞게 입력해주세요.";
	       $("#email").val("");
	       $("#email").focus();
	       return false;
	      }
		let formData = $("#email").serialize();
		$.ajax({
			url:"/signup/confirmEmail",
			type:"post",
			data:formData,
			success:function(data){
				if(data == "t") {
					emailOk = true;
					document.querySelector("#emailMsg").innerText = "사용 가능 합니다.";
				} else {
					document.querySelector("#emailMsg").innerText = "중복된 이메일이 있습니다.";
				}
			}
		})
		return false;
	});
	
	$("#phone_check").on("click",function(){
		//전화번호 공백 확인
	    if ($("#phone").val() == "") {
	    	document.querySelector("#phoneMsg").innerText = "전화번호를 입력해주세요.";
	        $("#phone").focus();
	        return false;
	      }
	      //전화번호 유효성 검사
	      if (!phonePattern.test($("#phone").val())) {
	    	  document.querySelector("#phoneMsg").innerText = "전화번호 형식에 맞게 입력해주세요.";
	          $("#phone").val("");
	          $("#phone").focus();
	          return false;
	      }
		let formData = $("#phone").serialize();
		$.ajax({
			url:"/signup/confirmPhone",
			type:"post",
			data:formData,
			success:function(data){
				if(data == "t") {
					phoneOk = true;
					document.querySelector("#phoneMsg").innerText = "사용 가능 합니다.";
				} else {
					document.querySelector("#phoneMsg").innerText = "중복된 폰번호가 있습니다.";
				}
			}
		})
		return false;
	});
	
	$("form").on("submit",function(){
	    //아이디 공백확인
	    if ($("#memberId").val() == "") {
	    	document.querySelector("#idMsg").innerText = "아이디를 입력해주세요.";
	       $("#memberId").focus();
	       return false;
	      }
	    //아이디의 유효성 검사
	    if (!memberIdPattern.test($("#memberId").val())) {
	    	document.querySelector("#idMsg").innerText = "아이디를 올바르게 입력해주세요.";
	       $("#memberId").val("");
	       $("#memberId").focus();
	       return false;
	      }
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
	    //비밀번호확인 공백 확인
	    if ($("#passwordCheck").val() == "") {
	    	document.querySelector("#pwCheckMsg").innerText = "비밀번호를 확인하세요.";
	       $("#passwordCheck").focus();
	       return false;
	      }
	    //비밀번호 확인
	    if ($("#password").val() != ($("#passwordCheck").val())) {
	    	document.querySelector("#pwCheckMsg").innerText = "비밀번호가 같지 않습니다.";
	       $("#password").val("");
	       $("#passwordCheck").val("");
	       $("#passwordCheck").focus();
	       return false;
	      }
	    //이메일 공백 확인
	    if ($("#email").val() == "") {
	    	document.querySelector("#emailMsg").innerText = "이메일을 입력해주세요.";
	       $("#email").focus();
	       return false;
	      }
	    //이메일 유효성 검사
	    if (!emailPattern.test($("#email").val())) {
	    	document.querySelector("#emailMsg").innerText = "이메일형식에 맞게 입력해주세요.";
	       $("#email").val("");
	       $("#email").focus();
	       return false;
	      }
	    //전화번호 공백 확인
	    if ($("#phone").val() == "") {
	    	document.querySelector("#phoneMsg").innerText = "전화번호를 입력해주세요.";
	        $("#phone").focus();
	        return false;
	      }
	      //전화번호 유효성 검사
	      if (!phonePattern.test($("#phone").val())) {
	    	  document.querySelector("#phoneMsg").innerText = "전화번호 형식에 맞게 입력해주세요.";
	          $("#phone").val("");
	          $("#phone").focus();
	          return false;
	      }
	    if (!memberIdOk) {
	    	document.querySelector("#idMsg").innerText = "사용 가능한 아이디인지 확인받으세요.";
		  	return false;
		   }
	    if (!emailOk) {
	    	document.querySelector("#emailMsg").innerText = "사용 가능한 이메일인지 확인받으세요.";
	    	return false;
	    }
	    if (!phoneOk) {
	    	document.querySelector("#phoneMsg").innerText = "사용 가능한 전화번호인지 확인받으세요.";
	    	return false;
	    }
	    var address = $("#sample4_postcode").val()+"/"+$("#sample4_roadAddress").val()+"/"+$("#sample4_jibunAddress").val()+"/"+$("#sample4_detailAddress").val()+"/"+$("#sample4_extraAddress").val();
	    $("#address").val(address);
	});
});

