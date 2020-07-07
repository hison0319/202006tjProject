var words;
var trans;
var input = document.querySelectorAll("input");
var wInput = document.querySelectorAll("input[name=word]");
var tInput = document.querySelectorAll("input[name=trans]");
var offset = input.length;
var insertBtn = document.querySelector("#insert");  //추가 버튼
var updateBtn = document.querySelector("#update");  //수정 버튼
var formBtn = document.querySelector("#form");  //장문 추가 버튼

$("#update").eq(0).on("click",function(){
	var input = document.querySelectorAll("input");
	var wInput = document.querySelectorAll("input[name=word]");
	var tInput = document.querySelectorAll("input[name=trans]");
	var offset = input.length;
	if(updateBtn.innerText=="수정"){
		insertBtn.disabled = "disabled";
		formBtn.disabled = "disabled";
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
		insertBtn.disabled = null;
		formBtn.disabled = null;
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
							location.replace("/");
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
		wInput = $("input[name=word]:not([disabled='disabled'])");
		tInput = $("input[name=trans]:not([disabled='disabled'])");
		var trs = $("tr");
		for(let i = 0; i<wInput.length;i++){
			if($.trim(wInput.eq(i).val()) == "" && $.trim(tInput.eq(i).val()) == ""){
				wInput.eq(i).parent().remove();
				tInput.eq(i).parent().remove();
			}
		}
		for(let i = 0; i<trs.length;i++){
			if(trs.eq(i).find("td").length==0){
				trs.eq(i).remove();
			}
		}
		trs = $("tr");
		for(let i=0; i<trs.length-2; i++){
			if(trs.eq(i).find("td").length==2 && trs.eq(i+1).find("td")!=null){
				trs.eq(i+1).find("td").eq(0).appendTo(trs.eq(i));
				trs.eq(i+1).find("td").eq(0).appendTo(trs.eq(i));
			}
		}
		trs = $("tr");
		for(let i = 0; i<trs.length;i++){
			if(trs.eq(i).find("td").length==0){
				trs.eq(i).remove();
			}
		}
		input.forEach(function(element){
			element.disabled = "disabled";
		})
	}
	return false;
});
