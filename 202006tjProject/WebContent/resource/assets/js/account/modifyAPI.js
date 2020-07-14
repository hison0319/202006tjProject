window.addEventListener("DOMContentLoaded",function(){
   //정규식
   var empJ = /\s/g; //공백체크
	var emailPattern = /^[0-9a-zA-Z]+@[0-9a-zA-Z]+\.[a-zA-z]{2,3}$/;
	var phonePattern =  /^\d{3}\d{4}\d{4}$/;

   var emailOk = true;
   var PhoneOk = true;
   
//입력 할 때   
   $("#email").on("change",function() {
	   document.querySelector("#emailMsg").innerText = "사용가능한 이메일인지 확인받으세요.";
      var emailOk = false;
   });
   $("#phone").on("change",function() {
	   document.querySelector("#phoneMsg").innerText = "사용가능한 전화번호인지 확인받으세요.";
      var PhoneOk = false;
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
            	document.querySelector("#emailMsg").innerText = "변경하지 않았습니다.";
            } else if (data == "t"){
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
         url:"/account/confirmPhone",
         type:"post",
         data:formData,
         success:function(data){
            if(data == "s") {
            	document.querySelector("#phoneMsg").innerText = "변경하지 않았습니다.";
            } else if (data == "t"){
            	document.querySelector("#phoneMsg").innerText = "사용 가능 합니다.";
            } else {
            	document.querySelector("#phoneMsg").innerText = "중복된 전화번호가 있습니다.";              
            }
         }
      })
      return false;
   });
   
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
      if (!emailOk) {
    	  document.querySelector("#emailMsg").innerText = "사용 가능한 이메일인지 확인받으세요.";
         return false;
      }
      if (!phoneOk) {
    	  document.querySelector("#phoneMsg").innerText = "사용 가능한 전화번호인지 확인받으세요.";
          return false;
      }
   });
});
//회원 탈퇴하기 눌렀을 때
function deleteCheck(id) {
	var conf = confirm("정말 탈퇴하시겠습니까?");
	console.log(id);
	if(conf) {
		location.replace("delete?id="+id);
	}
}
