$(function(){
	var sortJSON = function(data, key, type) {
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
};
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
				console.log(sortJSON(data,"count","desc"));
				for(let i = 0; i < data.length ; i++){
					$("ul#words").eq(0).append("<li>"+data[i].word + ":" + data[i].trans + ":" + data[i].count + "</li>");
				}
			}
		},
		error:function(request,status,error){
             alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
          }
	})
});