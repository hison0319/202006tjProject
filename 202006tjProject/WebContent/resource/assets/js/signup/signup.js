window.addEventListener("DOMContentLoaded",function(){
	//정규식
	var empJ = /\s/g; //공백체크
	var memberIdPattern = /^[A-Za-z]{2,15}[0-9]{1,15}$/;
	//숫자 1회 이상, 영문 2개 이상 사용하여 2자리 이상 총 15자리 이하 입력, 특수문자 사용 불가
	var passwordPattern = /(?=.*\d{1,15})(?=.*[~`!@#$%\^&*()-+=]{1,15})(?=.*[a-zA-Z]{2,50}).{8,15}$/;
	//숫자, 특수문자 각 1회 이상, 영문 2개 이상 사용하여 8자리 이상 총 15자리 이하 입력
	var emailPattern = /^[0-9a-zA-Z]+@[0-9a-zA-Z]+\.[a-zA-z]{2,3}$/;
	var phonePattern =  /^\d{3}\d{4}\d{4}$/;
	
	var memberIdOk = false;
	var emailOk = false;
	var phoneOk = false;
	
	$("#memberId").on("change",function() {
		var memberIdOk = false;
	});
	$("#email").on("change",function() {
		var emailOk = false;
	});
	$("#phone").on("change",function() {
		var phoneOk = false;
	});
	
//입력 할 때
	$("#memberId").on("focusout",function(){
		//아이디 유효성 검사
		if (!memberIdPattern.test($("#memberId").val())) {
			document.querySelector("#idMsg").innerText = "아이디를 형식에 맞게 입력해주세요.\n(숫자 1회 이상, 영문 2회 이상 입력, 특수문자 사용 불가)";
				return false;
		}
	});	
	$("#password").on("focusout",function(){
		//비밀번호 유효성 검사
		if (!passwordPattern.test($("#password").val())) {
	    	document.querySelector("#pwMsg").innerText = "비밀번호 형식에 맞게 입력해주세요.\n(숫자, 특수문자 각 1회 이상, 영문 2회 이상 입력)";
	       return false;
	    }
	});	
	$("#passwordCheck").on("focusout",function(){
		//비밀번호 확인 유효성 검사
		if (!passwordPattern.test($("#passwordCheck").val())) {
	    	document.querySelector("#pwCheckMsg").innerText = "비밀번호 형식에 맞게 입력해주세요.";
	       return false;
	    }
	});	
	$("#email").on("focusout",function(){
		//이메일 유효성 검사
		if (!emailPattern.test($("#email").val())) {
	    	document.querySelector("#emailMsg").innerText = "이메일 형식에 맞게 입력해주세요.";
	       return false;
	    }
	});
	$("#phone").on("focusout",function(){
		//전화번호 유효성 검사
		if (!phonePattern.test($("#phone").val())) {
			document.querySelector("#phoneMsg").innerText = "전화번호 형식에 맞게 입력해주세요.";
			return false;
		}
	});
	
//입력 완료 했을 때
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
		
//중복 확인 버튼 눌렀을 때
	$("#id_check").on("click",function(){
		//아이디 공백확인 
		if ($("#memberId").val() == "") {
			document.querySelector("#idMsg").innerText = "아이디를 입력해주세요.";
			$("#memberId").focus();
			return false;
		}
		//아이디 유효성 검사
		else if (!memberIdPattern.test($("#memberId").val())) {
			document.querySelector("#idMsg").innerText = "아이디를 형식에 맞게 입력해주세요.\n(숫자 1회 이상, 영문 2회 이상 입력, 특수문자 사용 불가)";
			$("#memberId").focus();
			return false;
		}
		//아이디 중복 확인 
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
	    	document.querySelector("#emailMsg").innerText = "이메일 형식에 맞게 입력해주세요.";
	       $("#email").focus();
	       return false;
	      }
	    //이메일 중복 확인 
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
	          $("#phone").focus();
	          return false;
	      }
	    //전화번호 중복 확인 
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
					document.querySelector("#phoneMsg").innerText = "중복된 전화번호가 있습니다.";
				}
			}
		})
		return false;
	});
	
//회원가입 버튼 눌렀을 때
	$("form").on("submit",function(){
	    //아이디 공백확인
	    if ($("#memberId").val() == "") {
	    	document.querySelector("#idMsg").innerText = "아이디를 입력해주세요.";
	       $("#memberId").focus();
	       return false;
	      }
	    //아이디의 유효성 검사
	    if (!memberIdPattern.test($("#memberId").val())) {
	    	document.querySelector("#idMsg").innerText = "아이디를 형식에 맞게 입력해주세요.";
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
	          $("#phone").focus();
	          return false;
	      }
//중복 확인 안했을 때
	    if (!memberIdOk) {
			document.querySelector("#idMsg").innerText = "사용 가능한 아이디인지 확인받으세요.";
			$("#memberId").focus();
		  	return false;
		   }
	    if (!emailOk) {
			document.querySelector("#emailMsg").innerText = "사용 가능한 이메일인지 확인받으세요.";
			$("#email").focus();
	    	return false;
	    }
	    if (!phoneOk) {
			document.querySelector("#phoneMsg").innerText = "사용 가능한 전화번호인지 확인받으세요.";
			$("#phone").focus();
	    	return false;
	    }
//주소 옮기기
	    var address = $("#sample4_postcode").val()+"/"+$("#sample4_roadAddress").val()+"/"+$("#sample4_jibunAddress").val()+"/"+$("#sample4_detailAddress").val()+"/"+$("#sample4_extraAddress").val();
	    $("#address").val(address);
//약관 동의 체크
	    if($("#check_1").is(":checked") == false){
	    	alert("모든 약관에 동의 하셔야 다음 단계로 진행 가능합니다.");
	    	return;
	    }else if($("#check_2").is(":checked") == false){
	    	alert("모든 약관에 동의 하셔야 다음 단계로 진행 가능합니다.");
	    	return;
	    }else{
	    	$("#signupForm").submit();
	    }
	});	
});

//체크박스 전체 선택
$(".checkbox_group").on("click", "#check_all", function () {
	$(this).parents(".checkbox_group").find('input').prop("checked", $(this).is(":checked")); 
	}); 

// 체크박스 개별 선택 
$(".checkbox_group").on("click", ".normal", function() {
	var is_checked = true; 
	$(".checkbox_group .normal").each(function(){ 
		is_checked = is_checked && $(this).is(":checked"); 
		});
	$("#check_all").prop("checked", is_checked); 
});

//비밀번호 보이기/숨기기 
$(document).ready(function(){
    $('.signup_pw i').on('click',function(){
        $('input').toggleClass('active');
        if($('input').hasClass('active')){
            $(this).attr('class',"fa fa-eye-slash fa-lg")
            .prev('input').attr('type',"text");
        }else{
            $(this).attr('class',"fa fa-eye fa-lg")
            .prev('input').attr('type','password');
        }
    });
});












