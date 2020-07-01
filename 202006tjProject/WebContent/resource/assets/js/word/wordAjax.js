$(function(){
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
				for(let i = 0; i < data.length ; i++){
					$("ul#words").eq(0).append("<li>"+data[i].word + ":" + data[i].trans+"</li>");
				}
			}
		},
		error:function(request,status,error){
             alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
          }
	})
});