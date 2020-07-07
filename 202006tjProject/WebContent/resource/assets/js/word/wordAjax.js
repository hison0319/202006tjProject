function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	results = regex.exec(location.search);
	return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}  //parameter 받아오기
	
window.addEventListener("DOMContentLoaded", function(){
	document.querySelector("#form").onclick=function(){
		location.href="/word/form?wordbookid="+getParameterByName("wordbookid");
		return false;
	}
	$.ajax({
		type:"post",
		url:"getwords",
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
				//console.log(sortJSON(data,"count","desc"));
				for(let i = data.length-1; i >= 0 ; i--){
					if(data.length%2==0){
						if((data.length-1-i)%2==0){
							$("table").eq(0).prepend("<tr><td><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /></td></tr>");
						}
						else{
							$("tr").eq(0).append("<td><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /></td>");
						}
					}
					else {
						if(i==data.length-1){
							$("table").eq(0).prepend("<tr><td><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /></td></tr>");
						}
						else if ((data.length-1-i)%2==1){
							$("table").eq(0).prepend("<tr><td><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /></td></tr>");
						}
						else {
							$("tr").eq(0).prepend("<td><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
							+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
							+ data[i].trans + "' /></td>");
						}
					}
				}
				$.getScript("/js/word/wordInsert.js?v=<%=System.currentTimeMillis()%>");
				$.getScript("/js/word/wordUpdate.js?v=<%=System.currentTimeMillis()%>");
			}
		},
		error:function(request,status,error){
             alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
          }
	})
});