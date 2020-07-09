window.addEventListener("DOMContentLoaded",function(){
	//정규식
	var empJ = /\s/g; //공백체크
	var emailPattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
	var phonePattern =  /^\d{3}\d{4}\d{4}$/;

	var emailOk = false;
	var PhoneOk = false;
	
	$("#email").on("change",function() {
		var emailOk = false;
	});
	$("#phone").on("change",function() {
		var phoneOk = false;
	});

//입력 할 때
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
			url:"/forAPIConfirmEmail",
			type:"post",
			data:formData,
			success:function(data){
				if (data == "t"){
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
		let formData = $("#phone").serialize();
		$.ajax({
			url:"/forAPIConfirmPhone",
			type:"post",
			data:formData,
			success:function(data){
				if (data == "t"){
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
	   
	   console.log($("#address").val());

	   
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

