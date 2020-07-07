window.onpageshow = function(event) {
	var historyTraversal = event.persisted
			|| (typeof window.performance != "undefined" && window.performance.navigation.type === 2);
	if (historyTraversal) {
		window.location.reload();
	} //페이지 뒤로가기 시 강제 리로딩
}
var favorite = $("[class*=favorite]");
for (let i = 0; i < favorite.length; i++) {
	favorite.eq(i).on(
			"click",
			function() {
				console.log(i);
				
				var data = $("form").eq(i).serialize();
				console.log(data);
				$.ajax({
					url : "favorite",
					type : "post",
					data : data,
					dataType : "json",
					success : function(data) {
						console.log(data);
					},
					error : function(request, status, error) {
						alert("code:" + request.status + "\n" + "message:"
								+ request.responseText + "\n" + "error:"
								+ error);
					}
				});
				if (favorite[i].innerText == "ㅡㅅㅡ") {
					favorite[i].innerText = "ㅇㅅㅇ";
				} else {
					favorite[i].innerText = "ㅡㅅㅡ";
				}
				return false;
			});
};
