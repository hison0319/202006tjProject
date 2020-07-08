$("#arraySelect").on("change",function() {
	let method = $("#arraySelect option:selected").val()
	console.log(method);
	if(method == 2) {
		location.replace("/wordbook/showlist");
	} else if(method == 3) {
		location.replace("/wordbook/showlistOwner");
	} else if(method == 4) {
		location.replace("/wordbook/showlistGuest");
	} else {
		location.replace("/wordbook/showlist");
	}
});

$(".searchBtn").on("click",function() {
	console.log("a");
});
