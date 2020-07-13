/*
var student = [
    { name : "재석", age : 21},
    { name : "광희", age : 25},
    { name : "형돈", age : 13},
    { name : "명수", age : 44}
];
student.sort(function(a, b) { // 오름차순
    return a.name < b.name ? -1 : a.name > b.name ? 1 : 0;
});
*/
var insertBtn;
var updateBtn;
var windowWidth;
var iInputVal;
var wInputVal;
var tInputVal;
var fInputVal;
var tempWidth;
function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	results = regex.exec(location.search);
	return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}  //parameter 받아오기

function setList(){
	$.ajax({
		type:"post",
		url:"getwords?wordbookid="+getParameterByName("wordbookid"),
		dataType:"json",
		success:function(data){
			console.log(data);
			if(data.nope=="notAllowed") {
				alert("권한이 없습니다.");
				location.replace("wordbook/showlist");
			}
			else if(data.nope=="notExist") {
				alert("존재하지 않는 페이지입니다.");
				location.replace("wordbook/showlist");
			}
			else if(data.nope=="loginPlease") {
				alert("로그인이 필요한 서비스입니다.");
				location.replace("/login/form");
			}
			else if(data.nope == "wrongAccess" ){
				alert("잘못 된 접근입니다.");
				location.replace("/");
			}
			else {
				if(data.length > 1 && arrange.value == 1){
					data.sort(function(a,b){
						return b.favorite-a.favorite;
					})
					data.sort(function(a,b){
						return (a.favorite != b.favorite) ? 0 : a.index-b.index;
					})
				}
				else if(data.length > 1 && arrange.value == 2) {
					data.sort(function(a,b){
						return b.favorite-a.favorite;
					})
					data.sort(function(a,b){
						return (a.favorite != b.favorite) ? 0 : a.word < b.word ? -1 : a.word > b.word ? 1 : 0;
					})
				}
				else if(data.length > 1 && arrange.value == 3) {
					data = data.sort(function(a, b) { // 입력순
						return a.index-b.index;
					});
				}
				else if(data.length > 1 && arrange.value==4){
					data = data.sort(function(a, b) { // 오름차순
						return a.word < b.word ? -1 : a.word > b.word ? 1 : 0;
					});
				}
				iInputVal = new Array();
				wInputVal = new Array();
				tInputVal = new Array();
				fInputVal = new Array();
				for(let i=0; i<data.length; i++){
					iInputVal[i]=data[i].index;
					wInputVal[i]=data[i].word;
					tInputVal[i]=data[i].trans;
					fInputVal[i]=data[i].favorite;
				}
				showList(data, iInputVal, wInputVal, tInputVal, fInputVal);
				windowWidth = $(window).width();
				if(windowWidth>=480){
					tempWidth=1;
				}
				else{
					tempWidth=0;
				}
				$(window).resize(function(){
					windowWidth = $(window).width();
					if(tempWidth==1 && windowWidth<480) {
						for(let i=0; i<document.querySelectorAll("input[name=index]").length;i++){
							iInputVal[i]=document.querySelectorAll("input[name=index]")[i].value;
							wInputVal[i]=document.querySelectorAll("input[name=word]")[i].value;
							tInputVal[i]=document.querySelectorAll("input[name=trans]")[i].value;
							fInputVal[i]=document.querySelectorAll("input[name=favorite]")[i].value;
						}
						showList(data, iInputVal, wInputVal, tInputVal, fInputVal);
						tempWidth=0;
					}
					else if(tempWidth==0 && windowWidth>=480){
						for(let i=0; i<document.querySelectorAll("input[name=index]").length;i++){
							iInputVal[i]=document.querySelectorAll("input[name=index]")[i].value;
							wInputVal[i]=document.querySelectorAll("input[name=word]")[i].value;
							tInputVal[i]=document.querySelectorAll("input[name=trans]")[i].value;
							fInputVal[i]=document.querySelectorAll("input[name=favorite]")[i].value;
						}
						showList(data, iInputVal, wInputVal, tInputVal, fInputVal);
						tempWidth=1;
					}
				})
			}
		},
		error:function(request,status,error){
             alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
          }
	})
};

function showList(data, iInputVal, wInputVal, tInputVal, fInputVal){
	$("table").find("tr:not(:last)").remove();
	windowWidth = $(window).width();
	insertBtn = document.querySelector("#insert");
	updateBtn = document.querySelector("#update");
	if(windowWidth >= 480){
		for(let i = iInputVal.length-1; i >= 0 ; i--){
			if(iInputVal.length%2==0){  //칸 수가 짝수일 경우
				if(insertBtn.innerText == "추가" && updateBtn.innerText == "수정"){  //평상시
					if((iInputVal.length-1-i)%2==0){  //큼, 전체짝수칸, 평상시, 홀수칸
					console.log("1");
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else{  //큼, 전체짝수칸, 평상시, 짝수칸
					console.log("2");
						$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
					}
				}
				else if(updateBtn.innerText == "수정완료"){
					if((iInputVal.length-1-i)%2==0){  //큼, 전체짝수칸, 수정시, 홀수칸
					console.log("3");
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' onfocus='wordModified("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' onfocus='transModified("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else{  //큼, 전체짝수칸, 수정시, 짝수칸
					console.log("4");
						$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "'  onfocus='wordModified("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' onfocus='transModified("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
					}
					if(i==0){
						modifying();
					}
				}
				else {  //큼, 전체짝수칸, 추가시
					if((iInputVal.length-1-i)%2==0){  //큼, 전체짝수칸, 추가시, 홀수칸
						if(i<data.length){  //큼, 전체짝수칸, 추가시, 홀수칸, 기존데이터
						console.log("5");
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td></tr>");
						}
						else{  //큼, 전체짝수칸, 추가시, 홀수칸, 신규데이터
						console.log("6");
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
						}
					}
					else {   //큼, 전체짝수칸, 추가시, 짝수칸
						if(i<data.length){   //큼, 전체짝수칸, 추가시, 짝수칸, 기존데이터
						console.log("7");
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td>");
						}
						else{   //큼, 전체짝수칸, 추가시, 짝수칸, 신규데이터
						console.log("8");
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
						}
					}
				}
			}  //전체짝수칸
			else {  //전체홀수칸
				if(insertBtn.innerText == "추가" && updateBtn.innerText == "수정"){  //평상시
					if(i==iInputVal.length-1){  //전체홀수칸, 평상시, 첫 칸
					console.log("9");
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else if ((iInputVal.length-1-i)%2==1){  //전체홀수칸, 평상시, 짝수칸
					console.log("10");
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else {  //전체홀수칸, 평상시, 홀수칸
					console.log("11");
						$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
					}
				}
				else if(updateBtn.innerText == "수정완료"){  //전체홀수칸, 수정시
					if(i==iInputVal.length-1){  //전체홀수칸, 수정시, 첫 칸
					console.log("12");
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' onfocus='wordModified("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' onfocus='transModified("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else if ((iInputVal.length-1-i)%2==1){  //전체홀수칸, 수정시, 짝수칸
					console.log("13");
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' onfocus='wordModified("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' onfocus='transModified("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else {  //전체홀수칸, 수정시, 홀수칸
					console.log("14");
						$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' onfocus='wordModified("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' onfocus='transModified("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
					}
					if(i==0){
						modifying();
					}
				}
				else{  //전체홀수칸, 추가시
					if(i==iInputVal.length-1){  //전체홀수칸, 추가시, 첫 칸
					console.log("15");
						$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
					}
					else if((iInputVal.length-1-i)%2==1){  //전체홀수칸, 추가시, 짝수칸
						if(i<data.length){  //전체홀수칸, 추가시, 짝수칸, 기존데이터
						console.log("16");
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td></tr>");
						}
						else{  //전체홀수칸, 추가시, 짝수칸, 신규데이터
						console.log("17");
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
						}
					}
					else{  //전체홀수칸, 추가시, 홀수칸
						if(i<data.length){  //전체홀수칸, 추가시, 홀수칸, 기존데이터
						console.log("18");
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td>");
						}
						else{  //전체홀수칸, 추가시, 홀수칸, 신규데이터
						console.log("19");
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td>");
						}
					}
				}
			}
		}
	}  //창 너비가 480px 이상인 경우
	else {
		for(let i=iInputVal.length-1; i>=0; i--){
			if(insertBtn.innerText == "추가" && updateBtn.innerText == "수정"){
				console.log("20");
				$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
						+wInputVal[i] + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
						+ tInputVal[i] + "' /><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
			}
			else if(updateBtn.innerText == "수정완료"){
				$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' onfocus='wordModified("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' onfocus='transModified("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
				if(i==0){
					modifying();
				}
			}
			else{
				if(i<data.length){
					$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
						+wInputVal[i] + "' disabled='disabled'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
						+ tInputVal[i] + "' disabled='disabled'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
				}
				else{
					$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+iInputVal[i]+"'/><input name='word' id='word"+i+"' type='text' value='"
							+wInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/></td><td><input name='trans' id='trans"+i+"' type='text' value='" 
							+ tInputVal[i] + "' onkeyup='toggleAddLine("+i+")'/><input name='favorite' type='hidden' value='"+fInputVal[i]+"'/></td></tr>");
				}
			}
		}
	}
	$("input[name=favorite][value=1]").parent().prev().children("input").css("color","red");
	$("input[name=favorite][value=1]").prev().css("color","red");
	console.log("빨강!!");
};


document.querySelector("#form").onclick=function(){
	location.href="/word/form?wordbookid="+getParameterByName("wordbookid");
	return false;
}
document.querySelector("#deleting").onclick=function(){
	let isOK = prompt("삭제하시려면 [단어장 삭제 확인]을 입력하세요.")
	if(isOK!="단어장 삭제 확인"){
		alert("입력이 잘못되었습니다.");
	}
	else{
		location.replace("/wordbook/delete?wordbookid="+getParameterByName("wordbookid"));
	}
}
setList();  //단어를 펼침

var arrange = document.querySelector("select#arrange");
arrange.onchange=function(){  //선택지 변경 시
	setList();
};

$.getScript("/js/word/wordInsert.js?v=<%=System.currentTimeMillis()%>");
$.getScript("/js/word/wordUpdate.js?v=<%=System.currentTimeMillis()%>");
