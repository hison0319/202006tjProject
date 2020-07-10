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

function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	results = regex.exec(location.search);
	return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}  //parameter 받아오기

function showList(){
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
				$("table").find("tr:not(:last)").remove();
				if(data.length > 1 && arrange.value == 1) {
					data = data.sort(function(a, b) { // 입력순
						return a.index-b.index;
					});
				}
				else {
					data = data.sort(function(a, b) { // 오름차순
						return a.word < b.word ? -1 : a.word > b.word ? 1 : 0;
					});
				}
				for(let i = data.length-1; i >= 0 ; i--){
					if(data.length%2==0){
						if((data.length-1-i)%2==0){
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td></tr>");
						}
						else{
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td>");
						}
					}
					else {
						if(i==data.length-1){
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td></tr>");
						}
						else if ((data.length-1-i)%2==1){
							$("table").eq(0).prepend("<tr><td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td></tr>");
						}
						else {
							$("tr").eq(0).prepend("<td><input name='index' type='hidden' value='"+data[i].index+"'/><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /><input name='favorite' type='hidden' value='"+data[i].favorite+"'/></td>");
						}
					}
				}
			}
		},
		error:function(request,status,error){
             alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
          }
	})
};
window.addEventListener("DOMContentLoaded", function(){
	document.querySelector("#form").onclick=function(){
		location.href="/word/form?wordbookid="+getParameterByName("wordbookid");
		return false;
	}
	showList();  //단어를 펼침
	
	var arrange = document.querySelector("select#arrange");
	arrange.onchange=function(){  //선택지 변경 시
		showList();
	};
	
	$.getScript("/js/word/wordInsert.js?v=<%=System.currentTimeMillis()%>");
	$.getScript("/js/word/wordUpdate.js?v=<%=System.currentTimeMillis()%>");
});

var windowWidth;
$( window ).resize(function() {
	windowWidth = $(window).width();
	   if(windowWidth <= 480 ){
		   console.log(windowWidth);
	   } else {
		   console.log(windowWidth);
	   }
	});





