var words;
var trans;
var insertLength=0;
var prevWord;
var prevTrans;
var table = $("tbody");
var input = document.querySelectorAll("input");
var wInput = document.querySelectorAll("input[name=word]");
var tInput = document.querySelectorAll("input[name=trans]");
var insertBtn = document.querySelector("#insert");  //추가 버튼
var updateBtn = document.querySelector("#update");  //수정 버튼
var formBtn = document.querySelector("#form");  //장문 추가 버튼

function addLine(){
	console.log(insertLength);
	if(($.trim($("input[name=word]:not([disabled='disabled']):last").val()) != "")
	||($.trim($("input[name=trans]:not([disabled='disabled']):last").val()) != "")){
		if(document.querySelectorAll("input").length%4==0){
			table.children().last().before("<tr><td><input type = 'text' name='word' onkeyup='addLine()'/></td><td><input type = 'text' name='trans' onkeyup='addLine()'/></td></tr>");
		}
		else{
			table.children().last().prev().append("<td><input type = 'text' name='word' onkeyup='addLine()'/></td><td><input type = 'text' name='trans' onkeyup='addLine()'/></td>");
		}
		insertLength=$("input[name=word]:not([disabled='disabled'])").length;
	}
	else if(($.trim($("input[name=word]:not([disabled='disabled']):last").val()) == "")
	&&($.trim($("input[name=trans]:not([disabled='disabled']):last").val()) == "")
	&&(($.trim($("input[name=word]:not([disabled='disabled']):eq("+(insertLength-2)+")").val()) == "")
	&&($.trim($("input[name=trans]:not([disabled='disabled']):eq("+(insertLength-2)+")").val()) == ""))
	&&insertLength>1){
		if(document.querySelectorAll("input").length%4==0){
			$("tr:last").prev().find("td").last().remove();
			$("tr:last").prev().find("td").last().remove();
		}
		else{
			$("tr:last").prev().remove();
		}
		insertLength=$("input[name=word]:not([disabled='disabled'])").length;
	}
}

console.log("ㅇㅅㅇ");
$("#insert").eq(0).on("click",function(){
	if(insertBtn.innerText=="추가"){
		updateBtn.disabled = "disabled";
		formBtn.disabled = "disabled";
		insertBtn.innerText="추가완료";
		if(document.querySelectorAll("input").length%4==0){
			table.children().last().before("<tr><td><input type = 'text' name='word' onkeyup='addLine()'/></td><td><input type = 'text' name='trans' onkeyup='addLine()'/></td></tr>");
		}
		else{
			table.children().last().prev().append("<td><input type = 'text' name='word' onkeyup='addLine()'/></td><td><input type = 'text' name='trans' onkeyup='addLine()'/></td>");
		}
		insertLength=$("input[name=word]:not([disabled='disabled'])").length;
		input = document.querySelectorAll("input");
		var addedWord=$("input[name=word]").last()[0];
		var addedTrans=$("input[name=trans]").last()[0];
	}
	else {
		updateBtn.disabled = null;
		formBtn.disabled = null;
		insertBtn.innerText="추가";
		let formData = $("form").eq(0).serialize();
		$.ajax({
			type:"post",
			url:"insert?wordbookid="+getParameterByName("wordbookid"),
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
					alert("추가 완료");
				}
			},
			error:function(request,status,error){
            		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        	}
		});
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
		document.querySelectorAll("input").forEach(function(element){
			element.disabled = "disabled";
		})
	}
	return false;
});