	var words;
var trans;
var input = document.querySelectorAll("input");
var wInput = document.querySelectorAll("input[name=word]");
var tInput = document.querySelectorAll("input[name=trans]");
var insertBtn = document.querySelector("#insert");  //추가 버튼
var updateBtn = document.querySelector("#update");  //수정 버튼

function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	results = regex.exec(location.search);
	return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}  //parameter 받아오기

$("#update").eq(0).on("click",function(){
	if(insertBtn.innerText=="추가 완료"){
		alert("추가 작업을 마쳐주세요.");
	}
	else if(updateBtn.innerText=="수정"){
		words = new Array();
		trans = new Array();
		updateBtn.innerText="수정완료";
		input.forEach(function(element){
			element.disabled = null;
		})
		console.dir(wInput);
		for(let i=0; i<wInput.length; i++){
			wInput[i].onfocus = function(){
				if(words[i]==null){
					words[i]=wInput[i].value;
				}
			};
			tInput[i].onfocus = function(){
				if(trans[i]==null){
					trans[i]=tInput[i].value;
				}
			};
		}
	}
	else {
		updateBtn.innerText="수정";
		for (let i=0;i<wInput.length;i++){
			if((words[i]!=null && words[i]!=wInput[i].value) || (trans[i]!=null && trans[i]!=tInput[i].value)){
				let formData = $("form").eq(0).serialize();
				$.ajax({
					type:"post",
					url:"update?wordbookid="+getParameterByName("wordbookid"),
					dataType:"json",
					data:formData,
					success:function(data){
						console.log(data);
						if(data.nope == "loginPlease"){
							alert("로그인이 필요한 페이지입니다.");
							location.replace("/login/form");
						}
						else if(data.nope == "certifyPlease"){
							alert("인증이 필요한 페이지입니다.");
							location.replace("/certify/form");
						}
						else if(data.nope == "wrongAccess"){
							alert("잘못 된 접근입니다.");
						}
						else if(data.nope == "notExist"){
							alert("존재하지 않는 페이지입니다.")
							location.replace("/");
						}
						else{
							alert("수정 완료");
						}
					},
					error:function(request,status,error){
	             		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	         		 }
				});
				break;
			}
		}
		input.forEach(function(element){
			element.disabled = "disabled";
		})
	}
	return false;
});
