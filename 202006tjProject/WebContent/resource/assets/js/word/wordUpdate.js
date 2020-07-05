	var words;
	var trans;
	var input = document.querySelectorAll("input");
	var wInput = document.querySelectorAll("input[id^=word]");
	var tInput = document.querySelectorAll("input[id^=trans]");
	var modify = document.querySelector("#modify");  //수정 버튼
	
	function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
        return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }  //parameter 받아오기

	console.log("ㅇㅂㅇ");
	$("form").eq(0).on("submit",function(){
		if(modify.innerText=="수정"){
			words = new Array();
			trans = new Array();
			modify.innerText="수정완료";
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
			modify.innerText="수정";
			for (let i=0;i<wInput.length;i++){
				if((words[i]!=null && words[i]!=wInput[i].value) || (trans[i]!=null && trans[i]!=tInput[i].value)){
					console.log
					let formData = $("form").eq(0).serialize();
					$.ajax({
						type:"post",
						url:"update?wordbookid="+getParameterByName("wordbookid"),
						dataType:"json",
						data:formData,
						success:function(data){
							console.log(data);
							alert("수정 완료");
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
