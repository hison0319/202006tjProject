window.onpageshow = function(event) {
	var historyTraversal = event.persisted
			|| (typeof window.performance != "undefined" && window.performance.navigation.type === 2);
	if (historyTraversal) {
		window.location.reload();
	} //페이지 뒤로가기 시 강제 리로딩
}
var favorite = $("[class*=favorite]");
var star = $("[class*=star]");
for (let i = 0; i < favorite.length; i++) {
	//favorite버튼은 i번부터 시작. 0번부터 적용.
	//위의 SearchForm으로 인해 form은 i+1번, form부터 ajax적용.
	favorite.eq(i).on(
			"click",
			function() {
				var data = $("form").eq(i+1).serialize();
				$.ajax({
					url : "favorite",
					type : "post",
					data : data,
					dataType : "json",
					success : function(data) {
						if(data == 1) {
							star.eq(i).css('color', '#cc0');							
						} else {
							star.eq(i).css('color', '#ccc');							
						}
					},
				});
				return false;
			});
};
