var insertLength=0;
var table = $("tbody");
var input = document.querySelectorAll("input");
var wInput = document.querySelectorAll("input[name=word]");
var tInput = document.querySelectorAll("input[name=trans]");
var insertBtn = document.querySelector("#insert");  //추가 버튼
var updateBtn = document.querySelector("#update");  //수정 버튼
var formBtn = document.querySelector("#form");  //장문 추가 버튼
var index = wInput.length;
var selectBar = document.querySelector("#arrange");

function addLine(i){
	iInputVal[i]=document.querySelectorAll("input[name=index]")[i].value;
	wInputVal[i]=document.querySelectorAll("input[name=word]")[i].value;
	tInputVal[i]=document.querySelectorAll("input[name=trans]")[i].value;
	fInputVal[i]=document.querySelectorAll("input[name=favorite]")[i].value;
	if(windowWidth>=480){
		if(($.trim($("input[name=word]:not([disabled='disabled']):last").val()) != "")
		||($.trim($("input[name=trans]:not([disabled='disabled']):last").val()) != "")){
			if(document.querySelectorAll("input").length%8==0){  //줄이 꽉 찼을 경우 다음 줄 추가
				table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
				index++;
			}
			else{  //같은 줄에 빈 칸 추가
				table.children().last().prev().append("<td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td>");
				index++;
			}
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;  //단어의 수
		}
		else if(($.trim($("input[name=word]:not([disabled='disabled']):last").val()) == "")  //마지막 칸의 word 칸이 비었을 경우
		&&($.trim($("input[name=trans]:not([disabled='disabled']):last").val()) == "")  //마지막 칸의 trans 칸이 비었을 경우
		&&(($.trim($("input[name=word]:not([disabled='disabled']):eq("+(insertLength-2)+")").val()) == "")  //마지막 전 칸의 ``
		&&($.trim($("input[name=trans]:not([disabled='disabled']):eq("+(insertLength-2)+")").val()) == ""))  //``
		&&insertLength>1){  //마지막 단어가 비어있을 경우
			if(document.querySelectorAll("input").length%8==0){  //같은 줄에 추가된 단어가 있을 경우
				$("tr:last").prev().find("td").last().remove();
				$("tr:last").prev().find("td").last().remove();
				index--;
			}
			else{  //새로운 줄에서 비어있을 경우
				$("tr:last").prev().remove();
				index--;
			}
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;
		}
	}
	else{
		if(($.trim($("input[name=word]:not([disabled='disabled']):last").val()) != "")
		||($.trim($("input[name=trans]:not([disabled='disabled']):last").val()) != "")){
			table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
			index++;
			console.log(iInputVal[iInputVal.length-1]);
		}
	}
}

function toggleAddLine(i){
	addLine(i);
	$(window).resize(function(){
		windowWidth = $(window).width();
		if(tempWidth==1 && windowWidth<480) {
			addLine(i);
		}
		else if(tempWidth==0 && windowWidth>=480){
			addLine(i);
		}
	})
}

function toggleStartLine(){
	$(window).resize(function(){
		if(tempWidth==1 && windowWidth<480 && $("input[name=index]:not(:disabled)").length==1) {
			table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine"+index+"'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
		}
		else if(tempWidth==0 && windowWidth>=480 && $("input[name=index]:not(:disabled)").length==1){
			($("input:not([type=hidden]):not(:disabled)").val()==null || $("input:not([type=hidden]):not(:disabled)").val()==undefined).remove();
			if(document.querySelectorAll("input").length%8==0){
				table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
				index++;
			}
			else{  //같은 줄에 빈 칸 추가
				table.children().last().prev().append("<td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td>");
				index++;
			}
		}
	})
}
$("#insert").eq(0).on("click",function(){
	if(insertBtn.innerText=="추가"){
		updateBtn.disabled = "disabled";
		formBtn.disabled = "disabled";
		selectBar.disabled = "disabled";
		insertBtn.innerText="추가완료";
		wInput = document.querySelectorAll("input[name=word]");
		index = wInput.length;
		console.log("인덱스: " + index);
		if(windowWidth>=480){
			if(document.querySelectorAll("input").length%8==0){
				table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
			}
			else{  //같은 줄에 빈 칸 추가
				table.children().last().prev().append("<td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td>");
			}
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;  //단어의 수
		}
		else{
			table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;
		}
		toggleStartLine(index);
		index++;
		for(let i=0; i<document.querySelectorAll("input[name=index]").length;i++){
			iInputVal[i]=document.querySelectorAll("input[name=index]")[i].value;
			wInputVal[i]=document.querySelectorAll("input[name=word]")[i].value;
			tInputVal[i]=document.querySelectorAll("input[name=trans]")[i].value;
			fInputVal[i]=document.querySelectorAll("input[name=favorite]")[i].value;
		}
		
		insertLength=$("input[name=word]:not([disabled='disabled'])").length;
		input = document.querySelectorAll("input");
		var addedWord=$("input[name=word]").last()[0];
		var addedTrans=$("input[name=trans]").last()[0];
	}
	else {
		updateBtn.disabled = null;
		formBtn.disabled = null;
		selectBar.disabled = null;
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
		if(windowWidth>=480){
			for(let i = 0; i<wInput.length;i++){
				if($.trim(wInput.eq(i).val()) == "" && $.trim(tInput.eq(i).val()) == ""){  //빈 칸(td) 삭제
					wInput.eq(i).parent().remove();
					tInput.eq(i).parent().remove();
				}
			}
			for(let i = 0; i<trs.length;i++){
				if(trs.eq(i).find("td").length==0){  //빈 줄(tr) 삭제
					trs.eq(i).remove();
				}
			}
			trs = $("tr");
			for(let i=0; i<trs.length-2; i++){  //마지막 줄(버튼)과 마지막 입력 줄 제외(다음 줄에서 떙겨오기 때문)
				if(trs.eq(i).find("td").length==2 && trs.eq(i+1).find("td")!=null){  //한 줄에 한 칸만 있고 다음 줄에도 단어가 있을 경우
					trs.eq(i+1).find("td").eq(0).appendTo(trs.eq(i));  //다음 줄의 단어를 이 줄의 뒤에 붙임
					trs.eq(i+1).find("td").eq(0).appendTo(trs.eq(i));  //다음 줄의 해석을 이 줄의 뒤에 붙임
				}
			}
			trs = $("tr");
			for(let i = 0; i<trs.length;i++){
				if(trs.eq(i).find("td").length==0){
					trs.eq(i).remove();  //빈 줄 제거
				}
			}
		}
		else{
			for(let i = 0; i<wInput.length;i++){
				if($.trim(wInput.eq(i).val()) == "" && $.trim(tInput.eq(i).val()) == ""){  //빈 칸(td) 삭제
					wInput.eq(i).parent().remove();
					tInput.eq(i).parent().remove();
				}
			}
			for(let i = 0; i<trs.length;i++){
				if(trs.eq(i).find("td").length==0){  //빈 줄(tr) 삭제
					trs.eq(i).remove();
				}
			}
		}
		/*document.querySelectorAll("input").forEach(function(element){
			element.onkeyup = " ";
			element.disabled = "disabled";
		})*/
	}
	for(let i=0; i<document.querySelectorAll("input[name=index]").length; i++){
			iInputVal[i]=document.querySelectorAll("input[name=index]")[i].value;
			wInputVal[i]=document.querySelectorAll("input[name=word]")[i].value;
			tInputVal[i]=document.querySelectorAll("input[name=trans]")[i].value;
			fInputVal[i]=document.querySelectorAll("input[name=favorite]")[i].value;
		}
		console.log("추가-" +wInputVal);
	return false;
});