var insertLength=0;
var table = $("tbody");
var wInput = document.querySelectorAll("input[name=word]");
var tInput = document.querySelectorAll("input[name=trans]");
var insertBtn = document.querySelector("#insert");  //추가 버튼
var updateBtn = document.querySelector("#update");  //수정 버튼
var formBtn = document.querySelector("#form");  //장문 추가 버튼
var index = wInput.length;  //단어 개수
var selectBar = document.querySelector("#arrange");  //정렬방식

function addLine(i){  //새로운 줄 추가
	iInputVal[i]=document.querySelectorAll("input[name=index]")[i].value;
	wInputVal[i]=document.querySelectorAll("input[name=word]")[i].value;
	tInputVal[i]=document.querySelectorAll("input[name=trans]")[i].value;
	fInputVal[i]=document.querySelectorAll("input[name=favorite]")[i].value;
	if(windowWidth>=480){  //창 너비가 480px 이상인 경우
		if(($.trim($("input[name=word]:not([disabled='disabled']):last").val()) != "")
		||($.trim($("input[name=trans]:not([disabled='disabled']):last").val()) != "")){  //마지막에 추가된 칸이 빈 칸이 아닐 경우
			if(document.querySelectorAll("input").length%8==0){  //줄이 꽉 찼을 경우 다음 줄 추가
				table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
				index++;
			}
			else{  //같은 줄에 빈 칸 추가
				table.children().last().prev().append("<td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td>");
				index++;
			}
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;  //새로운 칸 수
		}
		else if(($.trim($("input[name=word]:not([disabled='disabled']):last").val()) == "")  //마지막 칸의 word 칸이 비었을 경우
		&&($.trim($("input[name=trans]:not([disabled='disabled']):last").val()) == "")  //마지막 칸의 trans 칸이 비었을 경우
		&&(($.trim($("input[name=word]:not([disabled='disabled']):eq("+(insertLength-2)+")").val()) == "")  //마지막 전 칸의 word 칸이 비었을 경우
		&&($.trim($("input[name=trans]:not([disabled='disabled']):eq("+(insertLength-2)+")").val()) == ""))  //마지막 전 칸의 trans 칸이 비었을 경우
		&&insertLength>1){  //추가된 칸이 2 칸 이상일 경우
			if(document.querySelectorAll("input").length%8==0){  //같은 줄에 추가된 단어가 있을 경우
				$("tr:last").prev().find("td").last().remove();
				$("tr:last").prev().find("td").last().remove();  //마지막으로 추가된 줄의 마지막 칸을 지움
				iInputVal.splice(i,1);
				wInputVal.splice(i,1);
				tInputVal.splice(i,1);
				fInputVal.splice(i,1);  //배열의 i 번 째 제거
				index--;
			}
			else{  //새로운 줄에서 비어있을 경우
				$("tr:last").prev().remove();  //마지막으로 추가된 줄을 지움
				iInputVal.splice(i,1);
				wInputVal.splice(i,1);
				tInputVal.splice(i,1);
				fInputVal.splice(i,1);  //배열의 i 번 째 제거
				index--;
			}
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;  //새로운 칸 수
		}
	}
	else{  //창의 너비가 480px 미만인 경우
		if(($.trim($("input[name=word]:not([disabled='disabled']):last").val()) != "")
		||($.trim($("input[name=trans]:not([disabled='disabled']):last").val()) != "")){  //마지막에 추가된 칸이 빈 칸이 아닐 경우
			table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
			index++;
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;  //새로운 칸 수
		}  //새로운 칸 추가
		else if(($.trim($("input[name=word]:not([disabled='disabled']):last").val()) == "")  //마지막 칸의 word 칸이 비었을 경우
		&&($.trim($("input[name=trans]:not([disabled='disabled']):last").val()) == "")  //마지막 칸의 trans 칸이 비었을 경우
		&&(($.trim($("input[name=word]:not([disabled='disabled']):eq("+(insertLength-2)+")").val()) == "")  //마지막 전 칸의 word 칸이 비었을 경우
		&&($.trim($("input[name=trans]:not([disabled='disabled']):eq("+(insertLength-2)+")").val()) == ""))  //마지막 전 칸의 trans 칸이 비었을 경우
		&&insertLength>1){  //추가된 칸이 2 칸 이상일 경우
			$("tr:last").prev().remove();  //마지막으로 추가된 줄을 지움
			index--;
			iInputVal.splice(i,1);
			wInputVal.splice(i,1);
			tInputVal.splice(i,1);
			fInputVal.splice(i,1);  //배열의 i 번 째 제거
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;  //새로운 칸 수
		}
	}
}

function toggleAddLine(i){  //창 크기 변경 시
	addLine(i);
	$(window).resize(function(){
		if(tempWidth==1 && windowWidth<480) {
			addLine(i);
		}
		else if(tempWidth==0 && windowWidth>=480){
			addLine(i);
		}
	})
}


function sendInsertData(callback){  //추가 데이터 전송 함수
	let formData = $("form").eq(0).serialize();
	$.ajax({
		type:"post",
		url:"insert?wordbookid="+getParameterByName("wordbookid"),
		dataType:"json",
		data:formData,
		success:function(data){
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
				if(callback){
					callback();  //콜백
				}
			}
		},
		error:function(request,status,error){
        		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	}
	});
}


$("#insert").eq(0).on("click",function(){
	if(insertBtn.innerText=="추가"){  //추가버튼 클릭 시
		updateBtn.disabled = "disabled";  //수정버튼 비활성화
		formBtn.disabled = "disabled";  //장문추가버튼 비활성화
		selectBar.disabled = "disabled";  //정렬방식 비활성화
		insertBtn.innerText="추가완료";  //추가버튼 변경
		wInput = document.querySelectorAll("input[name=word]");
		index = wInput.length;  //기존 단어 수
		if(windowWidth>=480){  //창 너비가 480px 이상인 경우
			if(document.querySelectorAll("input").length%8==0){  //기존 단어로 한 줄이 꽉 차있는 경우
				table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
			}
			else{  //같은 줄에 빈 칸 추가
				table.children().last().prev().append("<td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td>");
			}
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;  //단어의 수
		}
		else{  //창 너비가 480px 미만인 경우
			table.children().last().before("<tr><td><input name='index' type='hidden' value='"+index+"'/><input type = 'text' name='word' onkeyup='toggleAddLine("+index+")'/></td><td><input type = 'text' name='trans' onkeyup='toggleAddLine("+index+")'/><input name='favorite' type='hidden' value='"+0+"'/></td></tr>");
			insertLength=$("input[name=word]:not([disabled='disabled'])").length;
		}
		index++;
		insertLength=$("input[name=word]:not([disabled='disabled'])").length;  //단어의 수
	}
	else {  //추가완료버튼 클릭 시
		updateBtn.disabled = null;  //수정버튼 활성화
		formBtn.disabled = null;  //장문추가버튼 활성화
		selectBar.disabled = null;  //정렬방식 선택 활성화
		insertBtn.innerText="추가";  //추가완료버튼 변경
		sendInsertData(function(){  //추가 데이터 전송 완료 후 다음 실행
			wInput = $("input[name=word]:not([disabled='disabled'])");
			tInput = $("input[name=trans]:not([disabled='disabled'])");
			var trs = $("tr");  //전체 tr
			if(windowWidth>=480){  //창 너비가 480px 이상인 경우
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
			$("input").each(function(){
				$(this).removeAttr("onkeyup");  //onkeyup 제거
				$(this).attr("disabled", "disabled");  //input 비활성화
			})
			setList();  //단어 재배열
		})
	}
	return false;
});