$("#arraySelect").on("change",function() {	//순서정렬 기능
	let method = $("#arraySelect option:selected").val()
	if(method == 2) {
		location.replace("/wordbook/showlistFavorite");
	} else if(method == 3) {
		location.replace("/wordbook/showlistOwner");
	} else if(method == 4) {
		location.replace("/wordbook/showlistGuest");
	} else {
		location.replace("/wordbook/showlist");
	}
});
