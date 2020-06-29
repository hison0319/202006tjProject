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
			document.querySelector("#idMsg").innerText = "";
		}
		else {
			document.querySelector("#idMsg").innerText = "필수 정보입니다";
		}
	});
	
	$("#password").on("keyup", function(){
		if($("password").val() != ""){
			document.querySelector("#pwMsg").innerText = "";
		}
		else {
			document.querySelector("#pwMsg").innerText = "필수 정보입니다";
		}
	});
	
	$("#passwordCheck").on("keyup", function(){
		if($("#passwordCheck").val() != ""){
			document.querySelector("#pwCheckMsg").innerText = "";
		}
		else {
			document.querySelector("#pwCheckMsg").innerText = "필수 정보입니다";
		}
	});
	
	$("#email").on("keyup", function(){
		if($("#email").val() != ""){
			document.querySelector("#emailMsg").innerText = "";
		}
		else {
			document.querySelector("#emailMsg").innerText = "필수 정보입니다";
		}
	});
	
	$("phone").on("keyup", function(){
		if($("#phone").val() != ""){
			document.querySelector("#phoneMsg").innerText = "";
		}
		else {
			document.querySelector("#phoneMsg").innerText = "필수 정보입니다";
		}
	});
	
	$("#memberId").on("change",function() {
		alert("사용가능한 아이디인지 확인받으세요.");
		var memberIdOk = false;
	});
	$("#email").on("change",function() {
		alert("사용가능한 이메일인지 확인받으세요.");
		var emailOk = false;
	});
	$("#phone").on("change",function() {
		alert("사용가능한 폰번호인지 확인받으세요.");
		var emailOk = false;
	});
	

	
	$("#id_check").on("click",function(){
		//아이디 공백확인
	    if ($("#memberId").val() == "") {
	       alert("아이디를 입력해주세요.");
	       $("#memberId").focus();
	       return false;
	      }
	    //아이디의 유효성 검사
	    if (!memberIdPattern.test($("#memberId").val())) {
	       alert("아이디를 올바르게 입력해주세요.");
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
					alert("사용가능 합니다.");
				} else {
					alert("중복된 아이디가 있습니다.");
					$(".memberId").css("border","1px solid red");
				}
			}
		})
		return false;
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
			url:"/signup/confirmEmail",
			type:"post",
			data:formData,
			success:function(data){
				if(data == "t") {
					emailOk = true;
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
			url:"/signup/confirmPhone",
			type:"post",
			data:formData,
			success:function(data){
				if(data == "t") {
					phoneOk = true;
					alert("사용가능 합니다.");
				} else {
					alert("중복된 폰번호가 있습니다.");
				}
			}
		})
		return false;
	});
	
	$("form").on("submit",function(){

	    //아이디 공백확인
	    if ($("#memberId").val() == "") {
	       alert("아이디를 입력해주세요.");
	       $("#memberId").focus();
	       return false;
	      }
	    //아이디의 유효성 검사
	    if (!memberIdPattern.test($("#memberId").val())) {
	       alert("아이디를 올바르게 입력해주세요.");
	       $("#memberId").val("");
	       $("#memberId").focus();
	       return false;
	      }
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
	    if (!memberIdOk) {
		 	alert("사용 가능한 아이디인지 확인받으세요.");
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

