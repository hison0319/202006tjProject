window.addEventListener("DOMContentLoaded",function(){
	//정규식
	var empJ = /\s/g; //공백체크
	var memberIdPattern = /^[a-z | A-Z]{3,6}[0-9]{3,6}$/;
	var passwordPattern = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
	var emailPattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
	var phonePattern =  /^01([0|1|6|7|8|9]?)([0-9]{3,4})([0-9]{4})$/;

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
					alert("사용가능 합니다.");
				} else {
					alert("중복된 아이디가 있습니다.");
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
	      //주소 공백 확인
	      if ($("#address").val() == "") {
	          alert("주소를 입력해주세요");
	          $("#address").focus();
	          return false;
	      }
	});
});

