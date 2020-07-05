/*var sortJSON = function(data, key, type) {
	if (type == undefined) {
		type = "asc";
	}
	return data.sort(function(a, b) {
		var x = a[key];
		var y = b[key];
		if (type == "desc") {
			return x > y ? -1 : x < y ? 1 : 0;
		} else if (type == "asc") {
			return x < y ? -1 : x > y ? 1 : 0;
		}
	});
};*/  //카운트 순서대로 배열

/*function getListFilter (data, key, value) {
if (data.TYPE == "S") {

return data.KK_LIST.filter (function (object) {

return object[key] === value;

});

} else {

alert(data.MESSAGE);

return "";

}

};*/  //값으로 json 접근
	
window.addEventListener("DOMContentLoaded", function(){
	$.ajax({
		type:"post",
		url:"getwords",
		dataType:"json",
		success:function(data){
			console.log(data);
			if(data.nope=="notAllowed") {
				alert("권한이 없습니다.");
				location.href="../wordbook/showlist";
			}
			else if(data.nope=="notExist") {
				alert("존재하지 않는 페이지입니다.");
				location.href="../wordbook/showlist";
			}
			else if(data.nope=="loginPlease") {
				alert("로그인이 필요한 서비스입니다.");
				location.href="../";
			}
			else {
				//console.log(sortJSON(data,"count","desc"));
				for(let i = data.length-1; i >= 0 ; i--){
					$("table").eq(0).prepend("<tr><td><input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
					+data[i].word + "' /></td><td><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
					+ data[i].trans + "' /></td></tr>");
					/*$("form").eq(0).prepend("<input name='word' id='word"+i+"' type='text' disabled='disabled' value='"
					+data[i].word + "' /><input name='trans' id='trans"+i+"' type='text' disabled='disabled' value='" 
					+ data[i].trans + "' /><input name='count' id='count"+i+"' type='number' disabled='disabled' value='"
					+ data[i].count + "' /><br/>");*/
				}
				$.getScript("/js/word/wordUpdate.js?v=<%=System.currentTimeMillis()%>");
			}
		},
		error:function(request,status,error){
             alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
          }
	})
});